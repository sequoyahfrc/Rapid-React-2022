// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.commands.ConsumerStartEndCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class RobotContainer {

	public final IntakeSubsystem intakeSubsystem;
	public final DriveSubsystem<?> driveSubsystem;
	public final ClimbSubsystem climbSubsystem;
	private final XboxController driver1, driver2;
	private final Compressor compressor;

	public RobotContainer() {
		intakeSubsystem = new IntakeSubsystem(4, 5, 6, 6, 7);
		driveSubsystem = new DriveSubsystem<WPI_TalonFX>(3, 1, 8, 2, WPI_TalonFX::new);
		climbSubsystem = new ClimbSubsystem(7, 4, 5);
		driver1 = new XboxController(0);
		driver2 = new XboxController(1);
		compressor = new Compressor(PneumaticsModuleType.CTREPCM);
		compressor.enableDigital();
		configureButtonBindings();
		configureDefaultCommands();
	}

	private void configureDefaultCommands() {
		driveSubsystem.setDefaultCommand(new DriveCommand(driveSubsystem, driver1));
	}

	private void configureButtonBindings() {
		XboxControllerButtons driver1 = new XboxControllerButtons(this.driver1);
		XboxControllerButtons driver2 = new XboxControllerButtons(this.driver2);
		// Intake / Shoot
		driver2.getA()
			.whenHeld(new ConsumerStartEndCommand<Double>(Constants.INTAKE_SPEED, 0.0, intakeSubsystem::setClaw,
				intakeSubsystem));
		driver2.getY()
			.whenPressed(
				new InstantCommand(() -> intakeSubsystem.setShooterSolenoid(true), intakeSubsystem)
					.andThen(new WaitCommand(Constants.SHOOT_DELAY))
					.andThen(new InstantCommand(() -> intakeSubsystem.setClaw(Constants.SHOOT_SPEED),
						intakeSubsystem))
					.andThen(new WaitCommand(Constants.SHOOT_TIME))
					.andThen(new InstantCommand(() -> {
						intakeSubsystem.stopAll();
						intakeSubsystem.setShooterSolenoid(false);
						intakeSubsystem.setClawSolenoid(false);
					}, intakeSubsystem)));
		driver2.getDPadRight()
			.whenPressed(new InstantCommand(() -> intakeSubsystem.setClawSolenoid(true), intakeSubsystem));
		driver2.getDPadLeft()
			.whenPressed(new InstantCommand(() -> intakeSubsystem.setClawSolenoid(false), intakeSubsystem));
		driver2.getDPadUp().whenHeld(
			new ConsumerStartEndCommand<Double>(Constants.CLAW_UP_SPEED, 0.0, intakeSubsystem::setClawRotator,
				intakeSubsystem));
		driver2.getDPadDown().whenHeld(
			new ConsumerStartEndCommand<Double>(Constants.CLAW_DOWN_SPEED, 0.0, intakeSubsystem::setClawRotator,
				intakeSubsystem));
		// Climb
		driver1.getDPadUp()
			.whenHeld(new ConsumerStartEndCommand<Double>(Constants.CLIMB_SPEED, 0.0, climbSubsystem::setMotor,
				climbSubsystem));
		driver1.getDPadDown()
			.whenHeld(new ConsumerStartEndCommand<Double>(-Constants.CLIMB_SPEED, 0.0, climbSubsystem::setMotor,
				climbSubsystem));
		driver1.getDPadLeft()
			.whenPressed(new InstantCommand(() -> climbSubsystem.setSolenoid(Value.kForward), climbSubsystem));
		driver1.getDPadRight()
			.whenPressed(new InstantCommand(() -> climbSubsystem.setSolenoid(Value.kReverse), climbSubsystem));
		// Dont require driveSubsystem here to prevent blocking drive
		new Button(() -> this.driver1.getRawAxis(Constants.IS_D1_USING_XBOX ? 3 : 4) > 0.5)
			.whenHeld(new ConsumerStartEndCommand<Double>(0.5, 1.0, driveSubsystem::setMultiplier));
	}
}
