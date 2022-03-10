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
		driveSubsystem.tankDrive(speedGraph(l), speedGraph(r));
	}

	private static double speedGraph(double x) {
		// Before changing, make sure graph(1) = 1 and graph(-1) = -1
		final double a = 0.75;
		final double b = 0.3333333333333333333;
		final double c = 3.0;
		boolean neg = x < 0;
		x = Math.abs(x);
		double y;
		if (x < 0.75) {
			y = a * x;
		} else {
			y = a * b + c * (x - a);
		}
		return y * (neg ? -1 : 1);
	}
}
