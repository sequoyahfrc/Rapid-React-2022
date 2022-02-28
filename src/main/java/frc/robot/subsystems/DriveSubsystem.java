// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem<TMotor extends MotorController> extends SubsystemBase {

	private final TMotor masterLeft, slaveLeft, masterRight, slaveRight;
	private final DifferentialDrive drive;

	public DriveSubsystem(int mlID, int slID, int mrID, int srID, MotorCreator<TMotor> factory) {
		masterLeft = factory.createMotor(mrID);
		slaveLeft = factory.createMotor(slID);
		masterRight = factory.createMotor(mrID);
		slaveRight = factory.createMotor(srID);

		drive = new DifferentialDrive(
				new MotorControllerGroup(masterLeft, slaveLeft),
				new MotorControllerGroup(masterRight, slaveRight));

		masterLeft.setInverted(true);
		slaveLeft.setInverted(true);
	}

	public void tankDrive(double left, double right) {
		drive.tankDrive(left, right);
	}

	// Creates a motor with a specified CAN id
	@FunctionalInterface
	public interface MotorCreator<TMotor> {
		public abstract TMotor createMotor(int canID);
	}
}
