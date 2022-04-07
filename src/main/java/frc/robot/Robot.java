// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
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
	}

	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();
		robotContainer.periodic();
	}

	private int frameCounter;
	private AutoState state;

	@Override
	public void autonomousInit() {
		frameCounter = 0;
		state = AutoState.SHOOTER_DOWN;
	}

	@Override
	public void autonomousPeriodic() {
		switch (state) {
			case SHOOTER_DOWN:
				int pos = robotContainer.intakeSubsystem.isInAngleRange(Constants.CLAW_ANGLE,
					Constants.CLAW_ANGLE_ERROR);
				if (pos < 0) {
					robotContainer.intakeSubsystem.setClawRotator(Constants.CLAW_DOWN_SPEED);
				} else if (pos > 0) {
					robotContainer.intakeSubsystem.setClawRotator(Constants.CLAW_UP_SPEED);
				} else {
					state = AutoState.SHOOT;
					frameCounter = 0;
					robotContainer.intakeSubsystem.setClawRotator(0);
				}
				break;
			case SHOOT:
				final int FRAME_SHOOT_SOL_ON = (int) (50 *
					Constants.AUTO_SHOOT_DELAY_TIME);
				final int FRAME_CLAW_ON = FRAME_SHOOT_SOL_ON + (int) (50 *
					Constants.SHOOT_DELAY);
				final int FRAME_STOP_SHOOT = FRAME_CLAW_ON + (int) (50 *
					Constants.SHOOT_TIME);
				switch (frameCounter) {
					case FRAME_SHOOT_SOL_ON:
						robotContainer.intakeSubsystem.setShooterSolenoid(true);
						break;
					case FRAME_CLAW_ON:
						robotContainer.intakeSubsystem.setClaw(Constants.SHOOT_SPEED);
						break;
					case FRAME_STOP_SHOOT:
						robotContainer.intakeSubsystem.stopAll();
						robotContainer.intakeSubsystem.setShooterSolenoid(false);
						frameCounter = 0;
						state = AutoState.TAXI;
						break;
				}
				break;
			case TAXI:
				final int FRAME_TAXI_STOP = (int) (50 * Constants.TAXI_TIME);
				if (frameCounter < FRAME_TAXI_STOP) {
					robotContainer.driveSubsystem.setBothSides(0.6);
				} else {
					robotContainer.driveSubsystem.setBothSides(0);
				}
				break;
		}
		frameCounter++;
	}

	@Override
	public void teleopInit() {
		robotContainer.driveSubsystem
			.setDefaultCommand(new DriveCommand(robotContainer.driveSubsystem, robotContainer.driver1));
	}

	public static enum AutoState {
		SHOOTER_DOWN, SHOOT, TAXI;
	}
}
