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
import frc.robot.commands.UseIntake;

/**
 * Add your docs here.
 */
public class Intake extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  CANSparkMax intake;
  Solenoid solenoid;

  public Intake(CANSparkMax _intake, Solenoid _solenoid) {
    super("Intake");
    intake = _intake;
    solenoid = _solenoid;
  }

  public void moveIntake(double speed) {

    if (speed > 0) {
      intake.set(1);
    }

  }

  public void setShift(boolean pos) {
    solenoid.set(pos);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new UseIntake(this));
  }
}