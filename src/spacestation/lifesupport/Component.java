package spacestation.lifesupport;

import java.util.ArrayList;

public class Component {
    private final String Name;
    private final String SettingName;
    private final double MinSetting;
    private final double MaxSetting;
    private final ArrayList<Sensor> Sensors = new ArrayList<>();
    private final Sensor PrimarySensor;
    private final Control IncreaseController;
    private final Control DecreaseController;
    private double Setting;
    private StatusCode Status;

    public Component(String name, String settingName, StatusCode status, double minSetting, double maxSetting, Control increaseController, Control decreaseController, Sensor primarySensor) {
        this.Name = name;
        this.SettingName = settingName;
        this.Status = status;
        this.MinSetting = minSetting;
        this.MaxSetting = maxSetting;
        this.IncreaseController = increaseController;
        this.DecreaseController = decreaseController;
        this.PrimarySensor = primarySensor;
    }

    public String check() {
        String str = getName() + " is " + Status + "\n";
        str+= SettingName + ": " + getSetting() + "\n\n";
        str += "  Sensors:\n";
        for (Sensor s : Sensors) {
            str += "  " + s.getName() + ": " + s.getReading() + "\n";
        }
        str += "\n  Controls:\n";
        str += "  " + IncreaseController.getName() + ": " + IncreaseController.check() + "\n";
        str += "  " + DecreaseController.getName() + ": " + DecreaseController.check() + "\n";
        return str;
    }

    public StatusCode getStatus() {
        return Status;
    }

    public StatusCode increaseControl(double value) {
        return incrementControl(IncreaseController,value);
    }

    public StatusCode decreaseControl(double value) {
        return incrementControl(DecreaseController,value);
    }

    public double getDeviationFromDesired() {
        return PrimarySensor.getDeviationFromDesired();
    }

    private StatusCode incrementControl(Control c, double value) {
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
        Sensors.add(s);
    }

    public ArrayList<Sensor> getSensors() {
        return Sensors;
    }

    public String getName() {
        return this.Name;
    }

}
