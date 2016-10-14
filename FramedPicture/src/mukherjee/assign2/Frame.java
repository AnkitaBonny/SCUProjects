package mukherjee.assign2;

/**
 * 
 * @author Ankita Mukherjee
 *
 */

public class Frame {
	private double length;
	private double width;
	private String material;

	public Frame() {
		length = 0.0;
		width = 0.0;
	}

	// constructor with given length and width
	public Frame(double length, double width, String material) {
		this.length = length;
		this.width = width;
		this.material = material;
	}

	public double getLength() {
		return length;
	}

	public void setlength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	/**
	 * Method for computing the area of frame
	 * 
	 * @return double
	 */
	public double getArea() {
		return length * width;
	}

	@Override
	public String toString() {
		return "Frame [length=" + length + ", width=" + width + ", material="
				+ material + "]" + ", Area:" + getArea();
	}

}
