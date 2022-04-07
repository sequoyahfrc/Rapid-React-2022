// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.auto.IStep;
import frc.robot.auto.*;
import frc.robot.commands.DriveCommand;

public class Robot extends TimedRobot {
	@SuppressWarnings({ "unused", "all" })
	private RobotContainer robotContainer;

	@Override
	public void robotInit() {
		robotContainer = new RobotContainer();
		CameraServer.startAutomaticCapture();
		try (PowerDistribution pdp = new PowerDistribution()) {
			pdp.clearStickyFaults();
		}
		try (PneumaticsControlModule pcm = new PneumaticsControlModule()) {
			pcm.clearAllStickyFaults();
		}

	private IStep[] steps;
	private int stepIndex;
	private boolean didInit;

	@Override
	public void autonomousInit() {
		stepIndex = 0;
		steps = new IStep[] {
			new ShooterDownStep(robotContainer.intakeSubsystem),
			new ShootStep(robotContainer.intakeSubsystem),
			new TaxiStep(robotContainer.driveSubsystem)
		};
		didInit = false;
	}

	@Override
	public void autonomousPeriodic() {
		if (stepIndex >= steps.length) {
			return;
		}
		if (!didInit) {
			steps[stepIndex].init();
			didInit = true;
		} else if (!steps[stepIndex].periodic()) {
			steps[stepIndex].end();
			stepIndex++;
			didInit = false;
		}
	}

	@Override
	public void teleopInit() {
		robotContainer.driveSubsystem
			.setDefaultCommand(new DriveCommand(robotContainer.driveSubsystem, robotContainer.driver1));
	}

	@Override
	public void teleopPeriodic() {
		CommandScheduler.getInstance().run();
	}
}
