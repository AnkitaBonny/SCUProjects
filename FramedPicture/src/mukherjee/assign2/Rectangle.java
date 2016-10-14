package mukherjee.assign2;

/**
 * 
 * @author Ankita Mukherjee
 *
 */
public class Rectangle {
	private double length;
	private double width;

	public Rectangle() {
		length = 0.0;
		width = 0.0;
	}

	// constructor with given length and width
	public Rectangle(double length, double width) {
		this.length = length;
		this.width = width;
	}

	public double getWidth() {
		return width;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		if (length >= 0)
			this.length = length;
	}

	public void setWidth(double width) {
		if (width >= 0)
			this.width = width;
	}

	/**
	 * Method for computing the area of rectangle
	 * 
	 * @return double
	 */
	public double getArea() {
		return length * width;
	}

	/**
	 * Method for computing circumference of rectangle
	 * 
	 * @return double
	 */
	public double calculateCircumference() {
		return 2 * (length + width);
	}

	@Override
	public String toString() {
		return "Rectangle [length=" + length + ", width=" + width + "]"
				+ " having Area:" + this.getArea() + ", Circumference:"
				+ this.calculateCircumference();
	}

}
