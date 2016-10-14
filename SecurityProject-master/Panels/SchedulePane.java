package project.security.Panels;

import project.security.Backend.*;
import project.security.Rooms.PanelCenter;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

/**
 * SchedulePane allows user to set schedule for all sensors
 */
public class SchedulePane extends JPanel{
    //MainSystem system;
    private JFrame allSensors;
    private JPanel bottomPanel;
    private JPanel passPanel, scheduleCard;
    private JPanel sensorCard, optionCard, sensors;
    private JPanel  cardPanel, right;
    private JPanel centerPanel;
    private CardLayout  cards;
    private JFormattedTextField passTF;
    private JTextField roomID, results;
    private JButton enter, enterID, showAll, saveBtn;
    private GridBagConstraints c = new GridBagConstraints();
    private DateFormat format;
    private DateTimeFormatter formatter;

    private ArrayList<Sensor> sensorArray;
    private ArrayList<JCheckBox> manualCBs;
    private ArrayList<JFormattedTextField> weekdayOn;
    private ArrayList<JFormattedTextField> weekdayOff;
    private ArrayList<JFormattedTextField> weekendOn;
    private ArrayList<JFormattedTextField> weekendOff;
    private ArrayList<JFormattedTextField> vacationOn;
    private ArrayList<JFormattedTextField> vacationOff;

    private BuildingList.roomRef currentRef;

    private JLabel numRooms;

    public SchedulePane() {
        setLayout(new BorderLayout());
        format = new SimpleDateFormat("HH:mm");
        formatter = DateTimeFormatter.ofPattern("HH:mm");

        saveBtn = new JButton("Save");
        saveBtn.setActionCommand("save");
        saveBtn.addActionListener(new ButtonHandler());

        bottomPanel = new JPanel(new FlowLayout());

        showAll = new JButton("Show All");
        showAll.setActionCommand("all");
        showAll.addActionListener(new ButtonHandler());

        numRooms = new JLabel("Number of rooms: " + String.valueOf(BuildingList.getBuilding(0).numSubAreas()));
        bottomPanel.add(numRooms);
        bottomPanel.add(showAll);

        cards = new CardLayout();
        cardPanel = new JPanel();
        cardPanel.setLayout(cards);
        cardPanel.setPreferredSize(new Dimension(300, 100));
        right = new JPanel();
        right.setPreferredSize(new Dimension(300,100));

        passPanel = new JPanel(new FlowLayout());

        scheduleCard = new JPanel(new BorderLayout());
        scheduleCard.add(bottomPanel, BorderLayout.SOUTH);

        sensors = new JPanel();
        sensors.setLayout(new BoxLayout(sensors, BoxLayout.Y_AXIS));

        sensorCard = new JPanel();
        sensorCard.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        sensorCard.add(sensors, gbc);
        optionCard = new JPanel();
        //optionCard.setLayout(new BoxLayout(optionCard, BoxLayout.Y_AXIS));
        optionCard.setLayout(new GridBagLayout());

        passTF = new JFormattedTextField(createFormatter("####"));
        passTF.setPreferredSize(new Dimension(100, 30));
        enter = new JButton("Enter");
        enter.setActionCommand("enter");
        enter.addActionListener(new PassHandler());

        centerPanel = new PanelCenter(new RoomHandler());
        scheduleCard.add(centerPanel, BorderLayout.CENTER);

        passPanel.add(new JLabel("Enter 4-digit Password(0000): "));
        passPanel.add(passTF);
        passPanel.add(enter);

        this.add(passPanel);
        cardPanel.add(sensorCard, "sensors");
        cardPanel.add(optionCard, "options");

        scheduleCard.add(cardPanel, BorderLayout.LINE_START);
        scheduleCard.add(right, BorderLayout.LINE_END);
    }

    /**
     * Function creates MaskFormatter for JFormattedTextFields
     * @param s format
     * @return a MaskFormatter
     */
    protected MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        }   catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }

    /**
     * Handles password input
     */
    private class PassHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String inputPass = passTF.getText();
            if(BuildingList.getBuilding(0).getSystemPass().equals(inputPass))
                validPassWord();
        }
    }

    /**
     * If password valid method is called
     * Removes passPanel and adds scheduleCard
     */
    private void validPassWord() {
        this.remove(passPanel);
        this.add(scheduleCard);
    }

    /**
     * Checks for sensors in a room then shows appropriates options
     * @param input Determines which room
     */
    private void checkForSensor(BuildingList.roomRef input) {
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
        sensors.removeAll();
        JLabel roomLbl = new JLabel("Subarea: " + title);
        roomLbl.setFont(new Font("Arial", Font.BOLD, 15));

        sensors.add(roomLbl);
        sensors.add(Box.createRigidArea(new Dimension(0, 5)));
        SubAreas temp = BuildingList.getBuilding(0).getSubArea(room);

        if (temp.hasFireSensor()) {
            JButton fireSensorBtn;
            fireSensorBtn = new JButton("Fire Sensor");
            //fireSensorBtn.setPreferredSize(new Dimension(200, 26));
            fireSensorBtn.setMinimumSize(new Dimension(120, 26));
            fireSensorBtn.setMaximumSize(new Dimension(120, 26));
            fireSensorBtn.setActionCommand("fire");
            fireSensorBtn.addActionListener(new ButtonHandler());
            sensors.add(fireSensorBtn);
            sensors.add(Box.createRigidArea(new Dimension(90, 4)));
        }
        if (temp.hasMotionSensor()) {
            JButton motionSensorBtn;
            motionSensorBtn = new JButton("Motion Sensor");
            //motionSensorBtn.setPreferredSize(new Dimension(200, 26));
            motionSensorBtn.setMaximumSize(new Dimension(120, 26));
            motionSensorBtn.setActionCommand("motion");
            motionSensorBtn.addActionListener(new ButtonHandler());
            sensors.add(motionSensorBtn);
        }
        else if (!temp.hasMotionSensor() && !temp.hasFireSensor()) {
            JLabel none = new JLabel("There are no sensors");
            none.setFont(new Font("Arial", Font.PLAIN, 14));
            sensors.add(none);
        }
        sensorCard.updateUI();
    }

    /**
     * Shows all sensors in Building along with settings for sensor
     */
    private void showAllSensors() {
        allSensors = new JFrame();
        allSensors.setPreferredSize(new Dimension(1000, 350));
        allSensors.setMinimumSize(new Dimension(1000, 350));
        allSensors.setTitle("All Sensors");
        allSensors.setLocationRelativeTo(null);

        Font font = new Font("Serif", Font.BOLD, 14);
        Map attributes = font.getAttributes();
        font = font.deriveFont(attributes);


        //JScrollPane sensorList = new JScrollPane();
        JPanel sensorList = new JPanel(new GridBagLayout());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        JLabel label = new JLabel(" SubArea Id  ");
        label.setFont(font);
        sensorList.add(label, c);
        c.gridx = 1;
        label = new JLabel(" Sensor Type  ");
        label.setFont(font);
        sensorList.add(label, c);
        c.gridx++;
        label = new JLabel(" Manual On/Off  ");
        label.setFont(font);
        sensorList.add(label, c);
        c.gridx++;
        label = new JLabel(" Weekday On  ");
        label.setFont(font);
        sensorList.add(label, c);
        c.gridx++;
        label = new JLabel(" Weekday Off  ");
        label.setFont(font);
        sensorList.add(label, c);
        c.gridx++;
        label = new JLabel(" Weekend On  ");
        label.setFont(font);
        sensorList.add(label, c);
        c.gridx++;
        label = new JLabel(" Weekend Off  ");
        label.setFont(font);
        sensorList.add(label, c);
        c.gridx++;
        label = new JLabel(" Vacation On  ");
        label.setFont(font);
        sensorList.add(label, c);
        c.gridx++;
        label = new JLabel(" Vacation Off  ");
        label.setFont(font);
        sensorList.add(label, c);

        ArrayList<SubAreas> areaList = BuildingList.getBuilding(0).getSubAreaList();
        sensorArray = new ArrayList<>(0);
        int size=0;
        for(SubAreas temp : areaList) {
            if(temp.hasMotionSensor()) {
                size++;
                sensorArray.add(temp.getMotionSensor());
            }
            if(temp.hasFireSensor()) {
                ++size;
                sensorArray.add(temp.getFireSensor());
            }
        }
        manualCBs = new ArrayList<>(size);
        weekdayOn = new ArrayList<>(size);
        weekdayOff= new ArrayList<>(size);
        weekendOn = new ArrayList<>(size);
        weekendOff = new ArrayList<>(size);
        vacationOn = new ArrayList<>(size);
        vacationOff = new ArrayList<>(size);
        for(Sensor temp : sensorArray) {
            JCheckBox tempBox = new JCheckBox();
            tempBox.setSelected(temp.getManualOn());
            tempBox.setToolTipText("State of sensor when in manual mode.");
            manualCBs.add(tempBox);
            JFormattedTextField weekdayOnTF = new JFormattedTextField(format);
            weekdayOnTF.setText(temp.getOnTime(Schedule.Setting.weekday).toString());
            weekdayOn.add(weekdayOnTF);
            JFormattedTextField weekdayOffTF = new JFormattedTextField(format);
            weekdayOffTF.setText(temp.getOffTime(Schedule.Setting.weekday).toString());
            weekdayOff.add(weekdayOffTF);
            JFormattedTextField weekendOnTF = new JFormattedTextField(format);
            weekendOnTF.setText(temp.getOnTime(Schedule.Setting.weekend).toString());
            weekendOn.add(weekendOnTF);
            JFormattedTextField weekendOffTF = new JFormattedTextField(format);
            weekendOffTF.setText(temp.getOffTime(Schedule.Setting.weekend).toString());
            weekendOff.add(weekendOffTF);
            JFormattedTextField vacationOnTF = new JFormattedTextField(format);
            vacationOnTF.setText(temp.getOnTime(Schedule.Setting.vacation).toString());
            vacationOn.add(vacationOnTF);
            JFormattedTextField vacationOffTF = new JFormattedTextField(format);
            vacationOffTF.setText(temp.getOffTime(Schedule.Setting.vacation).toString());
            vacationOff.add(vacationOffTF);
        }
        int i = 0;
        for(Sensor temp: sensorArray) {
            c.gridy = i+1;
            c.gridx = 0;
            sensorList.add(new JLabel("   " + temp.getRoomID() + "   "),c);
            c.gridx++;
            String type;
            if(FireSensor.class.equals(temp.getClass())) {
                type = "Fire Sensor     ";
            } else {
                type = "Motion Sensor     ";
            }
            sensorList.add(new JLabel("   " + type + "   "),c);
            c.gridx++;
            sensorList.add(manualCBs.get(i),c);
            c.gridx++;
            sensorList.add(weekdayOn.get(i), c);
            c.gridx++;
            sensorList.add(weekdayOff.get(i), c);
            c.gridx++;
            sensorList.add(weekendOn.get(i), c);
            c.gridx++;
            sensorList.add(weekendOff.get(i), c);
            c.gridx++;
            sensorList.add(vacationOn.get(i), c);
            c.gridx++;
            sensorList.add(vacationOff.get(i), c);
            i++;
        }

        c.gridy = i+3;
        c.gridx = 5;
        sensorList.add(saveBtn, c);

        allSensors.add(sensorList);
        allSensors.setVisible(true);
    }

    /**
     * Generates the optionCard on the Left
     * @param type type of sensor clicked
     */
    private void generateOptionCard(int type) {
        int room = 0;
        switch (currentRef) {
            case ROOM1:
                room = 1;
                break;
            case CLOSET:
                room = 2;
                break;
            case KITCHEN:
                room = 3;
                break;
            case ROOM2:
                room = 4;
                break;
            case LIVINGROOM:
                room = 5;
                break;
            case BATHROOM:
                room = 6;
                break;
        }
        optionCard.removeAll();

        CardLayout cardLayout = new CardLayout();
        JPanel modeCard = new JPanel(cardLayout);
        JPanel manualCard, weekdayCard, weekendCard, vacationCard;
        manualCard = new JPanel(new GridBagLayout());
        weekdayCard = new JPanel(new GridBagLayout());
        weekendCard = new JPanel(new GridBagLayout());
        vacationCard = new JPanel(new GridBagLayout());
        modeCard.add(manualCard, "manual");
        modeCard.add(weekdayCard, "weekday");
        modeCard.add(weekendCard, "weekend");
        modeCard.add(vacationCard, "vacation");
        JButton cancelBtn1, optionSaveBtn1;

        optionSaveBtn1 = new JButton("Save");
        optionSaveBtn1.setActionCommand("save");
        cancelBtn1 = new JButton("Cancel");
        cancelBtn1.setActionCommand("cancel");

        Sensor sensor;
        if(type == 0) {
            sensor = BuildingList.buildings.get(0).getSubArea(room).getFireSensor();
        }
        else if (type == 1) {
            sensor = BuildingList.buildings.get(0).getSubArea(room).getMotionSensor();
        }
        else
            return;
        GridBagConstraints bc = new GridBagConstraints();
        bc.fill = GridBagConstraints.HORIZONTAL;
        bc.gridwidth = 2;
        bc.gridx = 0;
        bc.gridy = 0;
        JLabel sensorIdLbl = new JLabel("Sensor Id: 0" + sensor.getSensorID());
        sensorIdLbl.setFont(new Font("Serif", Font.BOLD, 14));
        optionCard.add(sensorIdLbl, bc);
        bc.gridy = 1;
        if(type ==0) {
            JLabel sensorType = new JLabel("Type: Fire Sensor");
            sensorType.setFont(new Font("Serif", Font.BOLD, 14));
            optionCard.add(sensorType, bc);
        }
        else {
            JLabel sensorType = new JLabel("Type: Motion Sensor");
            sensorType.setFont(new Font("Serif", Font.BOLD, 14));
            optionCard.add(sensorType, bc);
        }


        String [] modes = {"Manual", "Weekday", "Weekend", "Vacation"};
        JComboBox mode = new JComboBox(modes);
        mode.setFont(new Font("Serif", Font.BOLD, 14));
        bc.gridwidth = 1;
        bc.gridy++;
        JLabel modeLbl = new JLabel("Setting: ");
        modeLbl.setFont(new Font("Serif", Font.BOLD, 14));
        optionCard.add(modeLbl, bc);
        bc.gridx++;
        optionCard.add(mode, bc);
        bc.gridx = 0;
        bc.gridy++;
        bc.gridwidth = 3;
        optionCard.add(modeCard, bc);
        bc.gridy++;
        optionCard.add(Box.createRigidArea(new Dimension(0, 8)), bc);
        bc.gridwidth = 1;
        bc.gridy++;
        optionCard.add(optionSaveBtn1, bc);
        bc.gridx++;
        optionCard.add(cancelBtn1, bc);

        /* Generate fields inside manualCard*/
        bc.fill = GridBagConstraints.HORIZONTAL;
        bc.gridwidth = 1;
        bc.gridx = 0;
        bc.gridy = 0;
        JLabel manLbl = new JLabel("Manual Setting");
        manLbl.setFont(new Font("Serif", Font.BOLD, 15));
        JCheckBox manCB = new JCheckBox("Manual On/Off");
        manCB.setSelected(sensor.getManualOn());

        manualCard.add(Box.createRigidArea(new Dimension(0, 6)), bc);
        bc.gridy++;
        manualCard.add(manLbl, bc);
        bc.gridy++;
        manualCard.add(Box.createRigidArea(new Dimension(0, 2)), bc);
        bc.gridy++;
        manualCard.add(manCB, bc);

        /* Generate fields in weekdayCard */
        bc.fill = GridBagConstraints.HORIZONTAL;
        bc.gridwidth = 1;
        bc.gridx = 0;
        bc.gridy = 0;
        JLabel weekdayLbl = new JLabel("Weekday Settings");
        weekdayLbl.setFont(new Font("Serif", Font.BOLD, 15));

        JFormattedTextField weekdayOnTF = new JFormattedTextField(format);
        weekdayOnTF.setText(sensor.getOnTime(Schedule.Setting.weekday).toString());
        JFormattedTextField weekdayOffTF = new JFormattedTextField(format);
        weekdayOffTF.setText(sensor.getOffTime(Schedule.Setting.weekday).toString());
        JLabel weekdayOnLbl = new JLabel("On time:");
        JLabel weekdayOffLbl = new JLabel("Off time:");

        weekdayCard.add(Box.createRigidArea(new Dimension(0, 6)), bc);
        bc.gridy++;
        bc.gridwidth = 2;
        weekdayCard.add(weekdayLbl, bc);
        bc.gridwidth = 1;
        bc.gridy++;
        weekdayCard.add(Box.createRigidArea(new Dimension(0, 2)), bc);
        bc.gridy++;
        weekdayCard.add(weekdayOnLbl, bc);
        bc.gridx++;
        weekdayCard.add(weekdayOnTF, bc);
        bc.gridwidth = 2;
        bc.gridx = 0;
        bc.gridy++;
        weekdayCard.add(Box.createRigidArea(new Dimension(100, 6)), bc);
        bc.gridwidth = 1;
        bc.gridy++;
        weekdayCard.add(weekdayOffLbl, bc);
        bc.gridx++;
        weekdayCard.add(weekdayOffTF, bc);

        /* Generate fields in weekendCard*/
        bc.fill = GridBagConstraints.HORIZONTAL;
        bc.gridwidth = 1;
        bc.gridx = 0;
        bc.gridy = 0;
        JLabel weekendLbl = new JLabel("Weekend Settings");
        weekendLbl.setFont(new Font("Serif", Font.BOLD, 15));

        JFormattedTextField weekendOnTF = new JFormattedTextField(format);
        weekendOnTF.setText(sensor.getOnTime(Schedule.Setting.weekend).toString());
        JFormattedTextField weekendOffTF = new JFormattedTextField(format);
        weekendOffTF.setText(sensor.getOffTime(Schedule.Setting.weekend).toString());
        JLabel weekendOnLbl = new JLabel("On time:");
        JLabel weekendOffLbl = new JLabel("Off time:");

        weekendCard.add(Box.createRigidArea(new Dimension(0, 6)), bc);
        bc.gridy++;
        bc.gridwidth = 2;
        weekendCard.add(weekendLbl, bc);
        bc.gridwidth = 1;
        bc.gridy++;
        weekendCard.add(Box.createRigidArea(new Dimension(0, 2)), bc);
        bc.gridy++;
        weekendCard.add(weekendOnLbl, bc);
        bc.gridx++;
        weekendCard.add(weekendOnTF, bc);
        bc.gridwidth = 2;
        bc.gridx = 0;
        bc.gridy++;
        weekendCard.add(Box.createRigidArea(new Dimension(100, 6)), bc);
        bc.gridwidth = 1;
        bc.gridy++;
        weekendCard.add(weekendOffLbl, bc);
        bc.gridx++;
        weekendCard.add(weekendOffTF, bc);

        /* Generate fields in vacationCard*/
        bc.fill = GridBagConstraints.HORIZONTAL;
        bc.gridwidth = 1;
        bc.gridx = 0;
        bc.gridy = 0;
        JLabel vacationLbl = new JLabel("Vacation Settings");
        vacationLbl.setFont(new Font("Serif", Font.BOLD, 15));

        JFormattedTextField vacationOnTF= new JFormattedTextField(format);
        vacationOnTF.setText(sensor.getOnTime(Schedule.Setting.vacation).toString());
        JFormattedTextField vacationOffTF = new JFormattedTextField(format);
        vacationOffTF.setText(sensor.getOffTime(Schedule.Setting.vacation).toString());
        JLabel vacationOnLbl = new JLabel("On time:");
        JLabel vacationOffLbl = new JLabel("Off time:");

        vacationCard.add(Box.createRigidArea(new Dimension(0, 6)), bc);
        bc.gridy++;
        bc.gridwidth = 2;
        vacationCard.add(vacationLbl, bc);
        bc.gridwidth = 1;
        bc.gridy++;
        vacationCard.add(Box.createRigidArea(new Dimension(0, 2)), bc);
        bc.gridy++;
        vacationCard.add(vacationOnLbl, bc);
        bc.gridx++;
        vacationCard.add(vacationOnTF, bc);
        bc.gridwidth = 2;
        bc.gridx = 0;
        bc.gridy++;
        vacationCard.add(Box.createRigidArea(new Dimension(100, 6)), bc);
        bc.gridwidth = 1;
        bc.gridy++;
        vacationCard.add(vacationOffLbl, bc);
        bc.gridx++;
        vacationCard.add(vacationOffTF, bc);

        mode.addActionListener(new ActionListener() {
                                   public void actionPerformed(ActionEvent e) {
                                       String s = (String) mode.getSelectedItem();
                                       switch (s) {
                                           case "Manual":
                                               cardLayout.show(modeCard, "manual");
                                               manCB.setSelected(sensor.getManualOn());
                                               return;
                                           case "Weekday":
                                               cardLayout.show(modeCard, "weekday");
                                               return;
                                           case "Weekend":
                                               cardLayout.show(modeCard, "weekend");
                                               return;
                                           case "Vacation":
                                               cardLayout.show(modeCard, "vacation");
                                               return;
                                       }
                                   }
                               }
        );
        class optionsBtnHandler implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand() == "save") {
                    sensor.setManualOn(manCB.isSelected());
                    sensor.setOnTime(Schedule.Setting.weekday, parseString(weekdayOnTF.getText()));
                    sensor.setOffTime(Schedule.Setting.weekday, parseString(weekdayOffTF.getText()));
                    sensor.setOnTime(Schedule.Setting.weekend, parseString(weekendOnTF.getText()));
                    sensor.setOffTime(Schedule.Setting.weekend, parseString(weekendOffTF.getText()));
                    sensor.setOnTime(Schedule.Setting.vacation, parseString(vacationOnTF.getText()));
                    sensor.setOffTime(Schedule.Setting.vacation, parseString(vacationOffTF.getText()));
                    cards.show(cardPanel, "sensors");
                }
                if(e.getActionCommand() == "cancel") {
                    cards.show(cardPanel, "sensors");
                }
            }
        }
        optionSaveBtn1.addActionListener(new optionsBtnHandler());
        cancelBtn1.addActionListener(new optionsBtnHandler());
    }

    /**
     * Parses string to LocalTime
     * @param string string to be parsed
     * @return LocalTime based on input string
     */
    private LocalTime parseString(String string) {
        String sub1 = string.substring(0, 2);
        String sub2 = string.substring(3, 5);
        LocalTime time = LocalTime.of(Integer.parseInt(sub1), Integer.parseInt(sub2));
        return time;
    }

    /**
     * Saves textfields to sensors, from allSensors then closes allSensors
     */
    private void saveTextFields() {
        int i=0;
        for(Sensor temp: sensorArray) {
            temp.setManualOn(manualCBs.get(i).isSelected());
            temp.setOnTime(Schedule.Setting.weekday, parseString(weekdayOn.get(i).getText()));
            temp.setOffTime(Schedule.Setting.weekday, parseString(weekdayOff.get(i).getText()));
            temp.setOnTime(Schedule.Setting.weekend, parseString(weekendOn.get(i).getText()));
            temp.setOffTime(Schedule.Setting.weekend, parseString(weekendOff.get(i).getText()));
            temp.setOnTime(Schedule.Setting.vacation, parseString(vacationOn.get(i).getText()));
            temp.setOffTime(Schedule.Setting.vacation, parseString(vacationOff.get(i).getText()));
            i++;
        }
        allSensors.dispose();

    }

    /**
     * ButtonHandler
     */
    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand() == "all") {
                showAllSensors();
            }
            if(e.getActionCommand() == "fire") {
                generateOptionCard(0);
                cards.next(cardPanel);

            }
            if(e.getActionCommand() == "motion") {
                generateOptionCard(1);
                cards.next(cardPanel);
            }
            if(e.getActionCommand() == "save") {
                saveTextFields();
            }
            if(e.getActionCommand() == "cancel") {
                cards.next(cardPanel);
            }
            if(e.getActionCommand() == "cancel2") {

            }
        }
    }

    /**
     * ButtonHandler for rooms
     */
    private class RoomHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand() == "room1") {
                checkForSensor(BuildingList.roomRef.ROOM1);
                cards.show(cardPanel, "sensors");
            }
            if(e.getActionCommand() == "closet") {
                checkForSensor(BuildingList.roomRef.CLOSET);
                cards.show(cardPanel, "sensors");
            }
            if(e.getActionCommand() == "kitchen") {
                checkForSensor(BuildingList.roomRef.KITCHEN);
                cards.show(cardPanel, "sensors");
            }
            if(e.getActionCommand() == "room2") {
                checkForSensor(BuildingList.roomRef.ROOM2);
                cards.show(cardPanel, "sensors");
            }
            if(e.getActionCommand() == "living room") {
                checkForSensor(BuildingList.roomRef.LIVINGROOM);
                cards.show(cardPanel, "sensors");
            }
            if(e.getActionCommand() == "bathroom") {
                checkForSensor(BuildingList.roomRef.BATHROOM);
                cards.show(cardPanel, "sensors");
            }
        }
    }
}
