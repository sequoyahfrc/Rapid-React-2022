package frc.robot.auto;

public interface IStep {
	public default void init() {
	}

	public default boolean periodic() {
		return true;
	}

	public default void end() {
	}
}
