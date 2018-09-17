package spacestation.lifesupport;

public class Control {
    private String Name;
    private Status Status = Status.NOMINAL;
    private String StatusDescription = "no issues";
    private Double SettingIncrement;

    public Control(String name, Double settingIncrement) {
        this.Name = name;
        this.SettingIncrement = settingIncrement;
    }
}
