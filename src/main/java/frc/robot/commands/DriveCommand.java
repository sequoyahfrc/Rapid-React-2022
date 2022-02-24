package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends CommandBase {

	private final DriveSubsystem<?> driveSubsystem;
	private final XboxController controller;

	public DriveCommand(DriveSubsystem<?> driveSubsystem, XboxController controller) {
		this.driveSubsystem = driveSubsystem;
		this.controller = controller;
		addRequirements(driveSubsystem);
	}

	@Override
	public void execute() {
		double l = controller.getRawAxis(XboxController.Axis.kLeftY.value);
		double r = controller.getRawAxis(XboxController.Axis.kRightY.value);
		driveSubsystem.tankDrive(l * 0.75, r * 0.75);
	}
}
