/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems; 

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Climber extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  CANSparkMax climber1;
  CANSparkMax climber2;
  public Climber(CANSparkMax climber1, CANSparkMax climber2) {
    this.climber1 = climber1;
    this.climber2 = climber2;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
  public void raise(double acceleration) {
    climber1.set(acceleration);
    climber2.set(acceleration);
  }
}
