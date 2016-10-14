package mukherjee.assign2;

/**
 * 
 * @author Ankita Mukherjee
 *
 */

public class Picture {
	double length;
	double width;
	String title;
	String artist;

	public Picture() {
		length = 1.0;
		width = 1.0;
	}

	// constructor with given length and width
	public Picture(double length, double width, String title, String artist) {
		this.length = length;
		this.width = width;
		this.title = title;
		this.artist = artist;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	/**
	 * Method for computing the area of Picture
	 * 
	 * @return double
	 */
	public double getArea() {
		return length * width;
	}

	@Override
	public String toString() {
		return "Picture [length=" + length + ", width=" + width + ", title="
				+ title + ", artist=" + artist + "]" + ", Area:" + getArea();
	}

}
