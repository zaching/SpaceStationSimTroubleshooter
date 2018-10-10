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
        double currentTemp = getReading();
        return currentTemp > Limit.UpperSoftBound;
    }

    public boolean belowDesired() {
        double currentTemp = getReading();
        return currentTemp < Limit.LowerSoftBound;
    }

    public boolean willBeAboveDesired(double amtTempWillChange) {
        double currentTemp = getReading();
        return currentTemp + amtTempWillChange > Limit.UpperSoftBound;
    }

    public boolean willBeBelowDesired(double amtTempWillChange) {
        double currentTemp = getReading();
        return currentTemp + amtTempWillChange < Limit.LowerSoftBound;
    }

    public boolean willBeOutsideDesired(double amtTempWillChange) {
        return willBeBelowDesired(amtTempWillChange) || willBeAboveDesired(amtTempWillChange);
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

    public double getDeviationFromDesired(double amtTempWillChange) {
        //System.out.println(aboveDesired());
        if (willBeAboveDesired(amtTempWillChange)) {
            //System.out.println("Sensor " + Name + " is above desired by " + (getReading()-Limit.UpperSoftBound));
            double currentTemp = getReading();
            return currentTemp - Limit.UpperSoftBound;
        }
        if (willBeBelowDesired(amtTempWillChange)) {
            //System.out.println("Sensor " + Name + " is below desired by " + (getReading()-Limit.LowerSoftBound));
            double currentTemp = getReading();
            return currentTemp - Limit.LowerSoftBound;
        }
        return 0;
    }

    public double getDeviationFromOptimal() {
        double optimal = getOptimal();
        return getReading() - optimal;
    }

    public double getDeviationFromOptimal(double amtTempWillChange) {
        double optimal = getOptimal();
        double currentTemp = getReading();
        return currentTemp + amtTempWillChange - optimal;
    }

    public double getOptimal() {
        return (Limit.UpperSoftBound + Limit.LowerSoftBound)/2.0;
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
