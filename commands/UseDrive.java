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
  public static double turnAmount = 0;
  public static double leftOutput = 0;
  public static double rightOutput = 0;
  boolean toggleOn = true;
 

  
  double errorR = 0;
  public static double totalOutput = 0;

  public UseDrive(Drive drive) {
    this.drive = drive;
    requires(this.drive);
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
    if(Robot.m_oi.getDriverDriveMode()){
      toggleOn = !toggleOn;
  
    } 
    double rightJoystick = Robot.m_oi.getDriverRightX(); // CURRENTLY IN SLOWMODE BECAUSE DIVIDING BY 2.
     
    double leftJoystick = -1 * Robot.m_oi.getDriverLeftY();
    double rightJoystickY = Robot.m_oi.getDriverRightY();

    if(toggleOn){

    if (leftJoystick < .01 && leftJoystick > -.01)
      leftJoystick = 0;
    errorL = leftJoystick - turnAmount;
    if (leftJoystick > .01 || leftJoystick < -.01 )
      turnAmount = turnAmount + (errorL * Kpl);
    turnAmount = turnAmount + (errorL * Kpl);
    if (turnAmount < .01 && turnAmount > -.01)
      turnAmount = 0;

    if (rightJoystick < .095 && rightJoystick > -.095)
      rightJoystick = 0;
    errorR = rightJoystick - totalOutput;
    totalOutput = totalOutput + (errorR * Kpl);
    if (totalOutput < .01 && totalOutput > -.01)
      totalOutput = 0;
    leftOutput = totalOutput + turnAmount;
    rightOutput = totalOutput - turnAmount;

      
    } else {
      if (leftJoystick < .008 && leftJoystick > -.008)
      leftJoystick = 0;
    errorL = leftJoystick - leftOutput;
    leftOutput = leftOutput + (errorL * Kpl);
    if (leftOutput < .01 && leftOutput > -.01)
      leftOutput = 0;

    if (rightJoystickY < .008 && rightJoystickY > -.008)
      rightJoystickY = 0;
    errorR = rightJoystickY - rightOutput;
    rightOutput = rightOutput + (errorR * Kpl);
    if (rightOutput < .01 && rightOutput > -.01)
      rightOutput = 0;

    }
      System.out.println("left" + leftOutput);
      System.out.println("right" + rightOutput);

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
