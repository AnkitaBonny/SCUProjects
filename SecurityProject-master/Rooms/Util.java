package project.security.Rooms;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Util {
    /**
     * Make the height and width of the image proportioned to the label.
     * Renders the selected image. Then add it to the label
     * @param label label to set
     * @param name  name of picture
     */
    protected static void setPicturetoLabel(JLabel label, String name) {
        ImageIcon icon = createImageIcon(name );
        ImageIcon imgThisImg =icon;

        if  (icon != null) {
            Image img = imgThisImg.getImage();
            Image newimg;
            int h, w;

            // make the height and width proportioned to the label
            // calculate new h and w
            if(imgThisImg.getIconHeight() > imgThisImg.getIconWidth()){
                h = (int) label.getPreferredSize().getHeight();
                w = imgThisImg.getIconWidth() *  h / imgThisImg.getIconHeight();
                if(w > label.getPreferredSize().getWidth() ) w = (int) label.getPreferredSize().getWidth() ;
            }
            else{
                w = (int) label.getPreferredSize().getWidth() ;
                h = imgThisImg.getIconHeight() *  w / imgThisImg.getIconWidth();

                if(h > label.getPreferredSize().getHeight()) h = (int) label.getPreferredSize().getHeight();
            }
            newimg = img.getScaledInstance( w, h ,java.awt.Image.SCALE_SMOOTH);
            ImageIcon icon1 = new ImageIcon(newimg);

            label.setIcon(icon1);
        }
        else {
            label.setText("Image not found");
        }
    }
    /**
     * Returns an ImageIcon, or null if the path was invalid.
     * @param path path of image
     * @return returns imageicon if successful
     */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = PanelCenter.class.getResource(path);
        // java.net.URL imgURL = .getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        }
        else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}