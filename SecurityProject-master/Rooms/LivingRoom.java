package project.security.Rooms;

import project.security.Rooms.Util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class to generate image and button for LivingRoom
 */
public class LivingRoom extends JPanel {
    private JLabel label1;
    private JButton livingRoom;

    public LivingRoom(){
        this.setBackground(Color.WHITE);
        this.setBounds(352, 252, 190, 290);

        //set up image in the picture panel
        setLivingRoomPanel();
        add(label1);
        add(livingRoom);
    }
    /**
     * Set up label for picture. Then add picture to the label.
     * Add label to the kitchen panel.
     *
     */
    public void setLivingRoomPanel(){
        livingRoom = new JButton("Living Room");
        livingRoom.setActionCommand("living room");
        livingRoom.setBackground(Color.CYAN);
        livingRoom.setLocation(0, 0);
        livingRoom.setSize(120, 30);
        livingRoom.setFont(new Font("Courier New", Font.ITALIC + Font.BOLD, 12));

        label1 = new JLabel();
        label1.setPreferredSize(new Dimension(100, 100));
        label1.setLocation(0, 0);
        label1.setSize(120, 160);
        Util.setPicturetoLabel(label1, "/images/livingRoom.jpg") ;
    }
    /**
     * Allows for outside access to add an actionlisteners
     * @param listener an actionlistner that allows outside access
     */
    public void setActionListener(ActionListener listener) {
        livingRoom.addActionListener(listener);
    }
}
