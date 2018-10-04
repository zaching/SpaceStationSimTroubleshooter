package spacestation.lifesupport;

import java.util.HashSet;
import java.util.Set;

public class Environment {
    private final Set<Parameter> parameters = new HashSet<>();
    public final double HeatExchangeFactor;
    public final double InternalLatentHeatGenerationInDegreesK;
    public final double ExternalLatentHeatDissipationInDegreesK;

    public Environment(double heatExchangeFactor, double internalLatentHeatGenerationInDegreesK, double externalLatentHeatDissipationInDegreesK) {
        this.HeatExchangeFactor = heatExchangeFactor;
        this.InternalLatentHeatGenerationInDegreesK = internalLatentHeatGenerationInDegreesK;
        this.ExternalLatentHeatDissipationInDegreesK = externalLatentHeatDissipationInDegreesK;
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