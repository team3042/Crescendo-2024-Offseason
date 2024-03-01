// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Flipper_Up extends Command {

  double flipperPos;
  boolean flipGoalReached;
  /** Creates a new Flipper_Up. */
  public Flipper_Up(double position) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.intake);

    flipperPos = position;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    boolean up = Robot.intake.flipLimitSwitch.get();

    if(up){// add or to include new idea
      Robot.intake.setFlipperPower(0);

    } else {
      Robot.intake.setFlipperPower(0);
    }

    SmartDashboard.putNumber("Flipper Position Goal: ", flipperPos);
    SmartDashboard.putNumber("Flipper Position Actual: ", Robot.intake.getFlipMotorPosition());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    //Stops flipper motor if function is interrupted
    Robot.intake.setFlipperPower(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
