package spacestation.lifesupport;

import java.util.ArrayList;

public class Main {

    private static final ArrayList<LifeSupportSubsystem> systems = new ArrayList<>();
    private static final Environment e = new Environment();
    private static final ArrayList<Component> allComponents = new ArrayList<>();

    public static void main(String[] args) {
        //A simulation of a space station w/ Life Support
        //LifeSupportSubsystem needs to deal with whatever issues pop up, but has lots of breaks causing the crew to die
        //Events: Initialization, Steady State, Daybreak, Nightfall, New Arrival, Micropuncture
        initialize();
        updateCycle();
        System.out.println(systems.get(0).check());


        //Life Support
        //Heating/Cooling, Electricity, Water, Food, Air
        //Food taken care of by a huge bin of freeze dried deliciousness
        //Water recirculation handled by the program
        //Air recirculation handled by the program
        /*
        checkTemp();
            checkExternalTemp();
            checkInternalTemp();
        checkAir();
            checkO2Level();
            checkCO2Level();
            checkCO2Scrubber();
        checkWaterReserves();
            checkWaterPurifier();
            testWaterPurifier();
            checkGrayWaterReserves();
        checkFoodReserves();
        checkElectricity();
            checkBatterLevels();
            checkGeneratorOutput();
            testGenerator();
        */
    }
    private static void initialize() {
        //Create the starting environment


        //Temperature
        //Heating is easy, just need electricity
        //Cooling is hard, heat transfer is a bitch, but accomplished with radiative fans and an internal AC (AC passes heat to radiators)
        Parameter externalTemp = new Parameter("External Temperature","Kelvin",200.0);
        Parameter internalTemp = new Parameter("Internal Temperature","Kelvin",295.0);
        e.add(externalTemp);
        e.add(internalTemp);

        ParameterLimit externalTempLimit = new ParameterLimit(externalTemp,300,225,350,200);
        ParameterLimit internalTempLimit = new ParameterLimit(externalTemp,300,288,310,275);

        //Add a bunch of sensors and controls for heat regulation
        Sensor externalThermometer = new Sensor("External Thermometer",externalTempLimit,200,500); //-100 degF to 440 degF
        Sensor internalThermometer = new Sensor("Internal Thermometer",internalTempLimit,200,500); //-100 degF to 440 degF
        Control extendRadiator = new Control("Extend Radiator",10,1);
        Control retractRadiator = new Control("Retract Radiator",5,-1);
        Control increaseAC = new Control("Increase Air Conditioning",5,1);
        Control decreaseAC = new Control("Decrease Air Conditioning",20,-1);
        Control increaseHeater = new Control("Increase Heater",5,1);
        Control decreaseHeater = new Control("Decrease Heater",100,-1);

        /*Start adding components*/

        Component heatRadiator = new Component("Heat Radiator","Radiator Setting",StatusCode.NOMINAL,0.0,100.0,1,0,extendRadiator,retractRadiator,externalThermometer,null);
        allComponents.add(heatRadiator);
        Component airConditioner = new Component("Air Conditioner","AC Setting",StatusCode.NOMINAL,0.0,100.0,2,-2,increaseAC,decreaseAC,internalThermometer,externalThermometer);
        allComponents.add(airConditioner);
        Component heater = new Component("Heater","Heater Setting",StatusCode.NOMINAL,0.0,100.0,2,1,increaseHeater,decreaseHeater,internalThermometer,externalThermometer);
        allComponents.add(heater);

        LifeSupportSubsystem temperatureControl = new LifeSupportSubsystem("Temperature Control",StatusCode.NOMINAL);
        temperatureControl.add(heatRadiator);
        temperatureControl.add(airConditioner);
        temperatureControl.add(heater);

        systems.add(temperatureControl);
    }

    private static void updateCycle() {
        //Assumes any events have already happened
        //Update the governors for each life support system
        for (LifeSupportSubsystem l : systems) {
            l.updateModule();
        }
        //Execute changes to the environment from setting changes
          //For each component, pull the parameter of the primary sensor and change it by the setting * impact then do the same with the secondary
        for (Component c : allComponents()) {
            double amountToChangePrimaryParameter = c.getSetting()* c.SettingPrimaryImpact;
            c.getPrimarySensor().getParameterLimit().getParameter().increaseValue(amountToChangePrimaryParameter);
            //BUG: Secondary sensor is null for many components, could be an easy bug to have an NPE if I remove this check
            if (c.getSecondarySensor() != null) {
                double amountToChangeSecondaryParameter = c.getSetting() * c.SettingSecondaryImpact;
                c.getSecondarySensor().getParameterLimit().getParameter().increaseValue(amountToChangeSecondaryParameter);
            }
        }
    }
}
