package project.security.Backend;

import java.time.LocalTime;

/**
 * SubArea represents a room
 * Contains 0-1 firesensors
 * Contains 0-1 motionsensors
 */
public class SubAreas {
    private int subAreaId;
    private Schedule schedule;
    private FireSensor fs = null;
    private MotionSensor ms = null;
    private Boolean fireSensorExists = false;
    private Boolean motionSensorExists = false;

    /**
     * Subarea creator
     * @param subAreaId new Subarea id
     * @param schedule a schedule
     */
    public SubAreas(int subAreaId, Schedule schedule) {
        this.subAreaId = subAreaId;
        this.schedule = schedule;
    }

    /**
     * returns subareaid
     * @return integer value
     */
    public int getSubAreaId() {
        return subAreaId;
    }

    /**
     * Change subareaid
     * @param subAreaId new SubArea id
     */
    public void setSubAreaId(int subAreaId) {
        this.subAreaId = subAreaId;
    }

    /**
     * Creates a firesensor in room
     * @return success value
     */
    public boolean createFireSensor() {
        if (fs != null)
            return false;
        int sensorID = subAreaId*2-1;
        fs = new FireSensor(sensorID, subAreaId);
        //Adds a sensor to the schedule list
        schedule.addSensor(fs);
        fireSensorExists = true;
        return true;
    }

    /**
     * Returns local firesensor
     * @return firesensor
     */
    public Sensor getFireSensor() {
        return fs;
    }

    /**
     * returns local motionsensor
     * @return motionsensor
     */
    public Sensor getMotionSensor() {
        return ms;
    }

    /**
     * Creates a motionsensor in room
     * @return success value
     */
    public boolean createMotionSensor() {
        if (ms != null)
            return false;
        int sensorID = subAreaId*2;
        ms = new MotionSensor(sensorID, subAreaId);
        schedule.addSensor(ms);
        motionSensorExists = true;
        return true;
    }

    /**
     * Method returns if room has firesensor
     * @return boolean value
     */
    public boolean hasFireSensor() {
        return fireSensorExists;
    }

    /**
     * Method returns if room has motionsensor
     * @return boolean value
     */
    public boolean hasMotionSensor() {
        return motionSensorExists;
    }

    /**
     * remove firesensor from local room
     * Calls funtion to remove sensor from schedule
     * @return surprisingly
     */
    public boolean removeFireSensor() {
        if (fs == null)
            return false;
        schedule.removeSensor(fs);
        fs = null;
        fireSensorExists = false;
        return true;
    }

    /**
     * Remove motionsensor from local room
     * Calls function to remove sensor from schedule
     * @return success value
     */
    public boolean removeMotionSensor() {
        if (ms == null)
            return false;
        schedule.removeSensor(ms);
        ms = null;
        motionSensorExists = false;
        return true;
    }

    /**
     * Sets subarea setting
     * @param setting new setting
     */
    public void setSetting(Schedule.Setting setting) {
        if(hasFireSensor())
            fs.setSetting(setting);
        if(hasMotionSensor())
            ms.setSetting(setting);
    }

    /**
     * Sets subarea time
     * @param time new time
     */
    public void setTime(LocalTime time) {
        if(hasFireSensor())
            fs.setTime(time);
        if(hasMotionSensor())
            ms.setTime(time);
    }

    /**
     * sets firesensor on
     * @deprecated
     */
    public void setFireSensorOn(){
        fs.setOn(true);
    }

    /**
     * sets firesensor off
     * @deprecated
     */
    public void setFireSensorOff() {
        fs.setOn(false);
    }

    /**
     * sets motionsensor on
     * @deprecated
     */
    public void setMotionSensorOn() {
        ms.setOn(true);
    }

    /**
     *sets motion sensor off
     * @deprecated
     */
    public void setMotionSensorOff() {
        ms.setOn(false);
    }

}