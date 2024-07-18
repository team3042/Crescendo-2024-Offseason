// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.commands.Drivetrain_GyroStraight;
import frc.robot.commands.Drivetrain_GyroTurn;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Shoot_DriveOut_Far extends SequentialCommandGroup {

  
  /** Creates a new Shoot_DriveOut_Far. */
  public Shoot_DriveOut_Far() {
    Intake intake = Robot.intake;
    Launcher launcher = Robot.launcher;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    Command shoot = new InstantCommand(launcher::startShooterAuto, launcher);
    Command sendNote = new InstantCommand(intake::intakeSpinOutAuto, intake);
    Command drive = new Drivetrain_GyroStraight(0.9, 1, 0);
    Command drive2 = new Drivetrain_GyroStraight(1, 1, 0);
    Command turn1 = new Drivetrain_GyroTurn(60);
    Command wait = new WaitCommand(0.5);
    Command stopMotors = new SequentialCommandGroup(new InstantCommand(launcher::stopShooter, launcher), new InstantCommand(intake::stopIntakeSpin, intake));
    
    addCommands(shoot, wait, sendNote, new WaitCommand(1), stopMotors, drive, turn1, drive2);
  }
}
