package mukherjee.assign2;

/**
 * 
 * @author Ankita Mukherjee
 *
 */

public class Square extends Rectangle {
	public Square() {
		super();
	}

	// initialize this Square's length, width to the constructor's argument
	public Square(double side) {
		super(side, side); // invoke superclass constructor
	}

	// over-ride toString() method inherited from Rectangle
	// When toString() called on a Square object, this version executed
	@Override
	public String toString() {
		return "Square[side = " + this.getLength() + "]" + " having Area: "
				+ super.getArea() + ", Circumference:"
				+ super.calculateCircumference();
	}

}
