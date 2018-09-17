package spacestation.lifesupport;

public class Sensor {
    private final String Name;
    private final Parameter Parameter;
    private final double MinReading;
    private final double MaxReading;

    public Sensor(String name, Parameter parameter, double minReading, double maxReading) {
        this.Name = name;
        this.Parameter = parameter;
        this.MinReading = minReading;
        this.MaxReading = maxReading;
    }

    public double getReading() {
        return Math.min(Math.max(Parameter.getValue(),MinReading),MaxReading);
    }

    public String getName() {
        return Name;
    }
}
