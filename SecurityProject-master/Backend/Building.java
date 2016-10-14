package project.security.Backend;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Building class contains an array list of subareas and the mainsystem
 * Provides functionality to access subareas and mainsystem
 */
public class Building {
    private BufferedImage image;
    private URL imageURL;
    private int buildingId;
    private ArrayList<SubAreas> subAreaList;
    private MainSystem mainSystem;

    /**
     * Building constructor
     * @param buildingId Numerical id of the Building
     */
    public Building(int buildingId)
    {
        this.buildingId = buildingId;
        mainSystem = new MainSystem();
    }

    /**
     * Function to create a subareas in the building.
     * Take in number of rooms to be created, calls subarea creater and adds subarea to list
     * @param rooms number of rooms to create
     */
    public void createSubAreas(int rooms) {
        subAreaList = new ArrayList<>(rooms);
        //loop to create rooms number of subAreas, numbered from 1 to number_of_rooms. These are added to subAreaList.
        for(int i=0; i<rooms; i++) {
            //SubAreas temp = new SubAreas(i+1, mainSystem.getSchedule());
            SubAreas temp = new SubAreas(numSubAreas()+1, mainSystem.getSchedule());
            subAreaList.add(temp);
        }
    }

    /**
     * Returns a specific Subarea by SubareaID
     * @param subAreaId id to find
     * @return a specific subarea
     */
    public SubAreas getSubArea(int subAreaId) {
        for(SubAreas temp : subAreaList) {
            if(temp.getSubAreaId() == subAreaId) {
                int index = subAreaList.indexOf(temp);
                return subAreaList.get(index);
            }
        }
        return null;
    }


    /**
     * @return the subareaList
     */
    public ArrayList<SubAreas> getSubAreaList() {
        return subAreaList;
    }

    /**
     * Function returns the password
     * @return password to unlock system as a String
     */
    public String getSystemPass() {
        return mainSystem.getPassword();
    }

    /**
     * Function sets password
     * @param password password to be set as a String
     */
    public void setSystemPass(String password) {
        mainSystem.setPassword(password);
    }

    /**
     * Returns image url of the Building
     * @return a URL
     */
    public URL getImage() {
        return imageURL;
    }

    /**
     * Sets imagage url of image
     * @param image image url
     */
    public void setImage(URL image) {
        this.imageURL = image;
    }

    /**
     * Returns size of subarealist
     * @return number of subareas
     */
    public int numSubAreas() {
        return subAreaList.size();
    }

    /**
     * Set phone1
     * @param phone1 first phone number to call
     */
    public void setPhone1(int phone1) {
        mainSystem.setPhone1(phone1);
    }

    /**
     * Ret phone 2
     * @param phone2 second phone number to call
     */
    public void setPhone2(int phone2) {
        mainSystem.setPhone2(phone2);
    }

    /**
     * Returns buildingId
     * @return the buildingid
     */
    public int getBuildingId() {
        return buildingId;
    }

    /**
     * Sets the Building ID
     * @param buildingId the buildngId to be changed to
     */
    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    /**
     * Updates the current system settings of all subareas
     * @param setting Current system settings
     */
    public void updateSettings(Schedule.Setting setting) {
        for(SubAreas s: subAreaList) {
            s.setSetting(setting);
        }
    }

    /**
     * Updates the time seen by each subarea
     * @param time LocalTime to be set for each subarea
     */
    public void updateTime(LocalTime time) {
        for(SubAreas s: subAreaList) {
            s.setTime(time);
        }
    }
}
