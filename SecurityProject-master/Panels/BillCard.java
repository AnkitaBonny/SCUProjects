package project.security.Panels;

import project.security.Backend.BuildingList;
import project.security.Backend.MainSystem;
import project.security.Backend.SubAreas;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.*;

/**
 * BillCard displays billing information
 * Information is sourced from UserFile.txt and information stored in each SubArea
 */
public class BillCard extends JPanel {
    private JLabel contractIDLabel, nameLabel, addressLabel,cityStateLabel,zipLabel,phoneLabel, contact1Label, contact2Label, emailLabel, dateLabel, fireSensorLabel, motionSensorLabel, pricePerFireLabel, pricePerMotionLabel, priceOfFireLabel, priceOfMotionLabel, totalPriceLabel;
    private JTextField contractIDField, nameField, addressField, cityStateField,zipField,phoneField,contact1Field, contact2Field, emailField, fireSensorField, motionSensorField, pricePerFireField, pricePerMotionLField, priceOfFireField, priceOfMotionField, totalPriceField;
    private JFormattedTextField dateField;
    private JButton update;
    private File inFile;

    public static final int FONTSIZE = 15;
    public final int BOX_SIZE = 27;
    private DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
    private int motionCount;
    private int fireCount;

    /**
     * BillCard constructor
     * Obtains UserFile.txt, obtains list of SubAreas, and fills panel with initial values
     */
    public BillCard() {
        inFile = new File("UserFile.txt");
        ArrayList<SubAreas> areaList = BuildingList.getBuilding(0).getSubAreaList();
        motionCount =0;
        fireCount = 0;
        for(SubAreas temp: areaList) {
            if(temp.hasMotionSensor()) {
                ++motionCount;
            }
            if(temp.hasFireSensor()) {
                ++fireCount;
            }
        }
        /*
        Formatting
         */
        GridBagLayout gbl_panel_1 = new GridBagLayout();

        gbl_panel_1.columnWidths = new int[]{1};
        gbl_panel_1.rowHeights = new int[]{0};

        setLayout(gbl_panel_1);

        final GridBagConstraints c = new GridBagConstraints();
        final GridBagConstraints c1 = new GridBagConstraints();

        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c1.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridy = 1;
        c1.gridy = 16;

        //Initializes textfields and labels with gridbagconstraint c
        initializeTFAndLabel(c);

        add(Box.createRigidArea(new Dimension(0, BOX_SIZE)), c);
        c.gridy++;
        c.gridy++;

        //Add refresh button
        add(Box.createRigidArea(new Dimension(14, 0)), c1);
        update = new JButton("Refresh");
        c1.gridheight = 2;
        update.setFont(new Font("Serif", Font.PLAIN, 16));
        update.setPreferredSize(new Dimension(115, 24));
        add(update, c1);
        c1.gridy++;

        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
        try {
            fillTextFields(inFile);
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    /**
     * Function initializes and adds JTextFields and JLabels
     * @param c the GridBagConstraint to be used
     */
    void initializeTFAndLabel(GridBagConstraints c) {
        //Create Contract Label
        contractIDLabel = createJLabel("Contract ID: ");
        contractIDField = createJTextField(15, "C1120");

        //Insert title and rigidAreas for formatting
        JLabel title = new JLabel("System-Generated Bill");
        title.setFont(new Font("Serif", Font.BOLD, FONTSIZE+9));
        add(title);
        c.gridy++;
        this.add(Box.createRigidArea(new Dimension(260, 15)), c);
        c.gridy++;
        add(contractIDLabel, c);
        add(contractIDField, c);
        add(Box.createRigidArea(new Dimension(0, BOX_SIZE)), c);
        c.gridy++;

        nameLabel = createJLabel("User Name: ");
        nameField = createJTextField(15);

        add(nameLabel, c);
        add(nameField, c);
        add(Box.createRigidArea(new Dimension(0, BOX_SIZE)), c);
        c.gridy++;

        addressLabel = createJLabel("Address:");
        addressField = createJTextField(15);

        add(addressLabel, c);
        add(addressField, c);
        add(Box.createRigidArea(new Dimension(0, BOX_SIZE)), c);
        c.gridy++;

        cityStateLabel = createJLabel("City, State:");
        cityStateField = createJTextField(15);

        add(cityStateLabel, c);
        add(cityStateField, c);
        add(Box.createRigidArea(new Dimension(0, BOX_SIZE)), c);
        c.gridy++;

        zipLabel = createJLabel("ZipCode:");
        zipField = createJTextField(15);

        add(zipLabel, c);
        add(zipField, c);
        add(Box.createRigidArea(new Dimension(0, BOX_SIZE)), c);
        c.gridy++;

        phoneLabel = createJLabel("User Phone No.:");
        phoneField = createJTextField(15);

        add(phoneLabel, c);
        add(phoneField, c);
        add(Box.createRigidArea(new Dimension(0, BOX_SIZE)), c);
        c.gridy++;

        contact1Label = createJLabel("Emergency Contact No.1:");
        contact1Field = createJTextField(15, String.valueOf(MainSystem.getPhone1()));

        add(contact1Label, c);
        add(contact1Field, c);
        add(Box.createRigidArea(new Dimension(0, BOX_SIZE)), c);
        c.gridy++;

        contact2Label = createJLabel("Emergency Contact No.2");
        contact2Field = createJTextField(15, String.valueOf(MainSystem.getPhone2()));

        add(contact2Label, c);
        add(contact2Field, c);
        add(Box.createRigidArea(new Dimension(0, BOX_SIZE)), c);
        c.gridy++;

        emailLabel = createJLabel("Email: ");
        emailField = createJTextField(15);

        add(emailLabel, c);
        add(emailField, c);
        add(Box.createRigidArea(new Dimension(0, BOX_SIZE)), c);
        c.gridy++;

        dateLabel = createJLabel("Date: ");
        dateField = new JFormattedTextField((format));
        dateField.setColumns(15);
        dateField.setPreferredSize(new Dimension(300, FONTSIZE+7));
        dateField.setValue(new java.util.Date());
        dateField.setEditable(false);

        add(dateLabel, c);
        add(dateField, c);
        add(Box.createRigidArea(new Dimension(0, BOX_SIZE)), c);
        c.gridy++;

        fireSensorLabel = createJLabel("No of Firesensors: ");
        fireSensorField = createJTextField(15, String.valueOf(fireCount));

        add(fireSensorLabel, c);
        add(fireSensorField, c);
        add(Box.createRigidArea(new Dimension(0, BOX_SIZE)), c);
        c.gridy++;

        motionSensorLabel = createJLabel("No of Motionsensors: ");
        motionSensorField = createJTextField(15,String.valueOf(motionCount));

        add(motionSensorLabel, c);
        add(motionSensorField, c);
        add(Box.createRigidArea(new Dimension(0, BOX_SIZE)), c);
        c.gridy++;

        pricePerFireLabel = createJLabel("Price per FireSensor:");
        int firePrice = 20; //set price of fire sensor
        pricePerFireField = createJTextField(15, "$" + String.valueOf(firePrice));

        add(pricePerFireLabel, c);
        add(pricePerFireField, c);
        add(Box.createRigidArea(new Dimension(0, BOX_SIZE)), c);
        c.gridy++;

        pricePerMotionLabel = createJLabel("Price per MotionSensor:");
        int motionPrice = 18;   //set price of motion sensor
        pricePerMotionLField = createJTextField(15, "$" + String.valueOf(motionPrice));

        add(pricePerMotionLabel, c);
        add(pricePerMotionLField, c);
        add(Box.createRigidArea(new Dimension(0, BOX_SIZE)), c);
        c.gridy++;

        priceOfFireLabel = createJLabel("Total Price of Firesensors:");
        int priceOfFire = fireCount * firePrice;
        priceOfFireField = createJTextField(15,"$" + String.valueOf(priceOfFire));

        add(priceOfFireLabel, c);
        add(priceOfFireField, c);
        add(Box.createRigidArea(new Dimension(0, BOX_SIZE)), c);
        c.gridy++;

        priceOfMotionLabel = createJLabel("Total Price of Motionsensors:");
        int priceOfMotion = motionCount * motionPrice;
        priceOfMotionField = createJTextField(15, "$" + String.valueOf(priceOfMotion));

        add(priceOfMotionLabel, c);
        add(priceOfMotionField, c);
        add(Box.createRigidArea(new Dimension(0, BOX_SIZE)), c);
        c.gridy++;

        totalPriceLabel = createJLabel("Total Price:");
        int totalPrice= priceOfFire + priceOfMotion;
        totalPriceField = createJTextField(15, "$" + String.valueOf(totalPrice));

        add(totalPriceLabel, c);
        add(totalPriceField, c);
    }

    /**
     * Function to create and format JLabel
     * @param s String to title JLabel
     * @return a JLabel
     */
    JLabel createJLabel(String s) {
        JLabel temp = new JLabel(s);
        temp.setFont(new Font("ARIAL", Font.PLAIN, FONTSIZE));
        return temp;
    }

    /**
     * Function to create and format JTextField
     * @param columns number of columns for JTextField constructor
     * @return a JTextField
     */
    JTextField createJTextField(int columns) {
        JTextField temp = new JTextField(columns);
        temp.setPreferredSize(new Dimension(500, FONTSIZE + 7));
        temp.setEditable(false);
        return temp;
    }

    /**
     * Function to create and format JTextField
     * @param columns number of columns for JTextField constructor
     * @param s string to initialize JTextField with
     * @return a JTextField
     */
    JTextField createJTextField(int columns, String s) {
        JTextField temp = new JTextField(columns);
        temp.setPreferredSize(new Dimension(500, FONTSIZE + 7));
        temp.setText(s);
        temp.setEditable(false);
        return temp;
    }

    /**
     *Update is called by the update button
     * Update will update information on the page from UserFile.txt and each SubArea
     */
    private void update() {
        //Calls method to read from
        try {
            fillTextFields(inFile);
        } catch (IOException e) {
            System.err.print(e);
            System.exit(1);
        }
        this.updateUI();

        contact1Field.setText(String.valueOf(MainSystem.getPhone1()));
        contact2Field.setText(String.valueOf(MainSystem.getPhone2()));

        ArrayList<SubAreas> areaList = BuildingList.getBuilding(0).getSubAreaList();

        motionCount =0;
        fireCount = 0;
        //Count number of each type of sensors
        for(SubAreas temp: areaList) {
            if(temp.hasMotionSensor()) {
                ++motionCount;
            }
            if(temp.hasFireSensor()) {
                ++fireCount;
            }
        }

        fireSensorField.setText(String.valueOf(fireCount));
        motionSensorField.setText(String.valueOf(motionCount));

        int firePrice = 20;
        int priceOfFire = fireCount * firePrice;
        priceOfFireField.setText("$" + String.valueOf(priceOfFire));

        int motionPrice = 18;
        int priceOfMotion = motionCount * motionPrice;
        priceOfMotionField.setText("$" + String.valueOf(priceOfMotion));

        int totalPrice= priceOfFire + priceOfMotion;
        totalPriceField.setText("$" + String.valueOf(totalPrice));
    }

    /**
     *Fill textfields will open the file and read information from the file
     * @param inFile input file to obtain user information
     * @throws IOException
     */
    public void fillTextFields(File inFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inFile));
        nameField.setText(reader.readLine());
        addressField.setText(reader.readLine());
        cityStateField.setText(reader.readLine());
        zipField.setText(reader.readLine());
        phoneField.setText(reader.readLine());
        emailField.setText(reader.readLine());
        reader.close();
    }
}




