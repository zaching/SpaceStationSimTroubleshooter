package spacestation.lifesupport;

public class Control {
    private final String Name;
    private final double SettingIncrement;
    private final int Direction;
    private StatusCode Status = StatusCode.NOMINAL;

    public Control(String name, double settingIncrement, int direction) {
        this.Name = name;
        this.SettingIncrement = settingIncrement;
        this.Direction = direction;
    }

    public StatusCode getStatus() {
        return this.Status;
    }

    public void malfunction() {
        if (Status.lessSevere(StatusCode.CRITICAL)) {
            Status = StatusCode.ERROR;
        }
    }

    public void criticalFail() {
        Status = StatusCode.CRITICAL;
    }

    public void repair() {
        System.out.print("Repairing " + Name +"; was: " + Status);
        if (Status.lessSevere(StatusCode.CRITICAL)) Status = StatusCode.NOMINAL;
        System.out.println("; is now: " + Status);
    }

    public double getIncrementSize() {
        return SettingIncrement;
    }

    public double increment(double value) {
        if (Status == StatusCode.NOMINAL) {
            return Math.min(value,SettingIncrement)*Direction;
        }
        return 0;
    }

    public String getName() {
        return Name;
    }
}
