/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Drive;

public class UseDrive extends Command {
  Drive drive;

  double Kpl = .1;
  double errorL = 0;
  double leftOutput = 0;
  double errorR = 0;
  double rightOutput = 0;

  public UseDrive(Drive drive) {
    this.drive = drive;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    double leftJoystick = -1 * Robot.m_oi.getDriverLeftY();
    if (leftJoystick < .008 && leftJoystick > -.008)
      leftJoystick = 0;
    errorL = leftJoystick - leftOutput;
    leftOutput = leftOutput + (errorL * Kpl);
    if (leftOutput < .01 && leftOutput > -.01)
      leftOutput = 0;

    double rightJoystick = Robot.m_oi.getDriverRightY();
    if (rightJoystick < .008 && rightJoystick > -.008)
      rightJoystick = 0;
    errorR = rightJoystick - rightOutput;
    rightOutput = rightOutput + (errorR * Kpl);
    if (rightOutput < .01 && rightOutput > -.01)
      rightOutput = 0;
    drive.drive(leftOutput, rightOutput);
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
  }
}
