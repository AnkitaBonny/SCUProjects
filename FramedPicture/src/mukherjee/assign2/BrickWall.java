package mukherjee.assign2;

/**
 * 
 * @author Ankita Mukherjee
 *
 */

public class BrickWall {

	private Rectangle rectangle;
	private Block block;

	public BrickWall() {
	}

	public BrickWall(double rectangleLength, double rectangleWidth,
			double blockHeight, double blockDepth, double blockLength) {
		rectangle = new Rectangle(rectangleLength, rectangleWidth);
		block = new Block(blockHeight, blockDepth, blockLength);
	}

	public void setRectangle(double rectangleLength, double rectangleWidth) {
		this.rectangle = new Rectangle(rectangleLength, rectangleWidth);
	}

	public void setBlock(double blockHeight, double blockDepth,
			double blockLength) {
		this.block = new Block(blockHeight, blockDepth, blockLength);
	}

	/**
	 * Method of checking if wall can be made with bricks
	 * 
	 * @return String
	 */
	public String isValid() {

		double area = rectangle.getArea() - block.getArea();
		double length = rectangle.getLength() - block.getLength();
		double width = rectangle.getWidth() - block.getHeight();
		if (length >= 0 && width >= 0 && area >= 0) {
			return "Wall can be made with bricks";
		} else {
			return "Wall can't be made with bricks : invalid input";
		}

	}

	/**
	 * Method of calculating number of bricks required to make wall
	 * 
	 * @return String
	 */
	public String numberOfBricks() {

		double length = rectangle.getLength() - block.getLength();
		double width = rectangle.getWidth() - block.getHeight();
		double n = rectangle.getArea() / block.getArea();
		if (n >= 1 && length >= 0 && width >= 0) {
			return "Number of Bricks required: " + (int) n;
		} else {
			return "Invalid input: Number of bricks- None";
		}

	}

	@Override
	public String toString() {
		return "BrickWall: " + rectangle + ", " + block + "\n" + isValid()
				+ "\n" + numberOfBricks();
	}

}
