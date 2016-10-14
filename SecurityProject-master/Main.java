package project.security;

import project.security.Backend.BuildingList;
import project.security.Backend.Clock;

import javax.swing.*;

public class Main {
    /**
     * Main function generates the static class Buildinglist and the deprecated static class Clock
     * Defaults are generated via the bulidinglist
     * MainFrame is called
     * @param args unused
     */
    public static void main(String[] args) {
        new Clock();
        new BuildingList();
        BuildingList.generateDefaults();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame ex = new MainFrame();
                ex.setVisible(true);
            }
        });
    }
}
