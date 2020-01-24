/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Drive;

/**
 * An example command. You can replace me with your own command.
 */
public class MoveToHeading extends Command {

  public static final double Kp = 0.1;
  public static double steering_adjust;
  public static double left_command;
  public static double right_command;
  Drive drive;

  public MoveToHeading(Drive _drive) {
    // Use requires() here to declare subsystem dependencies
    this.drive = _drive;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    steering_adjust = 0;
    left_command = 0;
    right_command = 0;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    System.out.println("limelight data: " + tx);
    steering_adjust = Kp * tx;

    left_command += steering_adjust;
    right_command -= steering_adjust;

    drive.drive(left_command, right_command);

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
