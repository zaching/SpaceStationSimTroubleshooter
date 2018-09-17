package spacestation.lifesupport;

import java.util.ArrayList;

public class Component {
    private String Name;
    private Status Status;
    private String StatusDescription = "no issues";
    private String SettingName;
    private Double MinSetting;
    private Double MaxSetting;
    private ArrayList<Sensor> sensors;
    private ArrayList<Control> controls;

    public Component(String name, String settingName, Status status, String statusDescription, Double minSetting, Double maxSetting) {
        this.Name = name;
        this.SettingName = settingName;
        this.Status = status;
        this.StatusDescription = statusDescription;
        this.MinSetting = minSetting;
        this.MaxSetting = maxSetting;
    }

    public String getName() {
        return this.Name;
    }

    public String check() {
        String str = getName() + " is " + Status + ", " + StatusDescription + "; Sensors:\n";
        for (Sensor s : sensors) {
            str += s.check() + "\n";
        }
        str += "\nComponents:\n";
        for (Control c : controls) {
            str += c.check() + "\n";
        }
        return str;
    }

    public String test() {
        String str = "Testing all sensors in " + getName() + "\n";
        for (Sensor s : sensors) {
            s.test();
            str += "Tested " + s.getName();
        }
        str += "\nComponents:\n";
        for (Control c : controls) {
            c.test();
            str += "Tested " + c.getName();
        }
        return s;
    }

    public void add(Sensor s) {
        sensors.add(s);
    }

    public void add(Control c) {
        controls.add(c);
    }

}
