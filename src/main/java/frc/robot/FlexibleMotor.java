package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public interface FlexibleMotor<T extends MotorController> {
	public T getBaseController();

	public void limitCurrent(double limit);
}
