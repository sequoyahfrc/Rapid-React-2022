// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {

	private final WPI_VictorSPX clawLeft, clawRight;
	private final WPI_TalonSRX clawRotator;
	private final Solenoid shooterSolenoid;

	public IntakeSubsystem(int clawLeftID, int clawRightID, int rotatorID, int shooterSolenoidID) {
		clawLeft = new WPI_VictorSPX(clawLeftID);
		clawRight = new WPI_VictorSPX(clawRightID);
		clawRotator = new WPI_TalonSRX(rotatorID);
		shooterSolenoid = new Solenoid(0, PneumaticsModuleType.CTREPCM, shooterSolenoidID);
		clawLeft.setInverted(true);
		clawRight.setInverted(false);
		clawRotator.setInverted(true);
		clawRotator.setNeutralMode(NeutralMode.Brake);
		setDefaultCommand(new InstantCommand(() -> {
			SmartDashboard.putString("DB/String 1", "Claw: " + getEncoder());
			SmartDashboard.putString("DB/String 2", "CA : " + getAngle());
		}, this).perpetually());
	}

	public void setClaw(double speed) {
		clawLeft.set(speed);
		clawRight.set(-speed);
	}

	public void setClawRotator(double speed) {
		clawRotator.set(speed);
	}

	public void stopAll() {
		setClaw(0);
		setClawRotator(0);
	}

	public void setShooterSolenoid(boolean v) {
		shooterSolenoid.set(v);
	}

	public double getEncoder() {
		return clawRotator.getSelectedSensorPosition();
	}

	public void resetEncoder() {
		clawRotator.setSelectedSensorPosition(0);
	}

	public double getAngle() {
		return getEncoder() / Constants.CTRE_MAG_ENCODER_RAW_TO_ROTATIONS * 360.0;
	}

	public int isInAngleRange(double target, double error) {
		double angle = getAngle();
		double x = target - angle;
		if (Math.abs(x) < error) {
			return 0;
		}
		return (int) Math.copySign(1, x);
	}
}
