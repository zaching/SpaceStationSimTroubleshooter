package spacestation.lifesupport;

import java.util.ArrayList;

public class Governor {
    //This is the brains of a life support system
    //Should be able to respond to different external conditions

    private final LifeSupportSubsystem ParentSystem;
    private final ArrayList<Sensor> cachedParentSensors;


    public Governor(LifeSupportSubsystem parentSystem) {
        this.ParentSystem = parentSystem;
        this.cachedParentSensors = parentSystem.getSensors();
    }

    public StatusCode checkStatus() {
        boolean problemFound = false;
        for (Sensor s: cachedParentSensors) {
            if (s.outsideDesired()) {
                ParentSystem.downgradeStatus(StatusCode.WARNING);
                problemFound = true;
            }
            if (s.outsideSafe()) {
                ParentSystem.downgradeStatus(StatusCode.CRITICAL);
                problemFound = true;
            }
        }
        if (!problemFound) {
            ParentSystem.upgradeStatus(StatusCode.NOMINAL);
        }
        return ParentSystem.getStatus();
    }

    public void updateModule() {
        if (checkStatus() == StatusCode.NOMINAL) {
            return;
        }
        for (Component c : ParentSystem.getComponentsWithProblems()) {
            double deviation = c.getDeviationFromDesired();
            if (deviation > 0) {
                //Left off here
            }
        }
    }
}
