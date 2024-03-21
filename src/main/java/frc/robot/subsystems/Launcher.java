// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Launcher extends SubsystemBase {

  private final CANSparkMax leftShooterMotor;
  private final CANSparkMax rightShooterMotor;

  /** Creates a new Launcher. */
  public Launcher() {

    leftShooterMotor = new CANSparkMax(RobotMap.kLeftShooterMotorPort, MotorType.kBrushless);
    rightShooterMotor = new CANSparkMax(RobotMap.kRightShooterMotorPort, MotorType.kBrushless);

    leftShooterMotor.restoreFactoryDefaults();
    rightShooterMotor.restoreFactoryDefaults();

    leftShooterMotor.setInverted(RobotMap.intakeMotorReversed);
    rightShooterMotor.setInverted(RobotMap.flipMotorReversed);

    leftShooterMotor.setIdleMode(IdleMode.kBrake);
    rightShooterMotor.setIdleMode(IdleMode.kBrake);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void startShooter(double speed) {
    // Robot.intake.flipIntakeUp();
    // Robot.intake.shooterIntakeSpin();
    leftShooterMotor.set(speed);
    rightShooterMotor.set(speed);
  }

  public void stopShooter() {

    leftShooterMotor.set(0);
    rightShooterMotor.set(0);
  }
}
