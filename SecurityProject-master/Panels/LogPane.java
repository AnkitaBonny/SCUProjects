package project.security.Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * LogPane allows user access to the action logs
 * Log displays events that occurred and is stored in the text file
 * Allows user to refresh the log or clear the log which erases data from the log text file
 */
public class LogPane extends JPanel{
    private JPanel center;
    private static GridBagConstraints c = new GridBagConstraints();
    private File inFile;
    private JButton refresh;
    private JButton clear;

    /**
     * Logpane creator
     * Creates the center panel and sets the format
     * Pane is filled with text from inFile
     * Implements refresh and clear button
     */
    public LogPane() {
        center = new JPanel();
        center.setLayout(new GridBagLayout());

        inFile = new File("LogFile.txt");
        //If inFile does  not exist, creates inFile at location
        try {
            if (!inFile.exists()) {
                inFile.createNewFile();
            }
        } catch (IOException e) {
            JFrame errorFrame = new JFrame("");
            JPanel errorPanel = new JPanel();
            errorFrame.getContentPane().add(errorPanel);
            errorPanel.add(new JLabel("File not found and file unable to be created. Please create file: LogFile.txt in Jar location."));
            errorFrame.setVisible(true);
        }
        /*
        Buttons
         */
        refresh = new JButton("Refresh");
        refresh.setActionCommand("refresh");
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    refreshPane(inFile);
                } catch (IOException e) {
                    System.err.println(e);
                    System.exit(1);
                }
            }
        });
        clear = new JButton("Clear");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearFile(inFile);
                } catch (IOException exc) {
                    System.err.println(exc);
                    System.exit(1);
                }
            }
        });
        try {
            refreshPane(inFile);
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
        setVisible(true);
    }

    /**
     * Method to refresh the pane
     * Center is cleared then refilled
     * @param inFile File to source log information from
     * @throws IOException
     */
    public void refreshPane (File inFile) throws IOException {
        center.removeAll();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        c.gridx = 0;

        JLabel title = new JLabel("Event Log: ");
        title.setFont(new Font("Serif", Font.BOLD, 18));
        center.add(title, c);
        c.gridx++;
        center.add(refresh, c);

        c.gridx++;
        center.add(clear, c);

        this.add(center);

        BufferedReader reader = new BufferedReader(new FileReader(inFile));
        String line;
        c.gridx=0;
        c.gridwidth = 3;
        c.gridy++;
        center.add(Box.createRigidArea(new Dimension(100, 10)), c);
        while ((line=reader.readLine()) != null) {
            c.gridy++;
            center.add(new JLabel(line), c);
        }
        c.gridwidth = 1;
        reader.close();
    }

    /**
     * Method to clear the file, opens the file then adds a blank string
     * @param inFile File to obtain log information
     * @throws IOException
     */
    public void clearFile(File inFile) throws IOException {
        center.removeAll();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        c.gridx = 0;

        JLabel title = new JLabel("Event Log: ");
        title.setFont(new Font("Serif", Font.BOLD, 18));
        center.add(title, c);
        c.gridx++;
        center.add(refresh, c);
        c.gridx++;
        center.add(clear, c);

        this.add(center);

        PrintWriter writer = new PrintWriter(inFile);
        writer.print("");
        writer.close();
    }
}
