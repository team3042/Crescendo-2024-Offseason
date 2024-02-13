// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//q: explain the purpose of the code in this class
 


package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase {

    private final CANSparkMax intakeMotor;
    private final CANSparkMax flipMotor;
    Timer timer = new Timer();

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

  public void intakeSpin() { 
    intakeMotor.set(0.5); //TODO: might need changing, how fast intake runs!!
  }

  public void stopIntakeSpin() {
    intakeMotor.set(0);
  }

  public void shooterIntakeSpin() {
    intakeMotor.set(-0.1);
    timer.hasElapsed(0.2);
    intakeMotor.set(0);
  }

  public void flipIntakeUp() {

    intakeMotor.set(0);
    if(flipLimitSwitch.get() == false) {

      flipMotor.set(RobotMap.flipSpeed); //TODO: edit, maybe reverse?
    }
    
  }

    public void flipIntakeDown() {

      while(flipMotor.getEncoder().getPosition() > 0) {
         
        flipMotor.set(RobotMap.flipSpeed); //TODO: edit, maybe reverse?
      }

    intakeMotor.set(0.5);
    
  }

  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }
}
