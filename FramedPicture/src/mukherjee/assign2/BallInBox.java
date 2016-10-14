package mukherjee.assign2;

/**
 * 
 * @author Ankita Mukherjee
 *
 */

public class BallInBox extends Ball {

	private Box box;

	public BallInBox() {
		super();
	}

	public BallInBox(double radius, double boxHeight, double boxLength,
			double boxDepth) {

		super(radius);
		box = new Box(boxHeight, boxLength, boxDepth);
	}

	public void setBox(double boxHeight, double boxLength, double boxDepth) {
		this.box = new Box(boxHeight, boxLength, boxDepth);
	}

	/**
	 * Method of checking of ball can be fit in box
	 * 
	 * @return String
	 */
	public String isValid() {

		double volume = box.getVolume() - super.getVolume();
		double length = box.getLength() - super.getRadius();
		double height = box.getHeight() - super.getRadius();
		double depth = box.getDepth() - super.getRadius();
		if (length >= 0 && height >= 0 && depth >= 0 && volume > 0) {

			return "Ball can be fit in the Box";
		} else {

			return "Ball cannot be fit in the Box";
		}

	}

	@Override
	public String toString() {
		return "BallInBox: " + super.toString() + "," + box + "\n" + isValid();
	}

}
