// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.Drivetrain_GyroStraight;
import frc.robot.commands.Flipper_SetPosition;
import frc.robot.commands.Roller_On;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PathPlannerLeftBlue extends SequentialCommandGroup {
  /** Creates a new PathPlannerLeftBlue. */
  public PathPlannerLeftBlue() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    Command gotonote = new PathPlannerAuto("Go To Left Note");
    var flipout = new Flipper_SetPosition(RobotMap.flipperOutPos);
    var intakeOn = new Roller_On(-1);
    var driveForward = new Drivetrain_GyroStraight(.3, 0.8, 0.8);
    var intakeOff = new Roller_On(0);
    var flipin = new Flipper_SetPosition(0);

    addCommands(
    gotonote,
    flipout,
    intakeOn,
    driveForward,
    intakeOff,
    flipin
    );
  }
}
