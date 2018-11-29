package spacestation.lifesupport;


import java.util.HashSet;
import java.util.Set;

public class LifeSupportSystem {
    private final String Name;
    private final Set<Component> SubComponents = new HashSet<>();
    private Computer Governor;

    public LifeSupportSystem(String name) {
        this.Name = name;
    }

    public String sitRep() {
        String str = "\n****************************************\n";
        str += "     " + getName() + " is " + getStatus() + "\n";
        str += "****************************************\n\n";
        for (Component c : SubComponents) {
            str += c.sitRep() + "\n";
        }
        str += "****************************************\n";
        str += "****************************************\n";
        str += "****************************************\n";
        return str;
    }

    public String getQuickSummary() {
        String str = getName() + ": " + getStatus() + "\n";
        for (Sensor s : getSensors()) {
            str += s.getName() + ": " + s.getReading() + " (" + s.getStatus() + ")\n";
        }
        for (Component c : SubComponents) {
            str += c.getQuickSummary();
        }
        str = str.substring(0,str.length()-2) +"\n";
        for (Component c : SubComponents) {
            StatusCode increaseStatus = c.getIncreaseControllerStatus();
            if (increaseStatus.moreSevere(StatusCode.NOMINAL)) {
                str += c.getName() + " has an increase control w/ status: " + increaseStatus + "\n";
            }
            StatusCode decreaseStatus = c.getDecreaseControllerStatus();
            if (decreaseStatus.moreSevere(StatusCode.NOMINAL)) {
                str += c.getName() + " has an decrease control w/ status: " + decreaseStatus + "\n";
            }
        }
        return str;
    }

    public Set<Component> getComponentsWithProblems() {
        Set<Component> ret = new HashSet<>();
        for (Component c : SubComponents) {
            if (c.getStatus().moreSevere(StatusCode.NOMINAL)) {
                ret.add(c);
            }
        }
        return ret;
    }

    public Set<Component> getComponents() {
        return SubComponents;
    }

    public void add(Component c) {
        SubComponents.add(c);
    }

    public void add(Computer governor) { this.Governor = governor; }

    public Set<Sensor> getSensors() {
        HashSet<Sensor> ret = new HashSet<>();
        for (Component c : SubComponents) {
            ret.add(c.getPrimarySensor());
            if (c.getSecondarySensor() != null) {
                ret.add(c.getSecondarySensor());
            }
        }
        return ret;
    }

    public void updateModule() {
        Governor.updateModule();
    }

    public StatusCode getStatus() {
        StatusCode s = StatusCode.NOMINAL;
        for (Component c : SubComponents) {
            s = s.getWorseStatus(c.getStatus());
        }
        return s;
    }

    public String getName() {
        return Name;
    }
}
