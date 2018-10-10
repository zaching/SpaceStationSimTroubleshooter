package spacestation.lifesupport;

public class Computer {
    //This is the brains of a life support system
    //Should be able to respond to different external conditions

    private final LifeSupportSystem ParentSystem;

    public Computer(LifeSupportSystem parentSystem) {
        this.ParentSystem = parentSystem;
    }

    public void updateModule() {
        for (Component c : ParentSystem.getComponents()) {
            double neededCorrection = c.willBeOutsideDesired() ? c.getPredictedDeviationFromOptimal()*c.getDirectionOfSetting() : 0;
            //System.out.println(c.getName() + "'s needed correction to " + c.getPrimarySensor().getName() + " is: " + (neededCorrection * -1));
            //BUG: Flipping if the controls go up or down would make for an easyish bug to find/fix
            if (neededCorrection > 0) {
                c.decreaseControl(Math.abs(neededCorrection));
            }
            if (neededCorrection < 0) {
                c.increaseControl(Math.abs(neededCorrection));
            }
            //BUG: I think we could have a 0 comparison problem with double rounding imprecision
            if (neededCorrection == 0.0) {
                c.dampenControls();
            }
            c.repairControls();
            //System.out.println(c.getName() + "'s setting is: " + c.getCurrentSetting());
        }
    }
}
