package spacestation.lifesupport;

import java.util.ArrayList;

public class LifeSupportSubsystem {
    private final String Name;
    private final ArrayList<Component> SubComponents = new ArrayList<>();
    private final Computer Governor;
    private StatusCode Status;

    public LifeSupportSubsystem(String name, StatusCode status, Computer governor) {
        this.Name = name;
        this.Status = status;
        this.Governor = governor;
    }

    public String check() {
        String str = "\n****************************************\n";
        str += "     " + getName() + " is " + Status + "\n";
        str += "****************************************\n\n";
        for (Component c : SubComponents) {
            str += c.check() + "\n";
        }
        str += "****************************************\n";
        str += "****************************************\n";
        str += "****************************************\n";
        return str;
    }

    public ArrayList<Component> getComponentsWithProblems() {
        ArrayList<Component> ret = new ArrayList<>();
        for (Component c : SubComponents) {
            if (c.getStatus().moreSevere(StatusCode.NOMINAL)) {
                ret.add(c);
            }
        }
        return ret;
    }

    public void add(Component c) {
        SubComponents.add(c);
    }

    public void downgradeStatus(StatusCode newStatus) {
        if (newStatus.moreSevere(Status)) {
            Status = newStatus;
        }
    }

    public void upgradeStatus(StatusCode newStatus) {
        if (newStatus.lessSevere(Status)) {
            Status = newStatus;
        }
    }

    public ArrayList<Sensor> getSensors() {
        ArrayList<Sensor> ret = new ArrayList<>();
        for (Component c : SubComponents) {
            ret.addAll(c.getExtraSensors());
        }
        return ret;
    }

    public void updateModule() {
        Governor.updateModule();
    }

    public StatusCode getStatus() {
        return Status;
    }

    public String getName() {
        return Name;
    }
}
