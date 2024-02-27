// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//q: explain the purpose of the code in this class
 


package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase {

    private final CANSparkMax intakeMotor;
    private final CANSparkMax flipMotor;

    Timer timer = new Timer();

    public final DigitalInput flipLimitSwitch;
    public final DigitalInput intakeLimitSwitch;


  /** Creates a new Intake. */
  public Intake() {
    
    intakeMotor = new CANSparkMax(RobotMap.kIntakeMotorPort, MotorType.kBrushless);
    flipMotor = new CANSparkMax(RobotMap.kIntakeFlipPort, MotorType.kBrushless);
    
    flipLimitSwitch = new DigitalInput(9);
    intakeLimitSwitch = new DigitalInput(7);

    intakeMotor.restoreFactoryDefaults();
    flipMotor.restoreFactoryDefaults();

    intakeMotor.setInverted(RobotMap.intakeMotorReversed);
    flipMotor.setInverted(RobotMap.flipMotorReversed);

    intakeMotor.setIdleMode(IdleMode.kCoast); //TODO: check if kCoast or kBrake affect shooter power
    flipMotor.setIdleMode(IdleMode.kCoast); //TODO: check if kCoast or kBrake affect shooter power

    intakeMotor.getEncoder().setPositionConversionFactor(42);
    flipMotor.getEncoder().setPositionConversionFactor(42);


    
  }

  public double getFlipMotorPosition() {

    return flipMotor.getEncoder().getPosition();
  }

  public void resetEncoders() {
      
      intakeMotor.getEncoder().setPosition(0);
      flipMotor.getEncoder().setPosition(0);
  }

  public void setIntakeSpeed(double percentPower) { 

    if (intakeLimitSwitch.get()) {

      stopIntakeSpin();
    }
    else {

      intakeMotor.set(percentPower); //TODO: might need changing, depending on how fast intake runs!!
    }
  }

  public void stopIntakeSpin() {
    intakeMotor.set(0);
  }

  public void intakeSpin() {
    intakeMotor.set(0.5);
  }

  public void flipIntakeUp() {

    if (flipLimitSwitch.get()) {

      flipMotor.set(0);
    }
    else {

      flipMotor.set(0.5);
    }
  }

  public void flipIntakeDown() {

    if (flipLimitSwitch.get()) {

      flipMotor.set(0);
    }
    else {

      flipMotor.set(-0.5);
    }
  }



  public double getFlipperAngle(){

    //TODO: finish this method when we get the robot
    return 0;
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }
}
