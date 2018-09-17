package spacestation.lifesupport;

public class Control {
    private String Name;
    private StatusCode Status = StatusCode.NOMINAL;
    private double SettingIncrement;

    public Control(String name, double settingIncrement) {
        this.Name = name;
        this.SettingIncrement = settingIncrement;
    }

    public StatusCode check() {
        return this.Status;
    }

    public double increment(double value) {
        return SettingIncrement * value;
    }

    public String getName() {
        return Name;
    }
}
