package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class LambdaCommand extends InstantCommand {

  private final Runnable lambda;

  public LambdaCommand(Runnable lambda) {
    this.lambda = lambda;
  }

  @Override
  protected void execute() {
    lambda.run();
  }
}