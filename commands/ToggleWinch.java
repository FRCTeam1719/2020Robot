/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Winch;

public class ToggleWinch extends Command {

  Winch winch;
  double speed;

  public ToggleWinch(Winch winch) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.winch = winch;
    requires(this.winch);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    speed = 0;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (winch.atTop()) {
      speed = .25;
    } else if (winch.atBottom()) {
      speed = -.25;
    }
    winch.moveWinch(speed);

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (speed == .25 && winch.atBottom()) {
      return true;
    } else if (speed == -.25 && winch.atTop()) {
      return true;
    }
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    winch.moveWinch(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
