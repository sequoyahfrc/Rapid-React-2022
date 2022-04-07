package frc.robot.auto;

import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

public class TaxiStep implements IStep {

	private final DriveSubsystem<?> driveSubsystem;
	private int frameCounter;

	public TaxiStep(DriveSubsystem<?> driveSubsystem) {
		this.driveSubsystem = driveSubsystem;
	}

	@Override
	public void init() {
		frameCounter = 0;
	}

	@Override
	public boolean periodic() {
		final int FRAME_END = (int) (50 * Constants.TAXI_TIME);
		driveSubsystem.tankDrive(0.6, 0.6);
		frameCounter++;
		return frameCounter < FRAME_END;
	}

	@Override
	public void end() {
		driveSubsystem.setBothSides(0);
	}
}
