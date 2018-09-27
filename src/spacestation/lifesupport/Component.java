package spacestation.lifesupport;

public class Component {
    private final String Name;
    private final String SettingName;
    private final double MinSetting; //The min setting for this component (how low can you set the throttle)
    private final double MaxSetting; //The max setting for this component (how high can you set the throttle)
    private final Sensor PrimarySensor; //This is the main sensor this component is trying to solve for
    private final Sensor SecondarySensor; //A secondary sensor to measure side effects, if relevant
    private final Control IncreaseController;
    private final Control DecreaseController;
    private double CurrentSetting;
    public final double SettingPrimaryImpact; //How many units the target parameter changes for each unit of setting change
    public final double SettingSecondaryImpact; //How many units the secondary parameter changes (only relevant if component has side effects)

    public Component(String name, String settingName, double minSetting, double maxSetting, double settingPrimaryImpact, double settingSecondaryImpact, Control increaseController, Control decreaseController, Sensor primarySensor, Sensor secondarySensor) {
        this.Name = name;
        this.SettingName = settingName;
        this.MinSetting = minSetting;
        this.MaxSetting = maxSetting;
        this.SettingPrimaryImpact = settingPrimaryImpact;
        this.SettingSecondaryImpact = settingSecondaryImpact;
        this.IncreaseController = increaseController;
        this.DecreaseController = decreaseController;
        this.PrimarySensor = primarySensor;
        this.SecondarySensor = secondarySensor;
    }

    public String sitRep() {
        String str = getName() + " is " + getStatus() + "\n";
        str+= SettingName + ": " + getCurrentSetting() + "\n\n";
        str += "  Sensors:\n";
        str += " " + PrimarySensor.getName() + ": " + PrimarySensor.getReading() + "\n";
        if (SecondarySensor != null) {
            str += "  " + SecondarySensor.getName() + ": " + SecondarySensor.getReading() + "\n";
        }
        str += "\n  Controls:\n";
        str += "  " + IncreaseController.getName() + ": " + IncreaseController.getStatus() + "\n";
        str += "  " + DecreaseController.getName() + ": " + DecreaseController.getStatus() + "\n";
        return str;
    }

    public StatusCode getStatus() {
        StatusCode s = StatusCode.NOMINAL;
        s = s.getWorseStatus(IncreaseController.getStatus());
        s = s.getWorseStatus(DecreaseController.getStatus());
        s = s.getWorseStatus(PrimarySensor.getStatus());
        if (SecondarySensor != null) {
            s = s.getWorseStatus(SecondarySensor.getStatus());
        }
        return s;
    }

    public void repairControls() {
        if (IncreaseController.getStatus().moreSevere(StatusCode.NOMINAL)) IncreaseController.repair();
        if (DecreaseController.getStatus().moreSevere(StatusCode.NOMINAL)) DecreaseController.repair();
    }

    public StatusCode increaseControl(double value) {
        return incrementControl(IncreaseController,value);
    }

    public StatusCode decreaseControl(double value) {
        return incrementControl(DecreaseController,value);
    }

    public double getDeviationFromDesired() {
        return PrimarySensor.getDeviationFromDesired();
    }

    private StatusCode incrementControl(Control c, double value) {
        value = Math.abs(value);
        CurrentSetting += c.increment(value);
        //If an invalid setting was sent, return a warning to the sender
        if (getCurrentSetting() >= MinSetting && value <= MaxSetting) {
            return StatusCode.NOMINAL;
        }
        fixSetting();
        return StatusCode.WARNING;
    }

    private void fixSetting() {
        CurrentSetting = Math.min(Math.max(getCurrentSetting(), MinSetting), MaxSetting);
    }

    public Sensor getPrimarySensor() { return PrimarySensor; }

    public Sensor getSecondarySensor() { return SecondarySensor; }

    public double getCurrentSetting() {
        return this.CurrentSetting;
    }

    public String getName() {
        return this.Name;
    }

}
