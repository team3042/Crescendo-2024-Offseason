



// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Climber extends SubsystemBase {

    private final CANSparkMax leftClimber;
    private final CANSparkMax rightClimber;
  /** Creates a new Climber. */
  public Climber() {

    leftClimber = new CANSparkMax(RobotMap.kLeftHangMotor, MotorType.kBrushless);
    rightClimber = new CANSparkMax(RobotMap.kRightHangMotor, MotorType.kBrushless);

    leftClimber.setInverted(RobotMap.kleftClimberReversed);

    leftClimber.setIdleMode(IdleMode.kBrake);
    rightClimber.setIdleMode(IdleMode.kBrake);

  }

  public void resetEncoders(){

    leftClimber.getEncoder().setPosition(0);
    rightClimber.getEncoder().setPosition(0);
  }

  public void moveClimberUp(double power){

    leftClimber.set(power);
    rightClimber.set(power);
  }

  public void moveLeftClimber(double power){

    leftClimber.set(power);
  }

  public void moveRightClimber(double power){

    leftClimber.set(power);
  }

  public void moveClimberDown(double power){
    leftClimber.set(-power);
    rightClimber.set(-power);
  }

  public double getLeftClimberCounts(){
    return leftClimber.getEncoder().getPosition();

  }

  public double getRightClimberCounts(){
    return rightClimber.getEncoder().getPosition();

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
