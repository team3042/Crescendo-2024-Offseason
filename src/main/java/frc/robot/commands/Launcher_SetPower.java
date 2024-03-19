// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

public class Launcher_SetPower extends Command {

  double speed;
  /** Creates a new Launcher_SetPower. */
  public Launcher_SetPower(double power) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.launcher);
    speed = power;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // if(Robot.intake.getFlipMotorPosition() <= RobotMap.flipperThreshold){
    //   Robot.intake.intakeSpin(-0.5);
      Robot.launcher.startShooter(speed);
    // }


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
