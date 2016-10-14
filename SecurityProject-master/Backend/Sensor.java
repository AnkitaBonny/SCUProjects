package project.security.Backend;

import java.time.LocalTime;

/**
 * Sensor abstract class which defines majority of firesensor and motionsensor class
 */
public abstract class Sensor {
    protected int sensorID;
    protected int roomID;
    //protected int passCode;
    protected Boolean on;
    //protected LocalTime onTime, offTime;
    protected LocalTime weekdayOn, weekdayOff;
    protected LocalTime weekendOn, weekendOff;
    protected LocalTime vacationOn, vacationOff;
    protected Boolean manualOn;
    private Schedule.Setting setting = Schedule.Setting.weekday;

    /**
     * Sensor creator which instantiates default on/off values
     * @param sensorID id of new sensor
     * @param roomID id of room sensor contained in
     */
    public Sensor(int sensorID, int roomID) {
        this.sensorID = sensorID;
        this.roomID = roomID;
        on = true;
        manualOn = true;

        //Arbitrary defaults
        weekdayOn = LocalTime.of(19,0);
        weekdayOff = LocalTime.of(8,30);
        weekendOn = LocalTime.of(16,0);
        weekendOff = LocalTime.of(10,0);
        vacationOn = LocalTime.MIN;
        vacationOff = LocalTime.of(23,59);
    }

    /**
     * returns id of sensor
     * @return sensorID
     */
    public Integer getSensorID() {
        return sensorID;
    }

    /**
     * Sets the setting of the sensor
     * @param setting current mod3
     */
    public void setSetting(Schedule.Setting setting) {
        this.setting = setting;
        System.out.println(sensorID + ": " + setting);
    }

    /**
     * Sets the current time of the sensor
     * @param time new time
     */
    public void setTime(LocalTime time) {
        //update on status
        if(setting == Schedule.Setting.manual) {
            on = manualOn;
            return;
        }
        LocalTime onTime = getOnTime(setting);
        LocalTime offTime = getOffTime(setting);
        if(onTime.isBefore(offTime)) {
            on = onTime.isBefore(time) && offTime.isAfter(time);
        }
        else {
            on = !(offTime.isBefore(time) && onTime.isAfter(time));
        }
    }

    /**
     * returns localtime of sensor
     * @param setting current setting
     * @return the on time of sensor under a certain mode
     */
    public LocalTime getOnTime(Schedule.Setting setting) {
        switch (setting) {
            case weekday:
                return weekdayOn;
            case weekend:
                return weekendOn;
            case vacation:
                return vacationOn;
            default:
                return LocalTime.MIN;
        }
    }

    /**
     * returns localtime of sensor
     * @param setting current setting
     * @return the off time of sensor under a certain mode
     */
    public LocalTime getOffTime(Schedule.Setting setting) {
        switch (setting) {
            case weekday:
                return weekdayOff;
            case weekend:
                return weekendOff;
            case vacation:
                return vacationOff;
            default:
                return LocalTime.MAX;
        }
    }

    /**
     * Sets the onTime of a specific mode
     * @param setting mode to set ontime
     * @param time new ontime
     */
    public void setOnTime(Schedule.Setting setting, LocalTime time) {
        switch (setting) {
            case weekday:
                weekdayOn = time;
                return;
            case weekend:
                weekendOn = time;
                return;
            case vacation:
                vacationOn = time;
                return;
            default:
                return;
        }
    }

    /**
     * Set offtime of specific setting
     * @param setting mode
     * @param time time to set
     */
    public void setOffTime(Schedule.Setting setting, LocalTime time) {
        switch (setting) {
            case weekday:
                weekdayOff = time;
                return;
            case weekend:
                weekendOff = time;
                return;
            case vacation:
                vacationOff = time;
                return;
            default:
                return;
        }
    }

    /**
     * Checks to see if sensor is on
     * @return true or fales
     */
    public Boolean isOn() {
        return on;
    }

    /**
     * Set to on mode regardless of mode
     * @param on true is one, false is off
     */
    public void setOn(Boolean on) {
        this.on = on;
    }

    /**
     * Sets manualmode on
     * @param manualOn true is on, false is off
     */
    public void setManualOn(Boolean manualOn) {
        this.manualOn = manualOn;
    }

    /**
     * Returns manualOn setting
     * @return boolean
     */
    public Boolean getManualOn() {
        return manualOn;
    }

    /**
     * sets mode to manual
     * If manual on is active, sensor is on
     */
    public void manualMode() {
        if(manualOn)
            on = true;
    }

    /**
     * return roomID
     * @return integer value
     */
    public int getRoomID() {
        return roomID;
    }

    /**
     * Class to be implemented in child classes.
     */
    abstract void raiseAlarm();
}
