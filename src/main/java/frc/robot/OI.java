// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.lib.Log;
import frc.robot.commands.Drivetrain_XStance;    
import frc.robot.commands.Flipper_SetPosition;
import frc.robot.commands.Intake_SetPower;
import frc.robot.commands.Launcher_SetPower;
import frc.robot.commands.Move_Climber_Down;
import frc.robot.commands.Move_Climber_Up;
import frc.robot.commands.Reset_Flipper;
import frc.robot.commands.Zero_Climber;
import edu.wpi.first.wpilibj.GenericHID;
// import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class OI {

  public static final double JOYSTICK_DRIVE_SCALE = RobotMap.JOYSTICK_DRIVE_SCALE;
	public static final double JOYSTICK_DRIVE_SCALE_LOW = RobotMap.JOYSTICK_DRIVE_SCALE_LOW;
  public static boolean isLowScale = false;

  Log log = new Log(RobotMap.LOG_OI, "OI");

  public static double CURRENT_DRIVE_SCALE = JOYSTICK_DRIVE_SCALE;
  public static final GenericHID driverController = new GenericHID(RobotMap.DRIVER_XBOX_USB_PORT);
  public static final GenericHID gunnerController = new GenericHID(RobotMap.GUNNER_XBOX_USB_PORT);

  public boolean getLeftJoyStickUp(GenericHID gunnerController){
	return gunnerController.getRawAxis(RobotMap.LEFT_VERTICAL_JOYSTICK_AXIS) >= 0.5;
  }

  public boolean getRightJoyStickUp(GenericHID gunnerController){
	return gunnerController.getRawAxis(RobotMap.RIGHT_VERTICAL_JOYSTICK_AXIS) >= 0.5;
  }

  public boolean getLeftJoyStickDown(GenericHID gunnerController){
	return gunnerController.getRawAxis(RobotMap.LEFT_VERTICAL_JOYSTICK_AXIS) <= -0.5;
  }

  public boolean getRightJoyStickDown(GenericHID gunnerController){
	return gunnerController.getRawAxis(RobotMap.RIGHT_VERTICAL_JOYSTICK_AXIS) <= -0.5;
  }

  public boolean getRightTrigger(GenericHID gunnerController){
		return gunnerController.getRawAxis(RobotMap.RIGHT_TRIGGER_AXIS) >= 0.95 ;
	}


  public OI() {
    configureBindings();
 
    /* Drivetrain actions */
		new Trigger(() -> driverController.getRawButton(RobotMap.RIGHT_BUMPER)).onTrue(new InstantCommand(Robot.drivetrain::zeroGyro, Robot.drivetrain)); // Zero the gyro, this is helpful at the start of a match for field-oriented driving
		new Trigger(() -> driverController.getRawButton(RobotMap.X_BUTTON)).onTrue(new Drivetrain_XStance()); // Defensive X-stance command
		new Trigger(() -> driverController.getRawButton(RobotMap.LEFT_BUMPER)).onTrue(new InstantCommand(() -> toggleScale())); // SlowMode command
	
		/* Intake actions */
		new Trigger(() -> gunnerController.getRawButton(RobotMap.RIGHT_BUMPER)).onTrue(new Flipper_SetPosition(RobotMap.flipperOutPos));
		new Trigger(() -> gunnerController.getRawButton(RobotMap.LEFT_BUMPER)).onTrue(new Flipper_SetPosition(0));
		new Trigger(() -> gunnerController.getRawButton(RobotMap.A_BUTTON)).onTrue(new Intake_SetPower(1));//0.6
		new Trigger(() -> gunnerController.getRawButton(RobotMap.A_BUTTON)).onFalse(new Intake_SetPower(0) );
		new Trigger(() -> gunnerController.getRawButton(RobotMap.Y_BUTTON)).onTrue(new Intake_SetPower(-1));
		new Trigger(() -> gunnerController.getRawButton(RobotMap.Y_BUTTON)).onFalse(new Intake_SetPower(0));
		new Trigger(() -> gunnerController.getRawButton(RobotMap.START_BUTTON)).onTrue(new Reset_Flipper());

	/*Launcher actions */
		new Trigger(() -> gunnerController.getRawButton(RobotMap.B_BUTTON)).onTrue(new Launcher_SetPower(0.8)); // for speaker
		new Trigger(() -> gunnerController.getRawButton(RobotMap.B_BUTTON)).onFalse(new Launcher_SetPower(0));
		new Trigger(() -> gunnerController.getRawButton(RobotMap.X_BUTTON)).onTrue(new Launcher_SetPower(0.3));
		new Trigger(() -> gunnerController.getRawButton(RobotMap.PREV_BUTTON)).onTrue(new Launcher_SetPower(-0.2));
		new Trigger(() -> gunnerController.getRawButton(RobotMap.PREV_BUTTON)).onFalse(new Launcher_SetPower(0));
		new Trigger(() -> gunnerController.getRawButton(RobotMap.X_BUTTON)).onFalse(new Launcher_SetPower(0));

	/*Climber actions */
		new Trigger(() -> driverController.getRawButton(RobotMap.START_BUTTON)).onTrue(new Move_Climber_Up(0.5));
		new Trigger(() -> driverController.getRawButton(RobotMap.START_BUTTON)).onFalse(new Move_Climber_Up(0));
		new Trigger(() -> driverController.getRawButton(RobotMap.PREV_BUTTON)).onTrue(new Move_Climber_Down(0.5));
		new Trigger(() -> driverController.getRawButton(RobotMap.PREV_BUTTON)).onFalse(new Move_Climber_Down(0));
		new Trigger(() -> driverController.getRawButton(RobotMap.Y_BUTTON)).onTrue(new Zero_Climber());

	}  
   
  public void setNormalScale() {
		CURRENT_DRIVE_SCALE = JOYSTICK_DRIVE_SCALE;
		isLowScale = false;
	}
	public void setLowScale(){
		CURRENT_DRIVE_SCALE = JOYSTICK_DRIVE_SCALE_LOW;
		isLowScale = true;
	}
	public void toggleScale() {

		if (isLowScale){

			setNormalScale();
		} else {

			setLowScale();
		}
	}

  public double getXSpeed() {
		double joystickValue = driverController.getRawAxis(1);
		// double joystickValue = driverController.getRightY(); // this would be for a flight joystick
		if (Math.abs(joystickValue) < 0.07) { // This is our deadband
			return 0.0;
		}
		else {
			return joystickValue * RobotMap.kPhysicalMaxSpeedMetersPerSecond * CURRENT_DRIVE_SCALE * 1; // Multiply by -1 reverses the direction
		}	
	}
	public double getYSpeed() {
		// double joystickValue = joyRight.getX(); // this would be for a flight joystick
		double joystickValue = driverController.getRawAxis(0);
		if (Math.abs(joystickValue) < 0.07) { // This is our deadband
			return 0.0;
		}
		else {
			return joystickValue * RobotMap.kPhysicalMaxSpeedMetersPerSecond * CURRENT_DRIVE_SCALE * 1; // Multiply by -1 reverses the direction, 0.5 to reduce speed
		}	
	}
	public double getZSpeed() {
		// double joystickValue = joyLeft.getX(); // this would be for a flight joystick
		double joystickValue = driverController.getRawAxis(4);
		if (Math.abs(joystickValue) < 0.07) { // This is our deadband
			return 0.0;
		}
		else {
			return joystickValue * RobotMap.kPhysicalMaxTurningSpeedRadiansPerSecond * CURRENT_DRIVE_SCALE * -1; // Multiply by -1 reverses the direction
		}	
	}


  private void configureBindings() {}

}

