package frc.robot.commands;

import java.util.function.DoubleConsumer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MotorControllerCommand extends CommandBase {

	private final double speed;
	private final DoubleConsumer consumer;

	public MotorControllerCommand(double speed, DoubleConsumer consumer, SubsystemBase... requirements) {
		this.speed = speed;
		this.consumer = consumer;
		addRequirements(requirements);
	}

	@Override
	public void initialize() {
		consumer.accept(speed);
	}

	@Override
	public void end(boolean interrupted) {
		consumer.accept(0);
	}
}