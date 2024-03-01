// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.lib.Log;
import frc.robot.Robot;
import frc.robot.RobotMap;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class FlipIntake extends InstantCommand {

  double flipperPos;
  /** Creates a new Flipper_Up. */
  public FlipIntake(double position) {
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
    //Getting distance between current position and desired position
    double flipError = flipperPos - Robot.intake.getFlipMotorPosition();

    //checks whether we are close enough
    boolean flipGoalReached = Math.abs(flipError) < RobotMap.kFlipThreshold;
    double power = 0;

    // if we aren't close enough, run the motor.
    if(!flipGoalReached){
      power = Math.copySign(0.5, flipError);

      Robot.intake.setFlipperPower(power);

    }
      // Otherwise, hold the current position
    Robot.intake.setFlipperPower(power);

    SmartDashboard.putNumber("Flipper Power: ", power);
    SmartDashboard.putNumber("Flipper Error: ", flipError);
    SmartDashboard.putNumber("Flipper Pos: ", flipperPos);


    }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    Robot.intake.setFlipperPower(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

}



