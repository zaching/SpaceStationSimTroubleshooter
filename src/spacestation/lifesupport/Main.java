package spacestation.lifesupport;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Main {

    private static final Set<LifeSupportSubsystem> systems = new HashSet<>();
    private static final Environment e = new Environment(5,10,5);
    private static final Set<Component> allComponents = new HashSet<>();

    public static void main(String[] args) {
        //A simulation of a space station w/ Life Support
        //LifeSupportSubsystem needs to deal with whatever issues pop up, but has lots of breaks causing the crew to die
        //Events: Initialization, Steady State, Daybreak, Nightfall, New Arrival, Micropuncture
        initialize();
        int numRounds = 100;
        for (int i = 0; i < numRounds; i++) {
            LifeSupportSubsystem sys = systems.iterator().next();
            System.out.println("Round " + i + ":");
            breakComponents(i);
            System.out.println(sys.getQuickSummary());
            updateCycle();
            if (sys.getStatus() == StatusCode.CRITICAL) {
                System.out.println("\n***Critical failure at the end of round " + i + "; you lose!***\n");
                System.out.println("Final results:\n" + sys.getQuickSummary());
                i = numRounds;
            }
        }
    }
    private static void initialize() {
        //Create the starting environment
        Parameter externalTemp = new Parameter("External Temperature","Kelvin",250.0);
        Parameter internalTemp = new Parameter("Internal Temperature","Kelvin",295.0);
        e.add(externalTemp);
        e.add(internalTemp);

        ParameterLimit externalTempLimit = new ParameterLimit(externalTemp,300,200,400,200);
        ParameterLimit internalTempLimit = new ParameterLimit(internalTemp,300,288,310,275);

        /*****Temperature Control*****/
        //Heating is easy, just need electricity
        //Cooling is hard, heat transfer is a bitch because we can't take advantage of convection or conduction.
        //It is accomplished by an internal AC passing heat to the outside of the ship where external radiative fans dissipate it.


        //First, lets create a bunch of sensors and controls for heat regulation
        Sensor externalThermometer = new Sensor("External Thermometer",externalTempLimit,200,500); //-100 degF to 440 degF
        Sensor internalThermometer = new Sensor("Internal Thermometer",internalTempLimit,200,500); //-100 degF to 440 degF
        Control extendRadiator = new Control("Extend Radiator",10,1);
        Control retractRadiator = new Control("Retract Radiator",5,-1);
        Control increaseAC = new Control("Increase Air Conditioning",5,1);
        Control decreaseAC = new Control("Decrease Air Conditioning",20,-1);
        Control increaseHeater = new Control("Increase Heater",5,1);
        Control decreaseHeater = new Control("Decrease Heater",100,-1);

        //Then group them into components
        Component heatRadiator = new Component("Heat Radiator","Radiator Setting",0.0,100.0,-1,0,extendRadiator,retractRadiator,externalThermometer,null);
        allComponents.add(heatRadiator);
        Component airConditioner = new Component("Air Conditioner","AC Setting",0.0,100.0,-2,2,increaseAC,decreaseAC,internalThermometer,externalThermometer);
        allComponents.add(airConditioner);
        Component heater = new Component("Heater","Heater Setting",0.0,100.0,2,1,increaseHeater,decreaseHeater,internalThermometer,externalThermometer);
        allComponents.add(heater);

        //Then group those into a life support system
        LifeSupportSubsystem temperatureControl = new LifeSupportSubsystem("Temperature Control");
        temperatureControl.add(heater);
        temperatureControl.add(airConditioner);
        temperatureControl.add(heatRadiator);

        //Then add some basic "AI" to govern the system
        Computer lifeSupportGovernor = new Computer(temperatureControl);
        temperatureControl.add(lifeSupportGovernor);

        systems.add(temperatureControl);
    }

    private static void updateCycle() {
        //Assumes any events have already happened
        //Update the governors for each life support system
        Parameter internalTemp = e.getParameter("Internal Temperature");
        Parameter externalTemp = e.getParameter("External Temperature");

        //Generate "side effect" internal heat from various systems that aren't explicitly part of temperature control
        //Basically, everything electronic generates *some* heat
        //System.out.println("Internal temperature before side-effect changes: " + internalTemp.getValue());
        //System.out.println("External temperature before side-effect changes: " + externalTemp.getValue());
        internalTemp.increaseValue(e.InternalLatentHeatGenerationInDegreesK);

        //Do a heat exchange between inside and outside
        double heatExchange = (internalTemp.getValue() - externalTemp.getValue())/e.HeatExchangeFactor;
        internalTemp.decreaseValue(heatExchange);
        externalTemp.increaseValue(heatExchange);

        //Also some natural radiation of heat to the background level of 0K
        externalTemp.decreaseValue(e.ExternalLatentHeatDissipationInDegreesK);
        //System.out.println("Internal temperature after side-effect changes: " + internalTemp.getValue());
        //System.out.println("External temperature after side-effect changes: " + externalTemp.getValue());




        //Update control settings with governor
        //System.out.println("Internal temperature before governor updates: " + internalTemp.getValue());
        //System.out.println("External temperature before governor updates: " + externalTemp.getValue());
        for (LifeSupportSubsystem l : systems) {
            l.updateModule();
        }
        //Execute changes to the environment from setting changes
          //For each component, pull the parameter of the primary sensor and change it by the setting * impact then do the same with the secondary
        for (Component c : allComponents) {
            double amountToChangePrimaryParameter = c.getCurrentSetting()* c.SettingPrimaryImpact;
            c.getPrimarySensor().getParameterLimit().getParameter().increaseValue(amountToChangePrimaryParameter);
            //BUG: Secondary sensor is null for many components, could be an easy bug to have an NPE if I remove this sitRep
            if (c.getSecondarySensor() != null) {
                double amountToChangeSecondaryParameter = c.getCurrentSetting() * c.SettingSecondaryImpact;
                c.getSecondarySensor().getParameterLimit().getParameter().increaseValue(amountToChangeSecondaryParameter);
            }
        }
        //System.out.println("Internal temperature after settings take effect: " + internalTemp.getValue());
        //System.out.println("External temperature after settings take effect: " + externalTemp.getValue());
    }

    public static void breakComponent(int currentRound, int targetRound, String componentName, boolean breakIncreaseController, int roundsBroken) {
        if (currentRound == targetRound) {
            Iterator<Component> it = allComponents.iterator();
            while (it.hasNext()) {
                Component c = it.next();
                if (c.getName().equals(componentName)) {
                    if (breakIncreaseController) {
                        c.increaseControlMalfunction(roundsBroken);
                        System.out.println("Increase controller for " + c.getName() + " has been broken, it will take " + roundsBroken + " rounds to repair.");
                    } else {
                        c.decreaseControlMalfunction(roundsBroken);
                        System.out.println("Decrease controller for " + c.getName() + " has been broken, it will take " + roundsBroken + " rounds to repair.");
                    }
                }
            }
        }
    }

    public static void breakComponents(int currentRound) {
        breakComponent(currentRound,40,"Heat Radiator",true,10);
    }
}
