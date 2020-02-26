/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Winch;

public class ShakeBot extends Command {
  final double SHAKE_STRENGTH = 0.5;

  Timer timer;
  private Drive drive;
  private Winch winch;

  public ShakeBot(Drive drive, Winch winch) {
    timer = new Timer();
    timer.start();

    this.drive = drive;
    this.winch = winch;

    requires(drive);
    requires(winch);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    timer.reset();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (timer.get() > 0.2) {
      drive.drive(SHAKE_STRENGTH, -SHAKE_STRENGTH);
      if(timer.get() > .75){
        drive.drive(0, 0);
        winch.moveWinch(-.1);
      }
      else if(timer.get() > .6){
        drive.drive(-.1,.1);
      }
      
    } else {
      winch.moveWinch(-.1);
    }

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return winch.atBottom();
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
