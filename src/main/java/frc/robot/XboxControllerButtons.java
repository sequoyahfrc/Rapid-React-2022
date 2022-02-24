package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;

public class XboxControllerButtons {
	private final Button a, b, x, y, lb, rb, ls, rs, dpadUp, dpadRight,
			dpadDown, dpadLeft;

	public XboxControllerButtons(XboxController controller) {
		a = new Button(controller::getAButton);
		b = new Button(controller::getBButton);
		x = new Button(controller::getXButton);
		y = new Button(controller::getYButton);
		lb = new Button(controller::getLeftBumper);
		rb = new Button(controller::getRightBumper);
		ls = new Button(controller::getLeftStickButton);
		rs = new Button(controller::getRightStickButton);
		dpadUp = new Button(() -> controller.getPOV() == 315 || controller.getPOV() == 0 || controller.getPOV() == 45);
		dpadRight = new Button(
				() -> controller.getPOV() == 45 || controller.getPOV() == 90 || controller.getPOV() == 135);
		dpadDown = new Button(
				() -> controller.getPOV() == 135 || controller.getPOV() == 180 || controller.getPOV() == 225);
		dpadLeft = new Button(
				() -> controller.getPOV() == 225 || controller.getPOV() == 270 || controller.getPOV() == 315);
	}

	public Button getDPadUp() {
		return dpadUp;
	}

	public Button getDPadRight() {
		return dpadRight;
	}

	public Button getDPadDown() {
		return dpadDown;
	}

	public Button getDPadLeft() {
		return dpadLeft;
	}

	public Button getRS() {
		return rs;
	}

	public Button getLS() {
		return ls;
	}

	public Button getRB() {
		return rb;
	}

	public Button getLB() {
		return lb;
	}

	public Button getY() {
		return y;
	}

	public Button getX() {
		return x;
	}

	public Button getB() {
		return b;
	}

	public Button getA() {
		return a;
	}

}