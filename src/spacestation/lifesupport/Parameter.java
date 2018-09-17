package spacestation.lifesupport;

public class Parameter {
    private final String Name;
    private final String UnitOfMeasure;
    private double Value;

    public Parameter(String name, String unitOfMeasure, double value) {
        this.Name = name;
        this.UnitOfMeasure = unitOfMeasure;
        this.Value = value;
    }

    public String getName() {
        return Name;
    }

    public String getUnitOfMeasure() {
        return UnitOfMeasure;
    }

    public double getValue() {
        return Value;
    }

    public void setValue(double value) {
        this.Value = value;
    }

    public void increaseValue(double amount) {
        this.Value += amount;
    }

    public void decreaseValue(double amount) {
        this.Value -= amount;
    }
}
