// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Flipper_SetPosition extends Command {

  public double FlipperGoal;
  public double FlipPositionError;

  /** Creates a new Flipper_SetPosition. */
  public Flipper_SetPosition(double flipperGoal) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.intake);

    FlipperGoal = flipperGoal;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    FlipPositionError = FlipperGoal - Robot.intake.getFlipMotorPosition();
    boolean flipGoalReached = Math.abs(FlipPositionError) < RobotMap.flipperThreshold;
    
    if(flipGoalReached){
      Robot.intake.setFlipperPower(0);
    } else if(!Robot.intake.flipLimitSwitch.get() && FlipperGoal == 0){

      Robot.intake.setFlipperPower(0);
    } else {
      Robot.intake.setFlipperPower(Math.copySign(0.4, FlipPositionError));
    }

    SmartDashboard.putNumber("Flipper Position Goal: ", FlipperGoal);
    SmartDashboard.putNumber("Flipper Position Actual: ", Robot.intake.getFlipMotorPosition());
    
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
