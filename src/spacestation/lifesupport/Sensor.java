package spacestation.lifesupport;

public class Sensor {
    private final String Name;
    private final ParameterLimit Limit;
    private final double MinReading;
    private final double MaxReading;
    private final int DigitsPrecision=2;

    public Sensor(String name, ParameterLimit parameter, double minReading, double maxReading) {
        this.Name = name;
        this.Limit = parameter;
        this.MinReading = minReading;
        this.MaxReading = maxReading;
    }

    public double getReading() {
        return roundToPrecision(Math.min(Math.max(this.Limit.getParameter().getValue(),MinReading),MaxReading));
    }

    public boolean aboveDesired() {
        //System.out.println(Name + " has a reading of " + getReading() + " vs a limit of " + Limit.UpperSoftBound);
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
        //System.out.println(Name + " is outside safe?" + outsideSafe() + "\n outside desired?" + outsideDesired());
        if (outsideSafe()) return StatusCode.CRITICAL; //BUG: Switching these two lines will make a nice bug
        if (outsideDesired()) return StatusCode.WARNING;
        return StatusCode.NOMINAL;
    }

    public double getDeviationFromDesired() {
        //System.out.println(aboveDesired());
        if (aboveDesired()) {
            //System.out.println("Sensor " + Name + " is above desired by " + (getReading()-Limit.UpperSoftBound));
            return getReading() - Limit.UpperSoftBound;
        }
        if (belowDesired()) {
            //System.out.println("Sensor " + Name + " is below desired by " + (getReading()-Limit.LowerSoftBound));
            return getReading() - Limit.LowerSoftBound;
        }
        return 0;
    }

    public double getDeviationFromOptimal() {
        double optimal = (Limit.UpperSoftBound + Limit.LowerSoftBound)/2.0;
        return getReading() - optimal;
    }
    public ParameterLimit getParameterLimit() {
        return this.Limit;
    }

    public String getName() {
        return Name;
    }

    public double roundToPrecision(double value) {
        return Math.floor(value * Math.pow(10,DigitsPrecision))/Math.pow(10,DigitsPrecision);
    }
}
