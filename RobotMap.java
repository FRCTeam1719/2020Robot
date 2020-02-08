/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;
  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
  public static CANSparkMax left1 = new CANSparkMax(5, MotorType.kBrushless);
  public static CANSparkMax left2 = new CANSparkMax(1, MotorType.kBrushless);
  public static CANSparkMax right1 = new CANSparkMax(2, MotorType.kBrushless);
  public static CANSparkMax right2 = new CANSparkMax(3, MotorType.kBrushless);

  // public static Solenoid driveShifter = new Solenoid(1);
  public static CANSparkMax intake = new CANSparkMax(6, MotorType.kBrushless);
  public static CANSparkMax winch = new CANSparkMax(4, MotorType.kBrushless);

  // PNEUMATICS
  public static Solenoid intakePiston = new Solenoid(0);

  // Hall effect sensors
  /*
   * DigitalInput winchUpperSwitch = new DigitalInput(1); DigitalInput
   * winchLowerSwitch = new DigitalInput(2);
   */

}
