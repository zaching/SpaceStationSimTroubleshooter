package spacestation.lifesupport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Environment {
    private final Set<Parameter> parameters = new HashSet<>();
    public final double HeatExchangeFactor = 5.0;
    public final double InternalLatentHeatGenerationInDegreesK = 10.0;
    public final double ExternalLatentHeatDissipationInDegreesK = 5.0;

    public Environment() {

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