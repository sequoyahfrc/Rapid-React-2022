package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimbSubsystem extends SubsystemBase {

	private final WPI_TalonFX motor;
	private final DoubleSolenoid solenoid;

	public ClimbSubsystem(int motorID, int forwardSolID, int reverseSolID) {
		motor = new WPI_TalonFX(motorID);
		solenoid = new DoubleSolenoid(0, PneumaticsModuleType.CTREPCM, forwardSolID, reverseSolID);
		motor.setInverted(false);
		motor.setNeutralMode(NeutralMode.Brake);
		resetEncoder();

	}

	@Override
	public void periodic() {
		SmartDashboard.putString("DB/String 0", "Climb Encoder");
		SmartDashboard.putString("DB/String 5", "" + getEncoder());
	}

	public void setMotor(double speed) {
		motor.set(speed);
	}

	public void setSolenoid(DoubleSolenoid.Value value) {
		solenoid.set(value);
	}

	// public DoubleSolenoid.Value

	public double getEncoder() {
		return motor.getSelectedSensorPosition();
	}

	public void resetEncoder() {
		motor.setSelectedSensorPosition(0);
	}
}