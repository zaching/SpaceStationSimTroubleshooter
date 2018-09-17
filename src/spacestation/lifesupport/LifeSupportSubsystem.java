package spacestation.lifesupport;

import java.util.ArrayList;

public class LifeSupportSubsystem {
    private String Name;
    private StatusCode Status;
    private ArrayList<Component> SubComponents = new ArrayList<>();

    public LifeSupportSubsystem(String name, StatusCode status) {
        this.Name = name;
        this.Status = status;
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

    public void add(Component c) {
        SubComponents.add(c);
    }

    public String getName() {
        return Name;
    }
}
