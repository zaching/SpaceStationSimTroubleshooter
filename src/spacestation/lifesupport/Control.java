package spacestation.lifesupport;

public class Control {
    private final String Name;
    private final double SettingIncrement;
    private final int Direction;
    private StatusCode Status = StatusCode.NOMINAL;
    private int RoundsBroken = 0;

    public Control(String name, double settingIncrement, int direction) {
        this.Name = name;
        this.SettingIncrement = settingIncrement;
        this.Direction = direction;
    }

    public StatusCode getStatus() {
        return this.Status;
    }

    public void malfunction(int roundsBroken) {
        if (Status.lessSevere(StatusCode.CRITICAL)) {
            Status = StatusCode.ERROR;
            this.RoundsBroken += roundsBroken;
        }
    }

    public void criticalFail() {
        Status = StatusCode.CRITICAL;
    }

    public void repair() {
        System.out.println("Repairing \"" + Name +"\".  Before repairs it is: " + Status);
        System.out.println("Attempting repairs...");
        if (Status.lessSevere(StatusCode.CRITICAL)) {
            RoundsBroken++;
            if (RoundsBroken <= 0) {
                Status = StatusCode.NOMINAL;
                RoundsBroken = 0;
                System.out.println("Repairs are completed, it is now: " + Status + "\n");
            }
            else {
                System.out.println("Repairs are underway, but \"" + Name + "\" will still take " + RoundsBroken + " rounds to be repaired.\n");
            }
        }
        else {
            System.out.println("Repair failed because \"" + Name + "\" was unrepairable");
        }
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
