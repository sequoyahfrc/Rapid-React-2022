// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.Consumer;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem<TMotor extends MotorController> extends SubsystemBase {

	public final TMotor masterLeft, slaveLeft, masterRight, slaveRight;
	private final DifferentialDrive drive;
	private double multiplier = 1.0;

	public DriveSubsystem(int mlID, int slID, int mrID, int srID, MotorCreator<TMotor> factory,
		Consumer<TMotor> setToCoastMode) {
		masterLeft = factory.createMotor(mlID);
		slaveLeft = factory.createMotor(slID);
		masterRight = factory.createMotor(mrID);
		slaveRight = factory.createMotor(srID);

		drive = new DifferentialDrive(
			new MotorControllerGroup(masterLeft, slaveLeft),
			new MotorControllerGroup(masterRight, slaveRight));

		masterLeft.setInverted(true);
		slaveLeft.setInverted(true);
		masterRight.setInverted(false);
		slaveRight.setInverted(false);

		setToCoastMode.accept(masterLeft);
		setToCoastMode.accept(slaveLeft);
		setToCoastMode.accept(masterRight);
		setToCoastMode.accept(slaveRight);
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
