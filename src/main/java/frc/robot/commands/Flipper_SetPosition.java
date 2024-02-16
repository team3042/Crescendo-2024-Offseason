// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotMap;
import frc.robot.subsystems.Intake;

public class Flipper_SetPosition extends Command {

  Intake intake = new Intake();

  public double FlipperGoal;
  public double FlipPositionError;

  /** Creates a new Flipper_SetPosition. */
  public Flipper_SetPosition(double flipperGoal) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake);

    FlipperGoal = flipperGoal;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    FlipPositionError = FlipperGoal - intake.getFlipMotorPosition();

    boolean flipGoalReached = Math.abs(FlipPositionError) < RobotMap.flipperThreshold;

    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
