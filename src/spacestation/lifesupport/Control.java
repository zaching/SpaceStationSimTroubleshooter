package spacestation.lifesupport;

public class Control {
    private String Name;
    private Status Status = Status.NOMINAL;
    private String StatusDescription = "no issues";
    private double SettingIncrement;

    public Control(String name, double settingIncrement) {
        this.Name = name;
        this.SettingIncrement = settingIncrement;
    }



    public String getName() {
        return Name;
    }
}
