package mukherjee.assign2;

/**
 * 
 * @author Ankita Mukherjee
 *
 */

public class Box extends Block {

	public Box() {
		super();
	}

	// initialize this Box's length, width to the constructor's argument
	public Box(double height, double depth, double length) {
		super(height, depth, length); // invoke superclass constructor
	}

	// over-ride toString() method inherited from Block
	// When toString() called on a Box object, this version executed
	@Override
	public String toString() {
		return "Box[height : " + this.getHeight() + " depth: "
				+ this.getDepth() + "length : " + this.getLength() + "]"
				+ ", Area:" + super.getArea() + ", Volume:" + super.getVolume();
	}

}
