package mukherjee.assign2;

/**
 * 
 * @author Ankita Mukherjee
 *
 */

public class Ball extends Circle {
	public Ball() {
		super();
	}

	// initialize this Ball's radius to the constructor's argument
	public Ball(double radius) {
		super(radius); // invoke superclass constructor
	}

	/**
	 * Method to compute volume of ball
	 * 
	 * @return double
	 */
	public double getVolume() {
		return 1.33 * getRadius() * getRadius() * getRadius() * Math.PI;
	}

	/**
	 * method to compute area of ball
	 * 
	 * @return double
	 */
	public double calculateArea() {
		return 4 * getRadius() * getRadius() * 3.14159;
	}

	@Override
	public String toString() {
		return "Ball[radius = " + this.getRadius() + " having Diameter: "
				+ super.calculateDiameter() + "]" + ", Area:"
				+ this.calculateArea() + ", Volume:" + this.getVolume();
	}
}