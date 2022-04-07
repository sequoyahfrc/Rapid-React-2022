package frc.robot.auto;

import frc.robot.Constants;
import frc.robot.subsystems.IntakeSubsystem;

public class ShooterDownStep implements IStep {

	private final IntakeSubsystem intakeSubsystem;

	public ShooterDownStep(IntakeSubsystem intakeSubsystem) {
		this.intakeSubsystem = intakeSubsystem;
	}

	@Override
	public boolean periodic() {
		int pos = intakeSubsystem.isInAngleRange(Constants.CLAW_ANGLE,
			Constants.CLAW_ANGLE_ERROR);
		if (pos < 0) {
			intakeSubsystem.setClawRotator(Constants.CLAW_DOWN_SPEED);
		} else if (pos > 0) {
			intakeSubsystem.setClawRotator(Constants.CLAW_UP_SPEED);
		} else {
			return false;
		}
		return true;
	}

	@Override
	public void end() {
		intakeSubsystem.setClawRotator(0);
	}

}
