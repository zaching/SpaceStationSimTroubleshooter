package spacestation.lifesupport;

import java.util.ArrayList;

public class Environment {
    private ArrayList<Parameter> parameters;
    public final double HeatExchangeFactor = 5.0;
    public final double InternalLatentHeatGenerationInDegreesK = 10.0;
    public final double ExternalLatentHeatDissipationInDegreesK = 5.0;

    public Environment() {
        parameters = new ArrayList<>();
    }

    public void add(Parameter p) {
        parameters.add(p);
    }

    public Parameter getParameter(String parameterName) {
        for (Parameter p : parameters) {
            if (p.getName().equals(parameterName)) {
                return p;
            }
        }
        return null;
    }

}
//Some extra comment that doesn't matter