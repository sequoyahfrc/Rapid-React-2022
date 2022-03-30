package frc.robot.auto;

import frc.robot.Constants;
import frc.robot.subsystems.IntakeSubsystem;

public class ShootStep implements IStep {

	private final IntakeSubsystem intakeSubsystem;
	private int frameCounter;

	public ShootStep(IntakeSubsystem intakeSubsystem) {
		this.intakeSubsystem = intakeSubsystem;
	}

	@Override
	public void init() {
		frameCounter = 0;
	}

	@Override
	public boolean periodic() {
		final int FRAME_SHOOT_SOL_ON = (int) (50 *
			Constants.AUTO_SHOOT_DELAY_TIME);
		final int FRAME_CLAW_ON = FRAME_SHOOT_SOL_ON + (int) (50 *
			Constants.SHOOT_DELAY);
		final int FRAME_STOP_SHOOT = FRAME_CLAW_ON + (int) (50 *
			Constants.SHOOT_TIME);
		switch (frameCounter) {
			case FRAME_SHOOT_SOL_ON:
				intakeSubsystem.setShooterSolenoid(true);
				break;
			case FRAME_CLAW_ON:
				intakeSubsystem.setClaw(Constants.SHOOT_SPEED);
				break;
			case FRAME_STOP_SHOOT:
				intakeSubsystem.stopAll();
				intakeSubsystem.setShooterSolenoid(false);
				return false;
		}
		return true;
	}
}
