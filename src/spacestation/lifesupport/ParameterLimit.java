package spacestation.lifesupport;

public class ParameterLimit {
    private final Parameter p;
    public final double UpperSoftBound;
    public final double LowerSoftBound;
    public final double UpperHardBound;
    public final double LowerHardBound;

    public ParameterLimit(Parameter p, double upperSoftBound, double lowerSoftBound, double upperHardBound, double lowerHardBound) {
        this.p = p;
        this.UpperSoftBound = upperSoftBound;
        this.LowerSoftBound = lowerSoftBound;
        this.UpperHardBound = upperHardBound;
        this.LowerHardBound = lowerHardBound;
    }

    public Parameter getParameter() { return p;}
}
