// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Drivetrain_GyroStraight;
import frc.robot.commands.Intake_SetPower;
import frc.robot.commands.Launcher_SetPower;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Shoot_AndDriveOut extends SequentialCommandGroup {
  /** Creates a new Shoot_AndDriveOut. */
  public Shoot_AndDriveOut() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    Command shoot = new Launcher_SetPower(0.8);
    Command sendNote = new Intake_SetPower(-0.4);
    Command drive = new Drivetrain_GyroStraight(-1.21, 0.8, 0);
    Command wait = new WaitCommand(2);
    Command stopMotors = new SequentialCommandGroup(new Launcher_SetPower(0), new Intake_SetPower(0));
    
    addCommands(shoot, wait, sendNote, stopMotors, drive);
  }
}
