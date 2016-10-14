package mukherjee.assign3;

import java.io.File;
import java.util.Date;
import java.util.Date;

/**
 * 
 * @author Ankita Mukherjee
 *
 */
public class Picture1 {
	String title;
	String category;
	String photographer;
	String location;
	String filePath;
	String fileName;
	Date date;

	/**
	 * Default Constructor
	 */
	Picture1() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPhotographer() {
		return photographer;
	}

	public void setPhotographer(String photographer) {
		this.photographer = photographer;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getfilePath() {
		return filePath;
	}

	public void setfilePath(String filePath) {
		this.filePath = filePath;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getfileName() {
		return fileName;
	}

	public void setfileName(String fileName) {
		this.fileName = fileName;
	}
}
