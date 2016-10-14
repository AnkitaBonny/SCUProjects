package project.security.Rooms;

import project.security.Rooms.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class PanelCenter extends JPanel {
    private Shape areaShapeOne;
    private Shape bedRoomOne;
    private Shape bedRoomTwo;
    private Shape kitchen1;
    private Shape bathRoomTwo;
    private Shape closet1;

    private Kitchen kitchen = new Kitchen ();
    private Bedroom1 bedroom1 = new Bedroom1 ();
    private Bedroom2 bedroom2 = new Bedroom2 ();
    private Bathroom bathroom = new Bathroom ();
    private Closet closet = new Closet ();
    private LivingRoom livingRoom = new LivingRoom ();

    /**
     * PanelCenter creator
     * @param listener allows external access to button actions
     */
    public PanelCenter(ActionListener listener) {
        setActionListener(listener);
        setBackground(Color.white);
        setVisible(true);

        this.add(kitchen);
        this.add(bedroom1);
        this.add(bedroom2);
        this.add(bathroom);
        this.add(closet);
        this.add(livingRoom);

        double shapeOneX = 50;
        double shapeOneY = 50;
        double shapeOneWidth = 500;
        double shapeOneHeight = 500;
        areaShapeOne = new Rectangle2D.Double(shapeOneX, shapeOneY, shapeOneWidth, shapeOneHeight);

        setPreferredSize(new Dimension(600, 600));
        setLayout(null);
    }

    /**
     * Paints Building and adds room images
     * @param g graphics
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setStroke(new BasicStroke(5));
        g2.draw(areaShapeOne);

        // Create Areas from the shapes.
        Area areaOne = new Area(areaShapeOne);

        bedRoomOne = new Rectangle2D.Double(50, 50, 200, 200);
        kitchen1 = new Rectangle2D.Double(350, 50, 200, 200);
        closet1 = new Rectangle2D.Double(250, 50, 100, 250);

        bedRoomTwo = new Rectangle2D.Double(50, 250, 200, 300);
        bathRoomTwo = new Rectangle2D.Double(250, 400, 100, 150);

        // Draw the outline of the resulting Area.
        g2.setPaint(Color.black);
        g2.draw(areaOne);
        g2.draw(bedRoomOne);
        g2.draw(kitchen1);
        g2.draw(closet1);
        g2.draw(bedRoomTwo);
        g2.draw(bathRoomTwo);
    }

    /**
     * Calls methods to add listener to all rooms
     * @param listener actionlistener for all room buttons
     */
    public void setActionListener(ActionListener listener) {
        kitchen.setActionListener(listener);
        bedroom1.setActionListener(listener);
        bedroom2.setActionListener(listener);
        bathroom.setActionListener(listener);
        closet.setActionListener(listener);
        livingRoom.setActionListener(listener);
    }


}