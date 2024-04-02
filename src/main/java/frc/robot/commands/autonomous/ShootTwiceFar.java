// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotMap;
import frc.robot.commands.Drivetrain_GyroStraight;
import frc.robot.commands.Flipper_SetPosition;
import frc.robot.commands.Intake_SetPower;
import frc.robot.commands.Launcher_SetPower;
import frc.robot.commands.Roller_On;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootTwiceFar extends SequentialCommandGroup {
  /** Creates a new ShootTwiceFar. */
  public ShootTwiceFar() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
   Command startLauncher = new Launcher_SetPower(0.8);
    Command sendNote = new Intake_SetPower(-0.4);
    Command drive = new Drivetrain_GyroStraight(2, 0.8, 0);
    Command wait = new WaitCommand(1);
    Command stopMotors = new SequentialCommandGroup(new Launcher_SetPower(0), new Intake_SetPower(0));
    Command intakeNotePosition = new SequentialCommandGroup(new Flipper_SetPosition(RobotMap.flipperOutPos), new Roller_On(-1));
    Command shootNotePosition = new SequentialCommandGroup( new Roller_On(0), new Flipper_SetPosition(0));
    Command driveback = new Drivetrain_GyroStraight(-3, 0.8, 0);
    
    addCommands(
    startLauncher, 
    new WaitCommand(0.5),
    sendNote,
    stopMotors, 
    intakeNotePosition,
    drive,
    shootNotePosition,
    driveback,
    startLauncher,
    sendNote,
    stopMotors,
    drive
    );
  }
}
