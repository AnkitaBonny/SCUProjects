package mukherjee.assign2;
/**
 * 
 * @author Ankita Mukherjee
 *
 */

public class Question1Tester {

	public static void main(String[] args) {

		Rectangle rectangle = new Rectangle();
		rectangle.setLength(4);
		rectangle.setWidth(6);
		System.out.println(rectangle);
		System.out.println("getArea(): " + rectangle.getArea());

		Square square = new Square(4);
		System.out.println(square);

		Rectangle r = new Square(5);
		System.out.println(r);

		Circle circle = new Circle(5);
		System.out.println(circle);
		System.out.println("calculateArea(): " + circle.calculateArea());

		Ball ball = new Ball();
		ball.setRadius(2);
		System.out.println("Radius of Ball: " + ball.getRadius());
		System.out.println(ball);

		Circle c = new Ball(4);
		System.out.println(c);

		Cylinder cylinder = new Cylinder(4, 2);
		System.out.println(cylinder);
		System.out.println("calculateVolume(): " + cylinder.calculateVolume());

		Circle d = new Cylinder(6, 4.2);
		System.out.println(d);

		Block block = new Block(2, 3, 4);
		System.out.println(block);

		Box box = new Box(1, 3.4, 4.2);
		System.out.println(box);

		Block ab = new Box(6, 3.4, 4.2);
		System.out.println(ab);
		System.out.println("getVolume():" + ab.getVolume() + "\ngetArea():"
				+ ab.getArea());

		Frame frame = new Frame(8.5, 11, "Wood");
		System.out.println(frame);

		Frame newFrame = new Frame();
		newFrame.setlength(8);
		newFrame.setWidth(6);
		newFrame.setMaterial("Fibre");
		System.out.println("newFrame [length: " + newFrame.getLength()
				+ " width:  " + newFrame.getWidth() + "]" + " getArea(): "
				+ newFrame.getArea());

		Picture picture = new Picture(6, 4, "Nature", "John");
		System.out.println(picture);

		FramedPicture framedPicture = new FramedPicture(12, 10, "Plastic", 6,
				4, "Wildlife", "Suman");
		System.out.println(framedPicture);

		FramedPicture frameOfPicture = new FramedPicture(12, 10, "Metal", 18,
				4, "Mountain", "Victor");
		System.out.println(frameOfPicture);

		BallInBox ballInBox = new BallInBox(5, 8, 9, 10);
		System.out.println(ballInBox);

		BallInBox ballInsideBox = new BallInBox(40, 8, 9, 10);
		System.out.println(ballInsideBox);

		BrickWall brickedWall = new BrickWall(10, 12, 2, 6, 8);
		System.out.println(brickedWall);

		BrickWall brickWall = new BrickWall(10, 12, 2, 20, 20);
		System.out.println(brickWall);

	}

}