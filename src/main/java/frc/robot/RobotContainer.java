// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ConsumerStartEndCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class RobotContainer {

	private final IntakeSubsystem intakeSubsystem;
	private final DriveSubsystem<?> driveSubsystem;
	private final ClimbSubsystem climbSubsystem;
	private XboxController controller;
	private Compressor compressor;

	public RobotContainer() {
		intakeSubsystem = new IntakeSubsystem(4, 5, 6);
		driveSubsystem = new DriveSubsystem<WPI_TalonFX>(3, 1, 8, 2, WPI_TalonFX::new);
		climbSubsystem = new ClimbSubsystem(7, 4);
		controller = new XboxController(0);
		compressor = new Compressor(PneumaticsModuleType.CTREPCM);
		compressor.enableDigital();
		configureButtonBindings();
		configureDefaultCommands();
	}

	private void configureDefaultCommands() {
		driveSubsystem.setDefaultCommand(new DriveCommand(driveSubsystem, controller));
	}

	private void configureButtonBindings() {
		XboxControllerButtons buttons = new XboxControllerButtons(controller);
		buttons.getA()
				.whenHeld(new ConsumerStartEndCommand<Double>(Constants.INTAKE_SPEED, 0.0, intakeSubsystem::setClaw,
						intakeSubsystem));
		buttons.getB()
				.whenHeld(new ConsumerStartEndCommand<Double>(Constants.SHOOT_SPEED, 0.0, intakeSubsystem::setClaw,
						intakeSubsystem));
		buttons.getX().whenHeld(
				new ConsumerStartEndCommand<Double>(0.25, 0.0, intakeSubsystem::setClawRotator, intakeSubsystem));
		buttons.getY().whenHeld(
				new ConsumerStartEndCommand<Double>(-0.25, 0.0, intakeSubsystem::setClawRotator, intakeSubsystem));
		buttons.getDPadUp()
				.whenHeld(new ConsumerStartEndCommand<Double>(Constants.CLIMB_SPEED, 0.0, climbSubsystem::setMotor,
						climbSubsystem));
		buttons.getDPadDown()
				.whenHeld(new ConsumerStartEndCommand<Double>(-Constants.CLIMB_SPEED, 0.0, climbSubsystem::setMotor,
						climbSubsystem));
		buttons.getDPadLeft()
				.whenPressed(new InstantCommand(() -> climbSubsystem.setSolenoid(false), climbSubsystem));
		buttons.getDPadRight()
				.whenPressed(new InstantCommand(() -> climbSubsystem.setSolenoid(true), climbSubsystem));
	}
}
