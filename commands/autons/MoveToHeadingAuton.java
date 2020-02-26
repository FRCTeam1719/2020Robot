/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autons;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.MoveToHeading;
import frc.robot.commands.ShakeBot;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Winch;

public class MoveToHeadingAuton extends CommandGroup {
  /**
   * Add your docs here.
   */
  Drive drive;
  Servo servo;
  AnalogInput ultra;
  Winch winch;
  public MoveToHeadingAuton(Drive drive, Servo servo, AnalogInput ultra, Winch winch) {
    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.

    // To run multiple commands at the same time,
    // use addParallel()
    // e.g. addParallel(new Command1());
    // addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.
    this.drive = drive;
    this.servo = servo;
    this.ultra = ultra;
    this.winch = winch;
    requires(this.drive);
    requires(this.winch);

    addSequential(new ShakeBot(this.drive, this.winch));
    addSequential(new MoveToHeading(this.drive, this.servo, this.ultra, this.winch));
  }
}
