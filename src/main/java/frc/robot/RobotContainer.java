// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.commands.ConsumerStartEndCommand;
import frc.robot.commands.MoveClawToTargetRangeCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class RobotContainer {

	public final IntakeSubsystem intakeSubsystem;
	public final DriveSubsystem<?> driveSubsystem;
	public final ClimbSubsystem climbSubsystem;
	public final XboxController driver1, driver2;
	public final Compressor compressor;
	public final PowerDistribution pdp;
	public ShuffleboardTab tab;

	public RobotContainer() {
		intakeSubsystem = new IntakeSubsystem(4, 5, 6, 6, 3, 9);
		driveSubsystem = new DriveSubsystem<WPI_TalonFX>(3, 1, 8, 2, WPI_TalonFX::new);
		climbSubsystem = new ClimbSubsystem(7, 4, 5);
		driver1 = new XboxController(0);
		driver2 = new XboxController(1);
		compressor = new Compressor(PneumaticsModuleType.CTREPCM);
		pdp = new PowerDistribution(0, ModuleType.kCTRE);
		compressor.enableDigital();
		configureButtonBindings();
		configureDefaultCommands();
	}

	public void periodic() {
		if (tab == null) {
			final String TAB_NAME = "Dashboard";
			tab = Shuffleboard.getTab(TAB_NAME);
			if (tab == null) {
				return;
			}
			Shuffleboard.selectTab(TAB_NAME);
			tab.getComponents().clear();
			tab.add("Camera", CameraServer.getServer().getSource())
				.withPosition(0, 0)
				.withSize(4, 3);

			// Intake
			ShuffleboardContainer intake = tab.getLayout("Intake", BuiltInLayouts.kGrid)
				.withPosition(4, 0)
				.withSize(2, 2)
				.withProperties(Map.of("Number of columns", 2, "Number of rows", 1));
			intake.addBoolean("Has Ball?", intakeSubsystem::getLimitSwitch)
				.withPosition(0, 0)
				.withSize(1, 1);
			intake.addNumber("Claw Angle", intakeSubsystem::getAngle)
				.withPosition(1, 0)
				.withSize(1, 1)
				.withWidget(BuiltInWidgets.kDial)
				.withProperties(Map.of("Min", -360.0, "Max", 360.0));

			// Climber
			tab.addNumber("Climb Encoder", climbSubsystem::getEncoder)
				.withWidget(BuiltInWidgets.kNumberBar)
				.withPosition(4, 2)
				.withSize(2, 1)
				.withProperties(Map.of("Min", 0.0, "Max", 1000000.0));

			// Other
			tab.add("PDP", pdp)
				.withWidget(BuiltInWidgets.kPowerDistribution)
				.withPosition(6, 0)
				.withSize(3, 2);
			tab.addNumber("Pressure", compressor::getPressure)
				.withWidget(BuiltInWidgets.kNumberBar)
				.withPosition(6, 2)
				.withSize(3, 1)
				.withProperties(Map.of("Min", 0.0, "Max", 120.0));
		}

	}

	private void configureDefaultCommands() {
	}

	private void configureButtonBindings() {
		XboxControllerButtons driver1 = new XboxControllerButtons(this.driver1);
		XboxControllerButtons driver2 = new XboxControllerButtons(this.driver2);
		// Intake / Shoot
		driver2.getA()
			.whenHeld(new ConsumerStartEndCommand<Double>(Constants.INTAKE_SPEED, 0.0, intakeSubsystem::setClaw,
				intakeSubsystem));
		driver2.getY().whenPressed(new ShootCommand(intakeSubsystem));
		driver2.getDPadUp().whenHeld(
			new ConsumerStartEndCommand<Double>(Constants.CLAW_UP_SPEED, 0.0, intakeSubsystem::setClawRotator,
				intakeSubsystem));
		driver2.getDPadDown().whenHeld(
			new ConsumerStartEndCommand<Double>(Constants.CLAW_DOWN_SPEED, 0.0, intakeSubsystem::setClawRotator,
				intakeSubsystem));
		driver2.getX().whenPressed(new MoveClawToTargetRangeCommand(intakeSubsystem));
		driver2.getB().whenHeld(
			new ConsumerStartEndCommand<Boolean>(true, false, intakeSubsystem::setClawSolenoid, intakeSubsystem));
		// Climb
		driver1.getDPadUp()
			.whenHeld(new ConsumerStartEndCommand<Double>(Constants.CLIMB_SPEED, 0.0, climbSubsystem::setMotor,
				climbSubsystem));
		driver1.getDPadDown()
			.whenHeld(new ConsumerStartEndCommand<Double>(-Constants.CLIMB_SPEED, 0.0, climbSubsystem::setMotor,
				climbSubsystem));
		driver1.getDPadLeft()
			.whenPressed(new InstantCommand(() -> {
				climbSubsystem.setSolenoid(Value.kForward);
			}, climbSubsystem));
		driver1.getDPadRight()
			.whenPressed(new InstantCommand(() -> climbSubsystem.setSolenoid(Value.kReverse), climbSubsystem));
		// Dont require driveSubsystem here to prevent blocking drive
		new Button(() -> this.driver1.getRawAxis(Constants.IS_D1_USING_XBOX ? 3 : 4) > 0.5)
			.whenHeld(new ConsumerStartEndCommand<Double>(0.5, 1.0, driveSubsystem::setMultiplier));

		driver1.getY().whenPressed(new InstantCommand(() -> {
			intakeSubsystem.resetEncoder();
		}, intakeSubsystem));
		driver1.getA()
			.whenPressed(new InstantCommand(() -> climbSubsystem.setMotor(Constants.CLIMB_SPEED), climbSubsystem)
				.andThen(new WaitUntilCommand(() -> climbSubsystem.getEncoder() > 300000))
				.andThen(
					new InstantCommand(() -> {
						climbSubsystem.setMotor(Constants.CLIMB_SPEED * 0.5);
					}, climbSubsystem))
				.andThen(new WaitUntilCommand(() -> climbSubsystem.getEncoder() >= 368000))
				.andThen(new InstantCommand(() -> climbSubsystem.setMotor(0), climbSubsystem)));
	}
}
