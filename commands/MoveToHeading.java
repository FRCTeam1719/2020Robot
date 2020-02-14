/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Winch;

/**
 * An example command. You can replace me with your own command.
 */
public class MoveToHeading extends Command {

  public static final double Kp = 0.013; // .01 seems to work. ROTATE
  public static final double KpS = 0.001; // .001 seems to work. SERVO

  public static double steering_adjust;
  double servoPos;
  Drive drive;
  boolean forward;
  Servo cameraServo;
  double driveSetpoint;
  boolean move;
  AnalogInput distanceSensor;
  boolean down;
  Timer intakeTimer;
  boolean foundTarget;

  public MoveToHeading(Drive _drive, Servo servo) {
    // Use requires() here to declare subsystem dependencies
    this.drive = _drive;
    cameraServo = servo;
    requires(this.drive);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    steering_adjust = 0;

    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(0);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
    // might need to set servo to look a bit up in initialize
    // servoPos = cameraServo.get();
    servoPos = .75;
    cameraServo.set(.75);
    driveSetpoint = 0;
    forward = true;
    distanceSensor = RobotMap.ultrasonicSensor;
    down = true;
    intakeTimer = new Timer();
    System.out.println("INITIALIZING");
    foundTarget = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double angleDeadzone = 3;
    double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    steering_adjust = Kp * tx;

    // System.out.println("distance: " + distanceSensor.getVoltage());
    if (foundTarget || (tx < angleDeadzone && tx > 0) || ((tx < 0) && (tx > (-1 * angleDeadzone)))) {
      foundTarget = true;
      // VERY EXPERIMENTAL
      if (Robot.winch.atBottom() && down) {
        intakeTimer.start();
        down = false;
        Scheduler.getInstance().add(new ToggleWinch(Robot.winch));
      }
      if (forward) {
        drive.drive(.2, -.2);
      } else
        drive.drive(0, 0);

    } else {
      // fixes issue where it hasnt found target but it is too close to actually
      // change
      if (steering_adjust > 0 && steering_adjust < .06)
        foundTarget = true;
      else if (steering_adjust < 0 && steering_adjust > -.06)
        foundTarget = true;
      drive.drive(steering_adjust, steering_adjust);
    }

    // When it is ready to score
    if (Robot.winch.atTop() && (distanceSensor.getVoltage() < .7) && intakeTimer.get() > 2.5) {
      forward = false;
      intakeTimer.stop();
      intakeTimer.reset();
      drive.drive(0, 0);
      System.out.println("READY TO SCORE");
      Scheduler.getInstance().add(new RunOuttake(Robot.intake));
    }

    // have it find the target with servo PID loop
    double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    if (ty > 3 || ty < -3) {
      servoPos -= ty * KpS;
    }

    // System.out.println("servo: " + servoPos + Robot.winch.atBottom());

    // when it should raise the winch based on distance
    // if (((distanceSensor.getVoltage() < 2) && Robot.winch.atBottom()) && down) {
    // down = false;
    // Scheduler.getInstance().add(new ToggleWinch(Robot.winch));
    // System.out.println("moving winch");
    // // drive.drive(-.001, 0.001);
    // }

    if (servoPos < .3) {
      servoPos = .3;
    }
    cameraServo.set(servoPos);

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("end");
    UseDrive.leftOutput = .15;
    UseDrive.rightOutput = -.15;
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    System.out.println("interrupted");
    cameraServo.set(.75);
    // UseDrive.leftOutput = .15;
    // UseDrive.rightOutput = -.15;
  }
}
