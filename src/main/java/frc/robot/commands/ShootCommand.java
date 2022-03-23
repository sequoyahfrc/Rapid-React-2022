package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeSubsystem;

public class ShootCommand extends SequentialCommandGroup {

	public ShootCommand(IntakeSubsystem intakeSubsystem) {
		addCommands(new InstantCommand(() -> intakeSubsystem.setShooterSolenoid(true), intakeSubsystem),
			new WaitCommand(Constants.SHOOT_DELAY),
			new InstantCommand(() -> intakeSubsystem.setClaw(Constants.SHOOT_SPEED),
				intakeSubsystem),
			new WaitCommand(Constants.SHOOT_TIME), new InstantCommand(() -> {
				intakeSubsystem.stopAll();
				intakeSubsystem.setShooterSolenoid(false);
			}, intakeSubsystem));
	}
}
