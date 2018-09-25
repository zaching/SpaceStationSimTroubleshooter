package spacestation.lifesupport;

import java.util.ArrayList;

public class Computer {
    //This is the brains of a life support system
    //Should be able to respond to different external conditions

    private final LifeSupportSubsystem ParentSystem;
    private final ArrayList<Sensor> cachedParentSensors;


    public Computer(LifeSupportSubsystem parentSystem) {
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
        //BUG: If there are ways to change variables but status isn't updated, then this could drop out prematurely
        //BUG: alternately, checkStatus has side effects that could make a fun bug
        if (checkStatus() == StatusCode.NOMINAL) {
            return;
        }
        for (Component c : ParentSystem.getComponentsWithProblems()) {
            double deviation = c.getDeviationFromDesired();
            //BUG: Flipping if the controls go up or down would make for an easy bug to find/fix
            if (deviation > 0) {
                c.decreaseControl(deviation);
            }
            if (deviation < 0) {
                c.increaseControl(deviation);
            }
            //BUG: I think we could have a 0 comparison problem with double rounding imprecision
            if (deviation == 0.0) {
                c.repairControls();
            }
        }
    }
}
