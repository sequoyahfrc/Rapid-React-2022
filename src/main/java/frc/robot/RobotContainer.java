// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

public class RobotContainer {

	private final IntakeSubsystem intakeSubsystem;
	private final DriveSubsystem<?> driveSubsystem;
	private XboxController controller;

	public RobotContainer() {
		intakeSubsystem = new IntakeSubsystem();
		driveSubsystem = new DriveSubsystem<WPI_TalonFX>(3, 1, 0, 2, WPI_TalonFX::new);
		controller = new XboxController(0);
		configureButtonBindings();
		configureDefaultCommands();
	}

	private void configureDefaultCommands() {
		driveSubsystem.setDefaultCommand(new DriveCommand(driveSubsystem, controller));
	}

	@SuppressWarnings({ "unused" }) // Unused Buttons
	private void configureButtonBindings() {
		JoystickButton a = new JoystickButton(controller, XboxController.Button.kA.value);
		JoystickButton b = new JoystickButton(controller, XboxController.Button.kB.value);
		JoystickButton x = new JoystickButton(controller, XboxController.Button.kX.value);
		JoystickButton y = new JoystickButton(controller, XboxController.Button.kY.value);
		JoystickButton lb = new JoystickButton(controller, XboxController.Button.kLeftBumper.value);
		JoystickButton rb = new JoystickButton(controller, XboxController.Button.kRightBumper.value);
		JoystickButton ls = new JoystickButton(controller, XboxController.Button.kLeftStick.value);
		JoystickButton rs = new JoystickButton(controller, XboxController.Button.kRightStick.value);
		a.whenHeld(new ConsumerStartEndCommand<Double>(Constants.INTAKE_SPEED, 0.0, intakeSubsystem::setClaw,
				intakeSubsystem));
		b.whenHeld(new ConsumerStartEndCommand<Double>(Constants.SHOOT_SPEED, 0.0, intakeSubsystem::setClaw,
				intakeSubsystem));
		x.whenHeld(new ConsumerStartEndCommand<Double>(0.25, 0.0, intakeSubsystem::setClawRotator, intakeSubsystem));
		y.whenHeld(new ConsumerStartEndCommand<Double>(-0.25, 0.0, intakeSubsystem::setClawRotator, intakeSubsystem));
	}
}
