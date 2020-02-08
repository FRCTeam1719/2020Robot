/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Drive;

/**
 * An example command. You can replace me with your own command.
 */
public class MoveToHeading extends Command {

  public static final double Kp = 0.01;
  public static final double KpS = 0.001;

  public static double steering_adjust;
  double servoPos;
  public static double left_command;
  public static double right_command;
  Drive drive;
  boolean forward;
  Servo cameraServo;
  double driveSetpoint;

  public MoveToHeading(Drive _drive, Servo servo) {
    // Use requires() here to declare subsystem dependencies
    this.drive = _drive;
    cameraServo = servo;
    requires(drive);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    steering_adjust = 0;
    left_command = 0;
    right_command = 0;
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(0);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
    forward = false;
    // might need to set servo to look a bit up in initialize
    // servoPos = cameraServo.get();
    servoPos = .2;
    driveSetpoint = 0;

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double angleDeadzone = 3;
    double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    steering_adjust = Kp * tx;

    left_command += steering_adjust;
    right_command -= steering_adjust;

    if ((tx < angleDeadzone && tx > 0) || ((tx < 0) && (tx > (-1 * angleDeadzone)))) {
      forward = true;
      // NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
      // NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
      // cameraServo.set(0);
    } else {
      drive.drive(steering_adjust, steering_adjust);
      forward = false;
    }

    if (forward) {
      drive.drive(-.15, .15);
    }
    // have it find the target with servo PID loop
    double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    if (ty > 3 || ty < -3) {
      servoPos += ty * KpS;
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

  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    UseDrive.leftOutput = .15;
    UseDrive.rightOutput = -.15;
  }
}
