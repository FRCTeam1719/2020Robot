/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.UseIntake;
import frc.robot.commands.UseWinch;

/**
 * Add your docs here.
 */
public class Winch extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  CANSparkMax motor;
  DigitalInput upperSwitch;
  DigitalInput lowerSwitch;

  public Winch(CANSparkMax motor, DigitalInput upperSwitch, DigitalInput lowerSwitch) {
    super("Winch");
    this.motor = motor;

    this.upperSwitch = upperSwitch;
    this.lowerSwitch = lowerSwitch;

  }

  public boolean atTop() {
    return upperSwitch.get();
  }

  public boolean atBottom() {
    return lowerSwitch.get();
  }

  public void moveWinch(double speed) {
    if (speed > .25) {
      motor.set(.25);
    }

    if (speed < -.25) {
      motor.set(-.25);
    }

    else {
      if (speed > -.05 && speed < .05) {
        motor.set(.025);
      } else
        motor.set(speed);
    }

  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new UseWinch(this));
  }
}
