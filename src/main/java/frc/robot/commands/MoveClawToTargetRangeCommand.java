package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeSubsystem;

public class MoveClawToTargetRangeCommand extends CommandBase {

	private final IntakeSubsystem intakeSubsystem;
	private int position;

	public MoveClawToTargetRangeCommand(IntakeSubsystem intakeSubsystem) {
		this.intakeSubsystem = intakeSubsystem;
		addRequirements(intakeSubsystem);
		isFinished();
	}

	@Override
	public void execute() {
		if (position < 0) {
			intakeSubsystem.setClawRotator(Constants.CLAW_DOWN_SPEED);
		} else {
			intakeSubsystem.setClawRotator(Constants.CLAW_UP_SPEED);
		}
	}

	@Override
	public void end(boolean interrupted) {
		intakeSubsystem.setClawRotator(0);
	}

	@Override
	public boolean isFinished() {
		return (position = intakeSubsystem.isInAngleRange(Constants.CLAW_ANGLE, Constants.CLAW_ANGLE_ERROR)) == 0;
	}
}
