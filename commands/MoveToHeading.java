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
import frc.robot.Robot;
import frc.robot.subsystems.Drive;

/**
 * An example command. You can replace me with your own command.
 */
public class MoveToHeading extends Command {

  public static final double Kp = 0.013; // .01 seems to work. ROTATE
  public static final double KpS = 0.001; // .001 seems to work. SERVO

  public static final double SCORE_DISTANCE = 0.7;
  public static final double SENSOR_SAFE_TIME = 2.5;
  public static final double PID_ANGLE_ERROR = 0.06;

  double steeringAdjust;

  boolean foundTargetLock;
  boolean downLock;
  boolean forwardLock;

  Servo cameraServo;
  double servoPos;

  Drive drive;
  double driveSetpoint;

  AnalogInput distanceSensor;

  Timer intakeTimer;

  public MoveToHeading(Drive _drive, Servo servo, AnalogInput distance) {
    // Use requires() here to declare subsystem dependencies
    this.drive = _drive;
    cameraServo = servo;
    distanceSensor = distance;
    requires(this.drive);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("INITIALIZING Move to heading command...");

    steeringAdjust = 0;

    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(0);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);

    // might need to set servo to look a bit up in initialize
    // servoPos = cameraServo.get();

    forwardLock = true;
    downLock = true;
    foundTargetLock = false;

    servoPos = .75;
    cameraServo.set(.75);
    
    driveSetpoint = 0;

    intakeTimer = new Timer();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double angleDeadzone = 3;
    double targetAngleX = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    steeringAdjust = Kp * targetAngleX;

    // System.out.println("distance: " + distanceSensor.getVoltage());
    if (foundTargetLock || Math.abs(targetAngleX) < angleDeadzone) {
      foundTargetLock = true;

      if (Robot.winch.atBottom() && downLock) {
        intakeTimer.start();
        downLock = false;

        Scheduler.getInstance().add(new ToggleWinch(Robot.winch));
      }

      if (forwardLock) 
        drive.drive(.2, -.2);
      else
        drive.drive(0, 0);

    } else {
      // fixes issue where it hasnt found target but it is too close to actually change

      if (steeringAdjust > 0 && steeringAdjust < PID_ANGLE_ERROR)
        foundTargetLock = true;
      else if (steeringAdjust < 0 && steeringAdjust > -PID_ANGLE_ERROR)
        foundTargetLock = true;
        
      drive.drive(steeringAdjust, steeringAdjust);
    }

    // When it is ready to score
    if (Robot.winch.atTop() && (distanceSensor.getVoltage() < SCORE_DISTANCE) && intakeTimer.get() > SENSOR_SAFE_TIME) {
      forwardLock = false;

      intakeTimer.stop();
      intakeTimer.reset();

      drive.drive(0, 0);

      System.out.println("READY TO SCORE");

      Scheduler.getInstance().add(new RunOuttake(Robot.intake));
    }

    // have it find the target with servo PID loop
    double targetAngleY = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    if (targetAngleY > 3 || targetAngleY < -3) {
      servoPos -= targetAngleY * KpS;
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
