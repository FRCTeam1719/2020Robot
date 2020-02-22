/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.DriverCamera;
import frc.robot.commands.MoveToHeading;
import frc.robot.commands.ToggleWinch;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  public OI() {

  }

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());

  private Joystick driverJoystick = new Joystick(0);
  private Joystick operator = new Joystick(1);

  // public static int camera1Selected = 0;

  public void init(Robot robot) {

    Button toggleWinchButton = new JoystickButton(driverJoystick, 4);
    toggleWinchButton.whenPressed(new ToggleWinch(Robot.winch));

    Button followTargetButton = new JoystickButton(driverJoystick, 3);
    followTargetButton.toggleWhenPressed(new MoveToHeading(Robot.drive, RobotMap.cameraServo, RobotMap.ultrasonicSensor));

    Button driverCam = new JoystickButton(driverJoystick, 1);
    driverCam.whenReleased(new DriverCamera(RobotMap.cameraServo));

  }

  public double getDriverLeftY() {
    return driverJoystick.getRawAxis(1);
  }

  public double getDriverLeftX() {
    return driverJoystick.getRawAxis(0);
  }

  public double getDriverRightY() {
    return driverJoystick.getRawAxis(5);
  }

  public double getDriverRightX() {
    return driverJoystick.getRawAxis(4);
  }

  public double getOperatorRightY() {
    return operator.getRawAxis(5);
  }

  public double getOperatorLeftY() {
    return operator.getRawAxis(1);
  }
}
