package spacestation.lifesupport;

public class Control {
    private final String Name;
    private final double MaxIncrement;
    private final double SettingIncrement;
    private StatusCode Status = StatusCode.NOMINAL;

    public Control(String name, double settingIncrement, double maxIncrement) {
        this.Name = name;
        this.SettingIncrement = settingIncrement;
        this.MaxIncrement = maxIncrement;
    }

    public StatusCode check() {
        return this.Status;
    }

    public void malfunction() {
        if (Status.lessSevere(StatusCode.CRITICAL)) {
            Status = StatusCode.ERROR;
        }
    }

    public void criticalFailure() {
        Status = StatusCode.CRITICAL;
    }

    public double getIncrement() {
        return SettingIncrement;
    }

    public double increment(double value) {
        if (Status == StatusCode.NOMINAL) {
            return SettingIncrement * Math.min(value,MaxIncrement);
        }
        return 0;
    }

    public String getName() {
        return Name;
    }
}
