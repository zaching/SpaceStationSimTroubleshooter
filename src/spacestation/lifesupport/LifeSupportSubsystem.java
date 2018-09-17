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

    public String getName() {
        return Name;
    }

    public String check() {
        String str = getName() + " is " + Status + ", " + StatusDescription + "; Subcomponents:\n";
        for (Component c : SubComponents) {
            str += c.check() + "\n";
        }
        return str;
    }

    public String test() {
        String str = "Testing all subcomponents in " + getName() + "\n";
        for (Component c : SubComponents) {
            c.test();
            str += "Tested " + c.getName();
        }
        return str;
    }
}
