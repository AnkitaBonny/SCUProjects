package project.security.Backend;

import java.lang.System;

/**
 * FireSensor class which extends the abstract class sensor
 * Specifies action of fire sensors
 */
public class FireSensor extends Sensor{
    public FireSensor(int sensorID, int roomID) {
        super(sensorID, roomID);
    }

    public void raiseAlarm() {
        System.out.println("Fire Detected in room: " + roomID);
        //Make phone call a thread, to be canceled when passCode is inputted
        if(MainSystem.phone1IsEnabled())
            System.out.println("Call: " + MainSystem.getPhone1());
        if(MainSystem.phone2IsEnabled())
            System.out.println("Call: " + MainSystem.getPhone2());
    }
}
