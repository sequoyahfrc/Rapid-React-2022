package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimbSubsystem extends SubsystemBase {

	private final WPI_TalonFX motor;
	private final DoubleSolenoid solenoid;

	public ClimbSubsystem(int motorID, int solenoidID) {
		motor = new WPI_TalonFX(motorID);
		solenoid = new DoubleSolenoid(0, PneumaticsModuleType.CTREPCM, 4, 5);
	}

	public void setMotor(double speed) {
		motor.set(speed);
	}

	public void setSolenoid(boolean v) {
		solenoid.set(v ? Value.kForward : Value.kReverse);
	}
}