// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.ConsumerStartEndCommand;
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
			new ConsumerStartEndCommand<Double>(Constants.CLAW_DOWN_SPEED * 0.25, 0.0,
				robotContainer.intakeSubsystem::setClaw, robotContainer.intakeSubsystem)
					.deadlineWith(new WaitCommand(Constants.AUTO_CLAW_DOWN_TIME))
					// Shoot
					.andThen(new ShootCommand(robotContainer.intakeSubsystem))
					// Taxi
					.andThen(new ConsumerStartEndCommand<Double>(-1.0, 0.0,
						robotContainer.driveSubsystem::setBoth,
						robotContainer.driveSubsystem))
					// Wait for TAXI_TIME or for auto to end
					.deadlineWith(new WaitCommand(Constants.TAXI_TIME).raceWith(new WaitUntilCommand(15))));
	}
}
