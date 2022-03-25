// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.DriveCommand;

public class Robot extends TimedRobot {
	@SuppressWarnings({ "unused", "all" })
	private RobotContainer robotContainer;

	@Override
	public void robotInit() {
		robotContainer = new RobotContainer();
	}

	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();
	}

	private int frameCounter;

	@Override
	public void autonomousInit() {
		frameCounter = 0;
	}

	@Override
	public void autonomousPeriodic() {
		final int FRAME_STOP_CLAW_ROTATOR = (int) (50 * Constants.AUTO_CLAW_DOWN_TIME);
		final int FRAME_SHOOT_SOL_ON = FRAME_STOP_CLAW_ROTATOR + (int) (50 * Constants.AUTO_SHOOT_DELAY_TIME);
		final int FRAME_CLAW_ON = FRAME_SHOOT_SOL_ON + (int) (50 * Constants.SHOOT_DELAY);
		final int FRAME_STOP_SHOOT = FRAME_CLAW_ON + (int) (50 * Constants.SHOOT_TIME);
		final int FRAME_TAXI_END = FRAME_STOP_SHOOT + (int) (50 * Constants.TAXI_TIME);
		switch (frameCounter) {
			case 0:
				robotContainer.intakeSubsystem.setClawRotator(Constants.CLAW_DOWN_SPEED);
				break;
			case FRAME_STOP_CLAW_ROTATOR:
				robotContainer.intakeSubsystem.setClawRotator(0);
				break;
			case FRAME_SHOOT_SOL_ON:
				robotContainer.intakeSubsystem.setShooterSolenoid(true);
				break;
			case FRAME_CLAW_ON:
				robotContainer.intakeSubsystem.setClaw(Constants.SHOOT_SPEED);
				break;
			case FRAME_STOP_SHOOT:
				robotContainer.intakeSubsystem.stopAll();
				robotContainer.intakeSubsystem.setShooterSolenoid(false);
				break;
		}
		if (frameCounter >= FRAME_STOP_SHOOT && frameCounter < FRAME_TAXI_END) {
			robotContainer.driveSubsystem.setBothSides(1);
		}
		switch (frameCounter) {
			case FRAME_TAXI_END:
				robotContainer.driveSubsystem.setBothSides(0);
				break;
		}
		frameCounter++;
	}

	@Override
	public void teleopInit() {
		robotContainer.driveSubsystem
			.setDefaultCommand(new DriveCommand(robotContainer.driveSubsystem, robotContainer.driver1));
	}
}
