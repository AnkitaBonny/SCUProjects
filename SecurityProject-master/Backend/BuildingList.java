package project.security.Backend;

import java.net.URL;
import java.util.ArrayList;

/**
 * Static class which contains the list of buildings
 * Provides access to SubAreas, and Sensors through the Buildings
 * Static enumerator: roomRef
 */
public class BuildingList {
    public static ArrayList<Building> buildings = new ArrayList<>(2);
    public enum roomRef {KITCHEN, LIVINGROOM, CLOSET, ROOM1, ROOM2, BATHROOM}

    /**
     * Creator for BuildingList
     * Cteates building, sets password, and supplies image URL
     */
    public BuildingList() {
        Building temp = new Building(1);
        temp.createSubAreas(6);
        temp.setSystemPass("0000");
        URL url = this.getClass().getClassLoader()
                .getResource("singleHouse.jpg");
        temp.setImage(url);

        buildings.add(temp);
        temp = new Building(2);
        url = this.getClass().getClassLoader()
                .getResource("commercial.jpg");
        temp.setImage(url);
        buildings.add(temp);
    }

    /**
     * Function alows access to Building from list
     * @param index the index of the building
     * @return the building found at index
     */
    public static Building getBuilding(int index) {
        return buildings.get(index);
    }

    /**
     * Generates the default sensor data
     */
    public static void generateDefaults() {
        Building temp = BuildingList.getBuilding(0);
        SubAreas temp2 = temp.getSubArea(1);
        temp2.createFireSensor();
        temp2.createMotionSensor();
        temp2 = temp.getSubArea(2);
        temp2.createMotionSensor();
        temp2 = temp.getSubArea(3);
        temp2.createMotionSensor();
        temp2.createFireSensor();
        temp2 = temp.getSubArea(5);
        temp2.createFireSensor();
        temp2 = temp.getSubArea(6);
        temp2.createFireSensor();
        temp2.createMotionSensor();
    }
}
