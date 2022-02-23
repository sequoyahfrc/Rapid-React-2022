// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

	private final MotorController clawLeft, clawRight, clawRotator;

	public IntakeSubsystem() {
		clawLeft = new WPI_VictorSPX(4);
		clawRight = new WPI_VictorSPX(5);
		clawRotator = new WPI_VictorSPX(6);
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
}
