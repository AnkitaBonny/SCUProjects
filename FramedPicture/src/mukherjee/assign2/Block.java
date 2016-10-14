package mukherjee.assign2;

/**
 * 
 * @author Ankita Mukherjee
 *
 */

public class Block {

	double height;
	double depth;
	double length;

	Block() {
		height = 0.0;
		depth = 0.0;
		length = 0.0;
	}

	Block(double height, double depth, double length) {
		this.height = height;
		this.depth = depth;
		this.length = length;
	}

	public double getHeight() {
		return height;
	}

	public double getDepth() {
		return depth;
	}

	public double getLength() {
		return length;
	}

	public void setHeight(double height) {
		if (height >= 0)
			this.height = height;
	}

	public void setDepth(double depth) {
		if (depth >= 0)
			this.depth = depth;
	}

	public void setLength(double length) {
		if (length >= 0)
			this.length = length;
	}

	/**
	 * Method to compute volume of block
	 * 
	 * @return double
	 */
	public double getVolume() {
		return height * depth * length;

	}

	/**
	 * Method to calculate area of block
	 * 
	 * @return double
	 */
	public double getArea() {
		return height * length;
	}

	@Override
	public String toString() {
		return "Block [height=" + height + ", depth=" + depth + ", length="
				+ length + "]" + ", Volume:" + getVolume() + ", Area:"
				+ getArea();

	}

}
