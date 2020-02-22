/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.UseDrive;

/**
 * Add your docs here.
 */
public class Drive extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private Solenoid shifter;

  CANSparkMax left1Controller;
  CANSparkMax left2Controller;
  CANSparkMax right1Controller;
  CANSparkMax right2Controller;

  public Drive(CANSparkMax _left1Controller, CANSparkMax _left2Controller, CANSparkMax _right1Controller,
      CANSparkMax _right2Controller, Solenoid driveShifter) {
    super("Drive");

    left1Controller = _left1Controller;
    left2Controller = _left2Controller;
    right1Controller = _right1Controller;
    right2Controller = _right2Controller;
    shifter = driveShifter;

  }

  @Override
  public void initDefaultCommand() {
    // setDefaultCommand(new UseDrive(this));
    setDefaultCommand(new UseDrive(this));
  }

  private double clamp(double val) {
    if (val < -1)
      return -1;
    else if (val > 1)
      return 1;
    else
      return val;
  }

  void leftSpeed(double speed) {
    left1Controller.set(speed);
    left2Controller.set(speed);
  }

  void rightSpeed(double speed) {
    right1Controller.set(speed);
    right2Controller.set(speed);
  }

  public void drive(double left, double right) {

    // driving without encoders
    leftSpeed(clamp(left));
    rightSpeed(clamp(right));
  }

  public void setShift(boolean shifted) {
    shifter.set(shifted);
  }

  public void toggleShift() {
    shifter.set(!shifter.get());
  }

}
