// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.Drivetrain_GyroStraight;
import frc.robot.commands.Flipper_SetPosition;
import frc.robot.commands.Intake_SetPower;
import frc.robot.commands.Launcher_SetPower;
import frc.robot.commands.Roller_On;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootTwiceNear extends SequentialCommandGroup {
  /** Creates a new Shootand1NoteAutoMiddle. */
  public ShootTwiceNear() {
    Launcher launcher = Robot.launcher;
    Intake intake = Robot.intake;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    Command shoot = new InstantCommand(launcher::startShooterAuto, launcher);
    Command wait = new WaitCommand(1);
    Command shoot2 = new InstantCommand(launcher::startShooterAuto, launcher);
    Command sendNote = new InstantCommand(intake::intakeSpinOutAuto, intake);
    Command sendNote2 = new InstantCommand(intake::intakeSpinOutAuto, intake);
    Command drive = new Drivetrain_GyroStraight(1, 0.8, 0);
    Command drive2 = new Drivetrain_GyroStraight(1, 0.8, 0);
    Command stopMotors = new SequentialCommandGroup(new InstantCommand(launcher::stopShooter, launcher), new InstantCommand(intake::stopIntakeSpin, intake));
    Command stopMotors2 = new SequentialCommandGroup(new InstantCommand(launcher::stopShooter, launcher), new InstantCommand(intake::stopIntakeSpin, intake));
    Command flipOut = new Flipper_SetPosition(RobotMap.flipperOutPos);
    Command intakeOn = new Intake_SetPower(-1);
    Command intakeNote = new ParallelCommandGroup(intakeOn, drive);
    Command shootNotePosition = new SequentialCommandGroup( new InstantCommand(intake::stopIntakeSpin, intake), new Flipper_SetPosition(0));
    Command driveback = new Drivetrain_GyroStraight(-1, 0.8, 0);
    
    addCommands(
    shoot, 
    wait,
    sendNote,
    new WaitCommand(1),
    stopMotors, 
    flipOut,
    intakeNote,
    shootNotePosition,
    driveback,
    shoot2,
    new WaitCommand(0.5),
    sendNote2,
    stopMotors2,
    drive2
    );
  }
}
