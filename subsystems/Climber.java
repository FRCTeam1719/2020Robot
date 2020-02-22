/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems; 

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.UseClimber;

/**
 * Add your docs here.
 */
public class Climber extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  CANSparkMax climber;
  public Climber(CANSparkMax climber) { 
    super("Climber");

    this.climber = climber;
  }

  @Override
  public void initDefaultCommand() {
  setDefaultCommand(new UseClimber(this));
  }
  public void raise(double acceleration) {
    climber.set(acceleration);
  }
}
