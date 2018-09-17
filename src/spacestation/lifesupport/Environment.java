package spacestation.lifesupport;

import java.util.ArrayList;

public class Environment {
    private ArrayList<Parameter> parameters;

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
