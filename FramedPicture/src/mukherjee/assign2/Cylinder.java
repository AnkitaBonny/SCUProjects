package mukherjee.assign2;

/**
 * 
 * @author Ankita Mukherjee
 *
 */

public class Cylinder extends Circle {
	private double height;

	public Cylinder() {
		super(); // invoke super class
	}

	public Cylinder(double radius, double height) {
		super(radius);
		this.height = height;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	/**
	 * Method to calculate lateral area of cylinder
	 * 
	 * @return double
	 */
	public double calculateLateralArea() {
		return 2 * 3.14159 * super.getRadius() * height;
	}

	/**
	 * Method to calculate total area of cylinder
	 * 
	 * @return double
	 */
	public double calculateTotalArea() {
		return 2 * 3.14159 * super.getRadius() * (height + super.getRadius());
	}

	/**
	 * Method to calculate volume
	 * 
	 * @return double
	 */
	public double calculateVolume() {
		return 3.14159 * super.getRadius() * super.getRadius() * height;
	}

	@Override
	public String toString() {
		return "Cylinder[radius=" + super.getRadius() + " having Diameter: "
				+ super.calculateDiameter() + " and height=" + height + "]"
				+ ", LateralArea=" + calculateLateralArea() + ", TotalArea="
				+ calculateTotalArea() + ", Volume=" + calculateVolume();
	}
}
