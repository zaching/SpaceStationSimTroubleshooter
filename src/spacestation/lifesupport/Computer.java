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



    public void updateModule() {
        for (Component c : ParentSystem.getComponentsWithProblems()) {
            double directionOfSetting = c.SettingPrimaryImpact/Math.abs(c.SettingPrimaryImpact);
            double deviation = c.getDeviationFromDesired()*directionOfSetting;
            System.out.println(c.getName() + "'s deviation from desired: " + deviation);
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
