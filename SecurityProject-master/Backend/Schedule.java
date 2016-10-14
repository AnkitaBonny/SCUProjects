package project.security.Backend;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Schedule class manages schedule of sensors
 * Static enumerator of current mode
 */
public class Schedule {
    boolean isArmed, fireArmed, motionArmed;
    private LocalTime time;
    public enum Setting {weekday, weekend, vacation, manual}
    private Setting setting;
    private ArrayList<Sensor> sensors = new ArrayList<>(20);

    /**
     * creator sets default value to weekday
     */
    public Schedule () {
        setting = Setting.weekday;
    }

    /**
     * Turns all sensors off
     */
    public void allOff() {
        for(Sensor temp: sensors) {
            temp.setOn(false);
        }
    }

    /**
     * disarm calls alloff method
     * @deprecated
     */
    public void disarm() {
        allOff();
    }

    /**
     * arm will activate system
     * @deprecated
     */
    public void arm() {
        if(setting == Setting.manual)
            manualOn();
        else
            updateSchedule();
    }

    /**
     * Turns on all sensors
     * @deprecated
     */
    public void allOn() {
        for(Sensor temp: sensors) {
            temp.setOn(true);
        }
    }

    /**
     * Turns on(or off) all firesensors
     * @deprecated
     * @param on boolean determines on or off
     */
    public void allFireSensorsOn(Boolean on) {
        setting = Setting.manual;
        for(Sensor temp: sensors) {
            if(temp.getClass().equals(FireSensor.class)) {
                if(on)
                    temp.setOn(true);
                else
                    temp.setOn(false);
            }

        }
    }
    /**
     * Turns on(or off) all motionsensors
     * @deprecated
     * @param on boolean determines on or off
     */
    public void allMotionSensorsOn(Boolean on) {
        setting = Setting.manual;
        for(Sensor temp: sensors) {
            if(temp.getClass().equals(MotionSensor.class)) {
                if(on)
                    temp.setOn(true);
                else
                    temp.setOn(false);
            }

        }
    }

    /**
     * Turns on all sensors which have manual selected
     */
    public void manualOn() {
        for(Sensor temp : sensors)
            temp.manualMode();
    }

    /**
     * updateschedule of all sensors based on current mode
     */
    public void updateSchedule() {
        if(setting == Setting.manual)
            return;
        updateSetting();
        if(setting == Setting.manual)
            return;
        this.time = Clock.getTime();
        for(Sensor temp: sensors) {
            if(!temp.getOnTime(setting).isAfter(time)) {
                temp.setOn(true);
            } else {
                break;
            }
        }
        //sortByOff();
        for(Sensor temp: sensors) {
            if(!temp.getOffTime(setting).isBefore(time)) {
                temp.setOn(false);
            } else {
                break;
            }
        }
    }

    /**
     * Adds a sensor to sensor list
     * @param aSensor newly created sensor
     */
    public void addSensor(Sensor aSensor) {
        sensors.add(aSensor);
        sortById();
    }

    /**
     * Removes a sensor from the list
     * @param aSensor sensor to be removed
     */
    public void removeSensor(Sensor aSensor) {
        sensors.remove(aSensor);
        sortById();
    }

    /**
     * Changes schedule setting(mode)
     * @param setting setting to be changed to
     * @deprecated
     */
    public void setSetting(Schedule.Setting setting) {
        this.setting = setting;
        if (setting == Setting.manual) {
            manualOn();
        }
        else updateSchedule();
    }

    /**
     * Activates vacation mode
     * @deprecated
     * @param days number of days on vacation
     */
    public void setVacation(int days) {
        this.setting = Setting.vacation;
        Clock.setVacation(days);
        updateSchedule();
    }

    /**
     * updates setting based on clock setitngs
     * @deprecated
     */
    public void updateSetting() {
        if(setting == Setting.manual)
            return;
        else if(Clock.onVacation())
            setting = Setting.vacation;
        else if(Clock.isWeekend())
            setting = Setting.weekend;
        else
            setting = Setting.weekday;
    }

    /**
     * returns isArmed boolean
     * @return boolean
     */
    public boolean isArmed() {
        return isArmed;
    }

    /**
     * Checks if firealarms are armed
     * @deprecated
     * @return armed or not
     */
    public boolean isFireArmed() {
        return fireArmed;
    }

    /**
     * Checks if motionalarms are armed
     * @deprecated
     * @return armed or not
     */
    public boolean isMotionArmed() {
        return motionArmed;
    }

    /**
     * Sorts sensor list
     * @deprecated
     */
    private void sortByOn() {
        Collections.sort(sensors, ByOnTime);
    }
    /**
     * Sorts sensor list
     * @deprecated
     */
    private void sortByOff() {
        Collections.sort(sensors, ByOffTime);
    }
    /**
     * Sorts sensor list by id
     */
    private void sortById() {
        Collections.sort(sensors, BySensorId);
    }

    /**
     * Comparotor to sort sensor list by the on time
     * @deprecated
     */
    private final Comparator<Sensor> ByOnTime = new Comparator<Sensor>() {
        @Override
        public int compare(Sensor o1, Sensor o2) {
            return o1.getOnTime(setting).compareTo(o2.getOnTime(setting));
        }
    };
    /**
     * Comparator to sort sensor list by sensor id
     */
    private final Comparator<Sensor> BySensorId= new Comparator<Sensor>() {
        @Override
        public int compare(Sensor o1, Sensor o2) {
            return o1.getSensorID().compareTo(o2.getSensorID());
        }
    };
    /**
     * Comparator to sort sensor list by offtime
     * @deprecated
     */
    private final Comparator<Sensor> ByOffTime = new Comparator<Sensor>() {
        @Override
        public int compare(Sensor o1, Sensor o2) {
            return o1.getOffTime(setting).compareTo(o2.getOffTime(setting));
        }
    };
}

