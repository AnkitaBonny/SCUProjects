package project.security.Panels;

import project.security.Backend.BuildingList;
import project.security.Backend.MainSystem;
import project.security.Rooms.PanelCenter;
import project.security.Backend.SubAreas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.net.URL;
import java.util.Map;

import javax.swing.*;

/**
 * SetupCard allows user to add or remove sensors from rooms in a building
 * Also allows user to specify use for emergency phone numbers
 */
public class SetupCard extends JPanel {

    private JComboBox combo;
    private JPanel panelRight, panelLeft, topPanel;
    private JPanel panelMiddle;
    private PanelCenter panelCenter = new PanelCenter(new RoomHandler());
    private JTextField enterNumber1, enterNumber2;
    private JLabel firstNumber, secondNumber;
    private JLabel status;
    private JButton savePhoneNumbers;
    private JCheckBox fireSensor, motionSensor;
    private BuildingList.roomRef currentRef;
    String soundName = "yourSound.wav";

    /**
     * SetupCard Constructor
     */
    public SetupCard() {
        setLayout(new BorderLayout());

        panelRight = new JPanel(new GridBagLayout());
        topPanel = new JPanel(new FlowLayout());
        topPanel.setPreferredSize(new Dimension(700, 35));
        panelLeft = new JPanel(new GridBagLayout());
        panelMiddle = new JPanel();

        panelMiddle.setBackground(Color.white);

        add(topPanel, BorderLayout.NORTH);
        add(panelRight, BorderLayout.LINE_END);
        add(panelLeft, BorderLayout.LINE_START);
        add(panelMiddle, BorderLayout.CENTER);

        //ComboBox to control which Building to display
        combo = new JComboBox();
        combo.addItem("Select a building");
        combo.addItem("Single House");
        combo.addItem("Commercial");

        combo.setMinimumSize(new Dimension(200, 25));
        combo.setMaximumSize(new Dimension(200, 25));
        topPanel.add(combo);

        panelRight.setPreferredSize(new Dimension(270, 100));
        panelRight.setMaximumSize(new Dimension(270, 100));

        panelLeft.setPreferredSize(new Dimension(270, 30));
        panelLeft.setMaximumSize(new Dimension(270, 100));

        panelCenter.setBackground(Color.white);

        JCheckBox enablePhone1 = new JCheckBox();
        JCheckBox enablePhone2 = new JCheckBox();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;

        firstNumber = new JLabel(" Enter First Phone number:", JLabel.LEFT);
        firstNumber.setFont(new Font("Serif", Font.BOLD, 15));
        enterNumber1 = new JTextField();
        enterNumber1.setPreferredSize(new Dimension(100, 30));
        enterNumber1.setMaximumSize(new Dimension(150, 30));
        enterNumber1.setEditable(true);

        gbc.gridx = 1;
        panelRight.add(firstNumber, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        panelRight.add(enablePhone1, gbc);
        gbc.gridx++;
        gbc.gridwidth = 2;
        panelRight.add(enterNumber1, gbc);

        gbc.gridy++;
        panelRight.add(Box.createRigidArea(new Dimension(150, 8)), gbc);

        secondNumber = new JLabel(" Enter Second Phone number:", JLabel.LEFT);
        secondNumber.setFont(new Font("Serif", Font.BOLD, 15));
        enterNumber2 = new JTextField();
        enterNumber2.setPreferredSize(new Dimension(100, 30));
        enterNumber2.setMaximumSize(new Dimension(50, 30));
        enterNumber2.setEditable(true);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy++;
        panelRight.add(secondNumber, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        panelRight.add(enablePhone2, gbc);
        gbc.gridx++;
        gbc.gridwidth = 2;
        panelRight.add(enterNumber2, gbc);

        gbc.gridy++;
        panelRight.add(Box.createRigidArea(new Dimension(150, 12)), gbc);

        savePhoneNumbers = new JButton("Save");
        savePhoneNumbers.setPreferredSize(new Dimension(100, 30));
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy++;
        panelRight.add(savePhoneNumbers, gbc);
        status = new JLabel("");
        gbc.gridy++;
        panelRight.add(status, gbc);

        savePhoneNumbers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!enterNumber1.getText().equals("")) {
                    BuildingList.getBuilding(0).setPhone1(Integer.parseInt(enterNumber1.getText()));
                    if(enablePhone1.isSelected())
                        MainSystem.enablePhone1(true);
                    else
                        MainSystem.enablePhone1(false);
                }
                if(!enterNumber2.getText().equals("")) {
                    BuildingList.getBuilding(0).setPhone2(Integer.parseInt(enterNumber2.getText()));
                    if(enablePhone2.isSelected())
                        MainSystem.enablePhone2(true);
                    else
                        MainSystem.enablePhone2(false);
                }
                status.setText("Saved!");
            }
        });

        combo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String selectedBuilding = (String) combo.getSelectedItem();

                if (selectedBuilding.equals("Single House")) {
                    createHouse();
                } else if (selectedBuilding.equals("Commercial")) {
                    createCBuilding();
                    return;
                }
                else if (selectedBuilding.equals("Select a building")) {
                    clearMiddle();
                }
                status.setText("");
            }
        });
    }

    /**
     * Shows house in panelMiddle
     */
    private void createHouse() {
        this.remove(panelMiddle);
        panelMiddle = panelCenter;
        this.add(panelMiddle, BorderLayout.CENTER);
        this.updateUI();
    }

    /**
     * Shows corporate building in panelMiddle
     */
    private void createCBuilding() {
        this.remove(panelMiddle);
        panelMiddle = new JPanel();
        panelMiddle.setBackground(Color.white);

        URL url = this.getClass().getClassLoader()
                .getResource("images/commercial.jpg");
        try {
            JLabel commercialLabel = new JLabel(new ImageIcon(url));
            panelMiddle.add(commercialLabel);
        } catch (NullPointerException e){
            System.err.print(e);
            System.exit(-1);

        }

        this.add(panelMiddle, BorderLayout.CENTER);
        this.updateUI();
    }

    /**
     * Removes panelMiddle and adds a new blank one
     */
    private void clearMiddle() {
        this.remove(panelMiddle);
        panelMiddle = new JPanel();
        panelMiddle.setBackground(Color.white);

        this.add(panelMiddle, BorderLayout.CENTER);
        this.updateUI();
    }

    /**
     * Allows user to add or remove a sensor from a room
     * This method displays the current sensors on the left side
     * @param input room reference to be added to
     */
    private void addSensors(BuildingList.roomRef input) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        int room = 0;
        String title = "";
        switch (input) {
            case ROOM1:
                room = 1;
                title = "Bedroom1";
                break;
            case CLOSET:
                room = 2;
                title = "Closet";
                break;
            case KITCHEN:
                room = 3;
                title = "Kitchen";
                break;
            case ROOM2:
                room = 4;
                title = "Bedroom2";
                break;
            case LIVINGROOM:
                room = 5;
                title = "Living Room";
                break;
            case BATHROOM:
                room = 6;
                title = "Bathroom";
                break;
        }
        currentRef = input;
        panelLeft.removeAll();
        JLabel roomLbl = new JLabel("Subarea: " + title);
        roomLbl.setFont(new Font("Arial", Font.BOLD, 16));
        Font font = roomLbl.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        roomLbl.setFont(font.deriveFont(attributes));

        c.gridwidth = 2;
        panelLeft.add(roomLbl, c);
        c.gridy++;
        panelLeft.add(Box.createRigidArea(new Dimension(150, 5)), c);

        SubAreas temp = BuildingList.getBuilding(0).getSubArea(room);

        fireSensor = new JCheckBox("Fire Sensor");
        fireSensor.setSelected(temp.hasFireSensor());


        motionSensor = new JCheckBox("Motion Sensor");
        motionSensor.setSelected(temp.hasMotionSensor());

        c.gridy++;
        panelLeft.add(fireSensor, c);
        c.gridy++;
        panelLeft.add(motionSensor, c);

        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");

        class ButtonHandler implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                status.setText("");
                if(e.getActionCommand() == "save") {
                    if(temp.hasFireSensor() && !fireSensor.isSelected())
                        temp.removeFireSensor();
                    else if(!temp.hasFireSensor() && fireSensor.isSelected())
                        temp.createFireSensor();
                    if(temp.hasMotionSensor() && !motionSensor.isSelected())
                        temp.removeMotionSensor();
                    else if(!temp.hasMotionSensor() && motionSensor.isSelected())
                        temp.createMotionSensor();
                }
                panelLeft.removeAll();
                panelLeft.updateUI();
            }
        }

        saveBtn.setActionCommand("save");
        saveBtn.addActionListener(new ButtonHandler());
        saveBtn.setMaximumSize(new Dimension(50, 20));

        cancelBtn.setActionCommand("cancel");
        cancelBtn.addActionListener(new ButtonHandler());
        cancelBtn.setMaximumSize(new Dimension(50, 20));

        c.gridwidth = 2;
        c.gridy++;
        panelLeft.add(Box.createRigidArea(new Dimension(150, 5)), c);

        c.gridwidth = 1;
        c.gridy++;
        panelLeft.add(saveBtn, c);
        c.gridx++;
        panelLeft.add(cancelBtn, c);

        panelLeft.updateUI();
    }

    /**
     * ButtonHandler to be passed to PanelCenter to control house buttons
     */
    private class RoomHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            status.setText("");
            if(e.getActionCommand() == "room1") {
                addSensors(BuildingList.roomRef.ROOM1);
            }
            if(e.getActionCommand() == "closet") {
                addSensors(BuildingList.roomRef.CLOSET);
            }
            if(e.getActionCommand() == "kitchen") {
                addSensors(BuildingList.roomRef.KITCHEN);
            }
            if(e.getActionCommand() == "room2") {
                addSensors(BuildingList.roomRef.ROOM2);
            }
            if(e.getActionCommand() == "living room") {
                addSensors(BuildingList.roomRef.LIVINGROOM);
            }
            if(e.getActionCommand() == "bathroom") {
                addSensors(BuildingList.roomRef.BATHROOM);
            }

        }
    }
}
