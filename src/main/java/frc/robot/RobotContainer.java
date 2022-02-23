// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ConsumerStartEndCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class RobotContainer {

	private IntakeSubsystem intakeSubsystem;
	private XboxController controller;

	public RobotContainer() {
		intakeSubsystem = new IntakeSubsystem();
		controller = new XboxController(0);
		configureButtonBindings();
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
