// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase {

    private final CANSparkMax intakeMotor;
    private final CANSparkMax flipMotor;

    public final DigitalInput flipLimitSwitch;


  /** Creates a new Intake. */
  public Intake() {

    
    intakeMotor = new CANSparkMax(RobotMap.kIntakeMotorPort, MotorType.kBrushless);
    flipMotor = new CANSparkMax(RobotMap.kIntakeFlipPort, MotorType.kBrushless);
    
    flipLimitSwitch = new DigitalInput(0);

    intakeMotor.restoreFactoryDefaults();
    flipMotor.restoreFactoryDefaults();

    intakeMotor.setInverted(RobotMap.intakeMotorReversed);
    flipMotor.setInverted(RobotMap.flipMotorReversed);

    intakeMotor.setIdleMode(IdleMode.kCoast); //TODO: check if kCoast or kBrake affect shooter power
    flipMotor.setIdleMode(IdleMode.kCoast); //TODO: check if kCoast or kBrake affect shooter power

    intakeMotor.getEncoder().setPositionConversionFactor(42);
    flipMotor.getEncoder().setPositionConversionFactor(42);

  }

  public void intakeSpin(double speed) {
    intakeMotor.set(speed);
  }

  public void flipIntake(double speed) {

    while(flipLimitSwitch.get() == false) {
      flipMotor.set(speed);
    }
    flipMotor.set(0);
    
  }



  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }
}
