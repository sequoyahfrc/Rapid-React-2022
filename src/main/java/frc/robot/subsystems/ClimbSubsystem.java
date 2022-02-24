package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimbSubsystem extends SubsystemBase {

	private final WPI_TalonFX motor;
	private final Solenoid solenoid;

	public ClimbSubsystem(int motorID, int solenoidID) {
		motor = new WPI_TalonFX(motorID);
		solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, solenoidID);
	}

	public void setMotor(double speed) {
		motor.set(speed);
	}

	public void setSolenoid(boolean on) {
		solenoid.set(on);
	}
}
