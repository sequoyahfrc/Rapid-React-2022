package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimbSubsystem extends SubsystemBase {

	private final WPI_TalonFX motor;
	private final Solenoid forward, reverse;

	public ClimbSubsystem(int motorID, int solenoidID) {
		motor = new WPI_TalonFX(motorID);
		forward = new Solenoid(0, PneumaticsModuleType.CTREPCM, 4);
		reverse = new Solenoid(0, PneumaticsModuleType.CTREPCM, 5);
		motor.setInverted(true);
	}

	public void setMotor(double speed) {
		motor.set(speed);
	}

	public void setForwardSolenoid(boolean v) {
		forward.set(v);
	}

	public void setReverseSolenoid(boolean v) {
		reverse.set(v);
	}
}