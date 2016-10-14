package project.security.Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * First panel visible to user
 * Allows user to save user information
 * Informatoin saved to textfile
 */
public class RegisterPane extends JPanel {
    private JButton save;
    private JLabel  nameLabel, addressLabel, cityStateLabel, zipLabel, phoneLabel, emailLabel;
    private static JTextField  nameField, addressField, cityStateField, zipField, phoneField,  emailField;

    public static final int FONTSIZE = 16;
    private Font font = new Font("Arial", Font.PLAIN, FONTSIZE);

    private GridBagConstraints c = new GridBagConstraints();
    public RegisterPane(ActionListener listener) {
        File inFile = new File("UserFile.txt");
        //If inFile does not exist, it is created
        try {
            if (!inFile.exists()) {
                inFile.createNewFile();
            }
        } catch (IOException e) {
            JFrame errorFrame = new JFrame("");
            JPanel errorPanel = new JPanel();
            errorFrame.getContentPane().add(errorPanel);
            errorPanel.add(new JLabel("File not found and file unable to be created. Please create file: UserFile.txt in Jar location."));
            errorFrame.setVisible(true);
        }
        setLayout(new GridBagLayout());
        save = new JButton("Save");
        save.setActionCommand("save");
        save.addActionListener(listener);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        //c.weighty = 1;
        c.gridwidth = 2;
        JLabel title = new JLabel("New User Registration");
        title.setFont(new Font("Serif", Font.BOLD, 24));
        add(title, c);

        c.gridy++;
        this.add(Box.createRigidArea(new Dimension(290, 5)), c);

        c.gridwidth = 1;
        nameLabel = new JLabel("First and Last Name:    ");
        nameField = new JTextField(15);
        addThis(nameLabel, nameField);

        addressLabel = new JLabel("Street Address:    ");
        addressField = new JTextField(15);
        addThis(addressLabel, addressField);

        cityStateLabel = new JLabel("City, State:    ");
        cityStateField = new JTextField(15);
        addThis(cityStateLabel, cityStateField);

        zipLabel = new JLabel("Zip Code:     ");
        zipField = new JTextField(15);
        addThis(zipLabel, zipField);

        phoneLabel = new JLabel("Phone Number:    ");
        phoneField = new JTextField(15);
        addThis(phoneLabel, phoneField);

        emailLabel = new JLabel("Email Address:    ");
        emailField = new JTextField(15);
        addThis(emailLabel, emailField);

        //calls fillTextFields method, obtains values from textfile
        try {
            fillTextFields(inFile);
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 2;
        this.add(Box.createRigidArea(new Dimension(290, 10)), c);
        c.gridy ++;
        c.gridwidth = 1;
        c.gridx = 1;
        this.add(save, c);
    }

    /**
     * Adds Jlabel and Jtextfield to panel
     * @param label label to be addedj
     * @param textField textfield to be added
     */
    private void addThis(JLabel label, JTextField textField) {
        c.gridy++;
        c.gridwidth = 2;
        c.gridx = 0;
        this.add(Box.createRigidArea(new Dimension(290, 5)), c);
        c.gridy++;
        c.gridwidth = 1;
        label.setFont(font);
        add(label, c);
        c.gridx = 1;
        add(textField, c);
        c.gridx = 0;
    }

    /**
     * Method to save information written to textfile
     * @param toFile filename
     * @throws IOException
     */
    public static void saveText(File toFile) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(toFile));
        writer.write(nameField.getText());
        writer.newLine();
        writer.write(addressField.getText());
        writer.newLine();
        writer.write(cityStateField.getText());
        writer.newLine();
        writer.write(zipField.getText());
        writer.newLine();
        writer.write(phoneField.getText());
        writer.newLine();
        writer.write(emailField.getText());
        writer.newLine();
        writer.close();
    }

    /**
     * Automatically fills textfields based on current values in textfile
     * @param inFile textfile to source user information from
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
