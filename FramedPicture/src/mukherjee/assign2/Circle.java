package mukherjee.assign2;

/**
 * 
 * @author Ankita Mukherjee
 *
 */

public class Circle {
	private double radius;

	public Circle() {

	}

	public Circle(double radius) {
		this.radius = radius;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double value) {
		if (radius < 0)
			radius = 0.00;
		else
			radius = value;
	}

	/**
	 * Method to calculate diameter of circle
	 * 
	 * @return double
	 */
	public double calculateDiameter() {
		return radius * 2;
	}

	/**
	 * Method to calculate circumference of circle
	 * 
	 * @return double
	 */
	public double calculateCircumference() {
		return calculateDiameter() * 3.14159;
	}

	/**
	 * Method to calculate area of circle
	 * 
	 * @return double
	 */
	public double calculateArea() {
		return radius * radius * 3.14159;
	}

	@Override
	public String toString() {
		return "Circle[radius : " + radius + " having Diameter:"
				+ this.calculateDiameter() + "]" + ", Area:"
				+ this.calculateArea() + ", Cicumference:"
				+ this.calculateCircumference();
	}
}
