package frc.robot.commands;

import java.util.function.Consumer;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class ConsumerStartEndCommand<TValue> extends StartEndCommand {

	public ConsumerStartEndCommand(TValue valueStart, TValue valueEnd, Consumer<TValue> func,
			Subsystem... requirements) {
		super(() -> func.accept(valueStart), () -> func.accept(valueEnd), requirements);
	}

}
