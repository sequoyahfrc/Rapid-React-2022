// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.FlexibleMotor;

public class DriveSubsystem extends SubsystemBase {

	private final FlexibleMotor<?> masterLeft, slaveLeft, masterRight, slaveRight;
	private final DifferentialDrive drive;
	private double multiplier = 1.0;

	public DriveSubsystem(int mlID, int slID, int mrID, int srID, MotorCreator<FlexibleMotor<?>> factory) {
		masterLeft = factory.createMotor(mrID);
		slaveLeft = factory.createMotor(slID);
		masterRight = factory.createMotor(mrID);
		slaveRight = factory.createMotor(srID);

		drive = new DifferentialDrive(
			new MotorControllerGroup(masterLeft.getBaseController(), slaveLeft
				.getBaseController()),
			new MotorControllerGroup(masterRight.getBaseController(), slaveRight.getBaseController()));

		final double LIMIT = 45;
		masterLeft.limitCurrent(LIMIT);
		slaveLeft.limitCurrent(LIMIT);
		masterRight.limitCurrent(LIMIT);
		slaveRight.limitCurrent(LIMIT);

		masterLeft.getBaseController().setInverted(true);
		slaveLeft.getBaseController().setInverted(true);
		masterRight.getBaseController().setInverted(false);
		slaveRight.getBaseController().setInverted(false);
	}

	public void tankDrive(double left, double right) {
		drive.tankDrive(left, right);
	}

	public void setBothSides(double speed) {
		tankDrive(speed, speed);
	}

	// Creates a motor with a specified CAN id
	@FunctionalInterface
	public interface MotorCreator<T> {
		public abstract T createMotor(int canID);
	}

	public void setMultiplier(double m) {
		multiplier = m;
	}

	public double getMultipler() {
		return multiplier;
	}
}
