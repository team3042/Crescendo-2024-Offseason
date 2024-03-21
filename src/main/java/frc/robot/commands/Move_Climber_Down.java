// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

public class Move_Climber_Down extends Command {

  double Power;
  /** Creates a new Climber_SetPosition. */
  public Move_Climber_Down(double power) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.climber);
    Power = power;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if(Power <= 0 && (Robot.climber.getLeftClimberCounts() <= 5 && Robot.climber.getRightClimberCounts() <= 5)){

      Robot.climber.moveClimberDown(0);
    } else {

      Robot.climber.moveClimberDown(Power);
    }

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
