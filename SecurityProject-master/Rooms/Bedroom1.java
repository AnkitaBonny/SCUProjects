package project.security.Rooms;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Bedroom1 extends JPanel {

    private JLabel label2;
    private JButton room1;

    public Bedroom1(){
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setBounds(52, 52, 194, 194);

        //set up image in the picture panel
        setBedroom1Panel();
        add(room1);
        add(label2);
    }
    /**
     * Set up label for picture. Then add picture to the label.
     * Add label to the kitchen panel.
     *
     */
    public void setBedroom1Panel(){
        //create and set up label
        room1 = new JButton("Bedroom1");
        room1.setBackground(Color.CYAN);
        room1.setActionCommand("room1");
        room1.setLocation(0, 0);
        room1.setSize(105, 30);
        room1.setFont(new Font("Courier New", Font.ITALIC + Font.BOLD, 12));

        label2 = new JLabel();
        label2.setPreferredSize(new Dimension(100, 100));
        label2.setLocation(0, 0);
        label2.setSize(120, 160);

        // add image to the label
        Util.setPicturetoLabel(label2, "/images/bedroom1.jpg") ;
    }
    /**
     * Allows for outside access to add an actionlisteners
     * @param listener an actionlistner that allows outside access
     */
    public void setActionListener(ActionListener listener) {
        room1.addActionListener(listener);
    }
}