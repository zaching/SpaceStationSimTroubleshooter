package spacestation.lifesupport;

import java.util.ArrayList;

public class LifeSupportSubsystem {
    private String Name;
    private Status Status;
    private String StatusDescription;
    private ArrayList<Component> SubComponents;

    public LifeSupportSubsystem(String name, Status status, String statusDescription) {
        this.Name = name;
        this.Status = status;
        this.StatusDescription = statusDescription;
    }

    public String check() {
        String str = getName() + " is " + Status + ", " + StatusDescription + "; Subcomponents:\n";
        for (Component c : SubComponents) {
            str += c.check() + "\n";
        }
        return str;
    }

    public String getName() {
        return Name;
    }
}
