// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.AbsoluteSensorRangeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.BetterSwerveModuleState;
import frc.lib.SwerveModuleConstants;
import frc.robot.Constants;
import frc.robot.Constants.ModuleConstants;

/** Add your docs here. */
public class SwerveModule {
    public int moduleNumber;
    private double angleOffset;
    private TalonFX angleMotor;
    private TalonFX driveMotor;
    private CANcoder angleEncoder;

    private StatusSignal<Double> drivePosition;
    private StatusSignal<Double> driveVelocity;
    private StatusSignal<Double> anglePosition;
    private StatusSignal<Double> angleVelocity;
    private BaseStatusSignal[] signals;

    private PositionVoltage angleSetter = new PositionVoltage(0);
    //private VelocityTorqueCurrentFOC driveSetter = new VelocityTorqueCurrentFOC(0);
    private VelocityVoltage driveSetter = new VelocityVoltage(0);

    private SwerveModulePosition internalState = new SwerveModulePosition();

    //Creates the Swerve modules Motors and Encoders
    //Relies on the moduleConstants class found in SwerveModuleConstants.java
    /**
     * Create and configure a new swerve module
     * @param moduleNumber Identifies which module it is
     * @param moduleConstants Constants for this module
     */
    public SwerveModule(int moduleNumber, SwerveModuleConstants moduleConstants) {
        this.moduleNumber = moduleNumber;
        angleOffset = moduleConstants.angleOffset;

        /* Angle Encoder Configuration */
        angleEncoder = new CANcoder(moduleConstants.cancoderID, Constants.DRIVETRAIN_CANBUS);
        configAngleEncoder();

        /* Angle Motor Configuration */
        angleMotor = new TalonFX(moduleConstants.angleMotorID, Constants.DRIVETRAIN_CANBUS);
        configAngleMotor(moduleConstants.cancoderID);

        /* Drive Motor Configuration */
        driveMotor = new TalonFX(moduleConstants.driveMotorID, Constants.DRIVETRAIN_CANBUS);
        configDriveMotor();

        drivePosition = driveMotor.getPosition();
        driveVelocity = driveMotor.getVelocity();
        anglePosition = angleMotor.getPosition();
        angleVelocity = angleMotor.getVelocity();

        signals = new BaseStatusSignal[4];
        signals[0] = drivePosition;
        signals[1] = driveVelocity;
        signals[2] = anglePosition;
        signals[3] = angleVelocity;
    }

    /**
     * Drive the module at a desired velocity and angle. Will not rotate module if velocity is too slow to move.
     * @param desiredState Desired velocity and angle of module
     * @param isOpenLoop If true it uses open loop control which sets the velocity using voltage. If false it uses closed loop which sets the velocity using a control method to vary motor power based on sensor velocity.
     */
    public void setDesiredState(SwerveModuleState desiredState){
        var optimize = SwerveModuleState.optimize(desiredState, internalState.angle);
        double angleToSet = optimize.angle.getRotations();
        angleMotor.setControl(angleSetter.withPosition(angleToSet));
        double velocityToSet = optimize.speedMetersPerSecond;
        SmartDashboard.putNumber(String.valueOf(moduleNumber) + "Request", velocityToSet);
        driveMotor.setControl(driveSetter.withVelocity(velocityToSet));
    }

    /**
     * Drive the module at a desired velocity and angle. Will not rotate module if velocity is too slow to move.
     * @param desiredState Desired velocity and angle of module
     * @param isOpenLoop If true it uses open loop control which sets the velocity using voltage. If false it uses closed loop which sets the velocity using a control method to vary motor power based on sensor velocity.
     */
    public void betterSetDesiredState(BetterSwerveModuleState desiredState, boolean isOpenLoop){
        var optimized = BetterSwerveModuleState.optimize(desiredState, internalState.angle);

        double angleToSetDeg = optimized.angle.getRotations();
        angleMotor.setControl(angleSetter.withPosition(angleToSetDeg));
        double velocityToSet = optimized.speedMetersPerSecond;
        driveMotor.setControl(driveSetter.withVelocity(velocityToSet));
    }

    /**
     * Stops the module from driving and turning when called
     */
    public void stop() {
        driveMotor.stopMotor();
        angleMotor.stopMotor();
    }

    /**
     * Configure settings for the angle encoder
     */
    private void configAngleEncoder() {
        angleEncoder.getConfigurator().apply(new CANcoderConfiguration());
        var angleEncoderConfigs = new CANcoderConfiguration();
        angleEncoderConfigs.MagnetSensor.MagnetOffset = angleOffset;
        angleEncoderConfigs.MagnetSensor.SensorDirection = ModuleConstants.canCoderInvert;
        angleEncoderConfigs.MagnetSensor.AbsoluteSensorRange = AbsoluteSensorRangeValue.Signed_PlusMinusHalf;
        angleEncoder.getConfigurator().apply(angleEncoderConfigs);
    }

    /**
     * Configure settings for the steering motor
     */
    private void configAngleMotor(int cancoderID) {
        angleMotor.getConfigurator().apply(new TalonFXConfiguration());
        var talonfxConfigs = new TalonFXConfiguration();
        talonfxConfigs.Slot0.kV = 0.0;
        talonfxConfigs.Slot0.kS = 0.0;
        talonfxConfigs.Slot0.kP = ModuleConstants.angleKP;
        talonfxConfigs.Slot0.kI = ModuleConstants.angleKI;
        talonfxConfigs.Slot0.kD = ModuleConstants.angleKD;
        talonfxConfigs.ClosedLoopGeneral.ContinuousWrap = true;
        talonfxConfigs.CurrentLimits.SupplyCurrentLimit = ModuleConstants.angleContinuousCurrentLimit;
        talonfxConfigs.CurrentLimits.SupplyCurrentLimitEnable = ModuleConstants.angleEnableCurrentLimit;
        talonfxConfigs.CurrentLimits.SupplyCurrentThreshold = ModuleConstants.anglePeakCurrentLimit;
        talonfxConfigs.CurrentLimits.SupplyTimeThreshold = ModuleConstants.anglePeakCurrentDuration;
        talonfxConfigs.Voltage.PeakForwardVoltage = 10;
        talonfxConfigs.Voltage.PeakReverseVoltage = -10;
        talonfxConfigs.Feedback.SensorToMechanismRatio = ModuleConstants.angleGearRatio;
        //talonfxConfigs.Feedback.FeedbackRemoteSensorID = cancoderID;
        //talonfxConfigs.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.FusedCANcoder;
        //talonfxConfigs.Feedback.RotorToSensorRatio = ModuleConstants.angleGearRatio;
        //talonfxConfigs.MotorOutput.NeutralMode = ModuleConstants.angleNeutralMode;
        talonfxConfigs.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        talonfxConfigs.MotorOutput.Inverted = ModuleConstants.angleMotorInvert;
        angleMotor.getConfigurator().apply(talonfxConfigs);
        Timer.delay(0.5);
        angleMotor.setRotorPosition(angleEncoder.getAbsolutePosition().refresh().getValue());
    }

    /**
     * Configure settings for the drive motor
     */
    private void configDriveMotor() {
        driveMotor.getConfigurator().apply(new TalonFXConfiguration());
        var talongfxConfigs = new TalonFXConfiguration();
        talongfxConfigs.Slot0.kV = ModuleConstants.driveKV;
        talongfxConfigs.Slot0.kS = ModuleConstants.driveKS;
        talongfxConfigs.Slot0.kP = ModuleConstants.driveKP;
        talongfxConfigs.Slot0.kI = ModuleConstants.driveKI;
        talongfxConfigs.Slot0.kD = ModuleConstants.driveKD;
        talongfxConfigs.CurrentLimits.SupplyCurrentLimit = ModuleConstants.driveContinuousCurrentLimit;
        talongfxConfigs.CurrentLimits.SupplyCurrentLimitEnable = ModuleConstants.driveEnableCurrentLimit;
        talongfxConfigs.CurrentLimits.SupplyCurrentThreshold = ModuleConstants.drivePeakCurrentLimit;
        talongfxConfigs.CurrentLimits.SupplyTimeThreshold = ModuleConstants.drivePeakCurrentDuration;
        talongfxConfigs.MotorOutput.Inverted = ModuleConstants.driveMotorInvert;
        //talongfxConfigs.MotorOutput.NeutralMode = ModuleConstants.driveNeutralMode;
        talongfxConfigs.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        talongfxConfigs.Feedback.SensorToMechanismRatio = ModuleConstants.rotationsPerMeter;
        driveMotor.getConfigurator().apply(talongfxConfigs);
        driveMotor.setRotorPosition(0.0);
    }

    /**
     * SwerveModulePosition is an object which contains the modules position and modules angle
     * @return The current position of the module
     */
    public SwerveModulePosition getPosition(boolean refresh) {
        if(refresh) {
            drivePosition.refresh();
            driveVelocity.refresh();
            anglePosition.refresh();
            angleVelocity.refresh();
        }
        
        double driveRotations = BaseStatusSignal.getLatencyCompensatedValue(drivePosition, driveVelocity);
        double angleRotations = BaseStatusSignal.getLatencyCompensatedValue(anglePosition, angleVelocity);

        double distance = driveRotations; // ModuleConstants.rotationsPerMeter;
        internalState.distanceMeters = distance;
        Rotation2d angle = Rotation2d.fromRotations(angleRotations);
        internalState.angle = angle;
        
        return internalState;
    }

    public SwerveModuleState getState(boolean refresh) {
        if(refresh) {
            driveVelocity.refresh();
            anglePosition.refresh();
        }

        return new SwerveModuleState(driveVelocity.getValue(), Rotation2d.fromRotations(anglePosition.getValue()));
    }

    public BaseStatusSignal[] getSignals() {
        return signals;
    }

    public Rotation2d getEncoderAngle() {
        return Rotation2d.fromRotations(angleEncoder.getAbsolutePosition().refresh().getValue());
    }

    public Rotation2d getMotorAngle() {
        return Rotation2d.fromRotations(angleMotor.getPosition().refresh().getValue());
    }

    public double getMotorSpeed() {
        return driveMotor.getVelocity().refresh().getValue();
    }
}