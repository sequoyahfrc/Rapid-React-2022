// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.ShootCommand;

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

	@Override
	public void autonomousInit() {
		CommandScheduler.getInstance().schedule(
			// Rotate claw
			new InstantCommand(() -> robotContainer.intakeSubsystem.setClaw(Constants.CLAW_DOWN_SPEED * 0.25),
				robotContainer.intakeSubsystem)
					.andThen(new WaitCommand(Constants.AUTO_CLAW_DOWN_TIME))
					.andThen(new InstantCommand(() -> robotContainer.intakeSubsystem.setClaw(0),
						robotContainer.intakeSubsystem))
					// Shoot
					.andThen(new ShootCommand(robotContainer.intakeSubsystem))
					// Taxi
					.andThen(
						new InstantCommand(() -> robotContainer.driveSubsystem.tankDrive(1, 1),
							robotContainer.driveSubsystem))
					// Wait for TAXI_TIME or for auto to end, just in case
					.andThen(new WaitCommand(Constants.TAXI_TIME).raceWith(new WaitUntilCommand(15.0)))
					.andThen(new InstantCommand(() -> robotContainer.driveSubsystem.tankDrive(0, 0),
						robotContainer.driveSubsystem)));
	}
}
