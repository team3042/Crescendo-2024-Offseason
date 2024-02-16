// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.lib.Log;
import frc.robot.OI;
import frc.robot.subsystems.Drivetrain;

public class DriveCommand extends Command {
  /** Creates a new DriveCommand. */
  Drivetrain drivetrain = Robot.drivetrain;
  OI oi = Robot.oi;

  public DriveCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double ySpeed = oi.getYSpeed();
		double xSpeed = oi.getXSpeed();
		double zSpeed = oi.getZSpeed();

		drivetrain.drive(xSpeed, ySpeed, zSpeed, true); // Drive the robot with the specified speeds
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.stopModules(); // Stop all modules when the command ends
  }
  
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false; // This command never ends unless it's interrupted by another drivetrain command
  }
}
