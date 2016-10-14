package mukherjee.assign2;

/**
 * 
 * @author Ankita Mukherjee
 *
 */

public class FramedPicture extends Frame {

	private Picture picture;

	public FramedPicture() {
		super();
	}

	public FramedPicture(double length, double width, String material,
			double pictureLength, double pictureWidth, String pictureTitle,
			String pictureArtist) {

		super(length, width, material);
		picture = new Picture(pictureLength, pictureWidth, pictureTitle,
				pictureArtist);
	}

	public void setPicture(double pictureLength, double pictureWidth,
			String pictureTitle, String pictureArtist) {
		this.picture = new Picture(pictureLength, pictureWidth, pictureTitle,
				pictureArtist);
	}

	/**
	 * Method for checking the validity of containing picture in a frame
	 * 
	 * @return
	 */
	public String isValid() {

		double length = super.getLength() - picture.getLength();
		double width = super.getWidth() - picture.getWidth();
		if (length >= 0 && width >= 0) {

			return "Picture can be contained in the frame";
		} else {

			return "Picture cannot be contained in the frame";
		}

	}

	@Override
	public String toString() {
		return "FramedPicture: " + super.toString() + ", " + picture + "\n"
				+ isValid();
	}

}
