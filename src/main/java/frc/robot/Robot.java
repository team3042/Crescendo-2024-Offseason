// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//hi this is yuji i shat myself

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.lib.Log;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.autonomous.AutonomousMode_Default;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private static final Log.Level LOG_LEVEL = RobotMap.LOG_ROBOT;
	private Log log = new Log(LOG_LEVEL, "Robot");


  /** Creating  Subsystems ********************************************/
  public static final Drivetrain drivetrain = new Drivetrain();
  public static final Intake intake = new Intake();
  public static final Launcher launcher = new Launcher();
  public static final Climber climber = new Climber();
  
  UsbCamera camera1;

  public static final OI oi = new OI();


  SendableChooser<Command> chooser = new SendableChooser<Command>();

  @Override
  public void robotInit() {
    
    log.add("Robot Init", Log.Level.TRACE);
    drivetrain.setDefaultCommand(new DriveCommand());

    drivetrain.zeroGyro();
    //TODO: reset encoders for all subsystems


    /* Autonomous Routines */

    chooser.setDefaultOption("Default", new AutonomousMode_Default());
    //add more auto options here
    SmartDashboard.putData(chooser);

    camera1 = CameraServer.startAutomaticCapture(0);
    camera1.setResolution(320, 240);
    camera1.setFPS(15);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {
    SmartDashboard.putNumber("FrontLeft Abs Encoder Counts", drivetrain.getFrontLeft().getAbsoluteEncoderRadians() * Math.PI);
    SmartDashboard.putNumber("FrontRight Abs Encoder Counts", drivetrain.getFrontRight().getAbsoluteEncoderRadians() * Math.PI);
    SmartDashboard.putNumber("BackLeft Abs Encoder Counts", drivetrain.getBackLeft().getAbsoluteEncoderRadians() * Math.PI);
    SmartDashboard.putNumber("BackRight Abs Encoder Counts", drivetrain.getBackRight().getAbsoluteEncoderRadians() * Math.PI);
    SmartDashboard.putString("BL State", drivetrain.getBackLeft().getState().toString());
    SmartDashboard.putString("BR State", drivetrain.getBackRight().getState().toString());
    SmartDashboard.putString("FL State", drivetrain.getFrontLeft().getState().toString());
    SmartDashboard.putString("FR State", drivetrain.getFrontRight().getState().toString());

    Robot.intake.resetEncoders();
  }

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = oi.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}
}
