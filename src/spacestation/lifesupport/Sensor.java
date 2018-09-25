package spacestation.lifesupport;

public class Sensor {
    private final String Name;
    private final ParameterLimit Limit;
    private final double MinReading;
    private final double MaxReading;

    public Sensor(String name, ParameterLimit parameter, double minReading, double maxReading) {
        this.Name = name;
        this.Limit = parameter;
        this.MinReading = minReading;
        this.MaxReading = maxReading;
    }

    public double getReading() {
        return Math.min(Math.max(this.Limit.getParameter().getValue(),MinReading),MaxReading);
    }

    public boolean aboveDesired() {
        return getReading() > Limit.UpperSoftBound;
    }

    public boolean belowDesired() {
        return getReading() < Limit.LowerSoftBound;
    }

    public boolean aboveSafe() {
        return getReading() > Limit.UpperHardBound;
    }

    public boolean belowSafe() {
        return getReading() < Limit.LowerHardBound;
    }

    public boolean outsideDesired() {
        return aboveDesired() || belowDesired();
    }

    public boolean outsideSafe() {
        return aboveSafe() || belowSafe();
    }

    public StatusCode getStatus() {
        if (outsideSafe()) return StatusCode.CRITICAL; //BUG: Switching these two lines will make a nice bug
        if (outsideDesired()) return StatusCode.WARNING;
        return StatusCode.NOMINAL;
    }
    public double getDeviationFromDesired() {
        if (aboveDesired()) {
            return getReading() - Limit.UpperSoftBound;
        }
        if (belowDesired()) {
            return getReading() - Limit.LowerSoftBound;
        }
        return 0;
    }
    public ParameterLimit getParameterLimit() {
        return this.Limit;
    }

    public String getName() {
        return Name;
    }
}
