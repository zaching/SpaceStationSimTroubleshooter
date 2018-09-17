package spacestation.lifesupport;

import java.util.ArrayList;

public class Component {
    private String Name;
    private Status Status;
    private String StatusDescription = "no issues";
    private String SettingName;
    private double MinSetting;
    private double MaxSetting;
    private ArrayList<Sensor> sensors;
    private ArrayList<Control> controls;

    public Component(String name, String settingName, Status status, String statusDescription, double minSetting, double maxSetting) {
        this.Name = name;
        this.SettingName = settingName;
        this.Status = status;
        this.StatusDescription = statusDescription;
        this.MinSetting = minSetting;
        this.MaxSetting = maxSetting;
    }

    public String check() {
        String str = getName() + " is " + Status + ", " + StatusDescription + "; Sensors:\n";
        for (Sensor s : sensors) {
            str += s.getName() + ": " + s.getReading() + "\n";
        }
        str += "\nComponents:\n";
        for (Control c : controls) {
            str += c.getName() + ": " + c.check() + "\n";
        }
        return str;
    }

    public void add(Sensor s) {
        sensors.add(s);
    }

    public void add(Control c) {
        controls.add(c);
    }

    public String getName() {
        return this.Name;
    }
}
