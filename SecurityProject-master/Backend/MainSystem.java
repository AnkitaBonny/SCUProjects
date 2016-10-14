package project.security.Backend;

/**
 * Created by Daniel on 8/14/2015.
 */
public class MainSystem {
    //passwordA
    private String password;
    private static int phone1, phone2;
    private static boolean usePhone1, usePhone2;
    private Schedule schedule;
    //Clock clock;
    public MainSystem () {
        schedule = new Schedule();
        usePhone1 = false;
        usePhone2 = false;
        //Clock.setTime(LocalTime.now());
        //new Clock();
    }
    public Schedule getSchedule() {
        return schedule;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    public void setPhone1(int phone1) {
        MainSystem.phone1 = phone1;
    }
    public void setPhone2(int phone2) {
        MainSystem.phone2 = phone2;
    }
    public static void enablePhone1(Boolean enabled) {
        usePhone1 = enabled;
    }
    public static void enablePhone2(Boolean enabled) {
        usePhone2 = enabled;
    }
    public static Boolean phone1IsEnabled() {
        return usePhone1;
    }
    public static Boolean phone2IsEnabled() {
        return usePhone2;
    }
    public static int getPhone1() {
        return phone1;
    }
    public static int getPhone2() {
        return phone2;
    }
    /*
    public void setTime(LocalTime time) {
        //Clock.setTime(time);
        schedule.updateSchedule();
    }
    */
}
