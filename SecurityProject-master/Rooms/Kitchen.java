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
 * Class to generate image and button for kitchen
 */
public class Kitchen extends JPanel {
    private JLabel label1;
    private JButton kitchen;

    public Kitchen() {
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setBounds(352, 52, 195, 195);

        // set up image in the picture panel
        setKitchenPanel();
        add(label1);
        add(kitchen);
    }
    /**
     * Set up label for picture. Then add picture to the label. Add label to the
     * kitchen panel.
     *
     */
    public void setKitchenPanel() {
        kitchen = new JButton("Kitchen");
        kitchen.setActionCommand("kitchen");
        kitchen.setBackground(Color.CYAN);
        kitchen.setLocation(0, 0);
        kitchen.setSize(105, 30);
        kitchen.setFont(new Font("Courier New", Font.ITALIC + Font.BOLD, 12));

        label1 = new JLabel();
        label1.setPreferredSize(new Dimension(100, 100));
        label1.setLocation(0, 0);
        label1.setSize(120, 160);
        Util.setPicturetoLabel(label1, "/images/kitchen.jpg") ;
    }
    /**
     * Allows for outside access to add an actionlisteners
     * @param listener an actionlistner that allows outside access
     */
    public void setActionListener(ActionListener listener) {
        kitchen.addActionListener(listener);
    }
}