// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

	private final MotorController clawLeft, clawRight;
	private final WPI_TalonSRX clawRotator;
	private final Solenoid shooterSolenoid, clawSolenoid;

	public IntakeSubsystem(int clawLeftID, int clawRightID, int rotatorID, int shooterSolenoidID, int clawSolenoidID) {
		clawLeft = new WPI_VictorSPX(clawLeftID);
		clawRight = new WPI_VictorSPX(clawRightID);
		clawRotator = new WPI_TalonSRX(rotatorID);
		shooterSolenoid = new Solenoid(0, PneumaticsModuleType.CTREPCM, shooterSolenoidID);
		clawSolenoid = new Solenoid(0, PneumaticsModuleType.CTREPCM, clawSolenoidID);
		clawLeft.setInverted(true);
		clawRight.setInverted(true);
		clawRotator.setInverted(true);
	}

	public void setClaw(double speed) {
		clawLeft.set(speed);
		clawRight.set(speed);
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

	public void setClawSolenoid(boolean v) {
		clawSolenoid.set(v);
	}
}
