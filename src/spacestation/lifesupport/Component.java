package spacestation.lifesupport;

import java.util.ArrayList;

public class Component {
    private String Name;
    private StatusCode Status;
    private String SettingName;
    private double Setting;
    private double MinSetting;
    private double MaxSetting;
    private ArrayList<Sensor> sensors = new ArrayList<>();
    private ArrayList<Control> controls = new ArrayList<>();

    public Component(String name, String settingName, StatusCode status, double minSetting, double maxSetting) {
        this.Name = name;
        this.SettingName = settingName;
        this.Status = status;
        this.MinSetting = minSetting;
        this.MaxSetting = maxSetting;
    }

    public String check() {
        String str = getName() + " is " + Status + "\n";
        str+= SettingName + ": " + getSetting() + "\n\n";
        str += "  Sensors:\n";
        for (Sensor s : sensors) {
            str += "  " + s.getName() + ": " + s.getReading() + "\n";
        }
        str += "\n  Controls:\n";
        for (Control c : controls) {
            str += "  " + c.getName() + ": " + c.check() + "\n";
        }
        return str;
    }

    public StatusCode incrementControl(Control c, double value) {
        value = Math.abs(value);
        Setting += c.increment(value);
        if (getSetting() >= MinSetting && value <= MaxSetting) {
            return StatusCode.NOMINAL;
        }
        fixSetting();
        return StatusCode.WARNING;
    }

    private void fixSetting() {
        Setting = Math.min(Math.max(getSetting(), MinSetting), MaxSetting);
    }

    public double getSetting() {
        return this.Setting;
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
