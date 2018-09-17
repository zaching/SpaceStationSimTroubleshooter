package spacestation.lifesupport;

public class Main {

    public static void main(String[] args) {
        //A simulation of a space station w/ Life Support
        //LifeSupportSubsystem needs to deal with whatever issues pop up, but has lots of breaks causing the crew to die
            //Events: Initialization, Steady State, Daybreak, Nightfall, New Arrival, Micropuncture
        //Life Support
        //Heating/Cooling, Electricity, Water, Food, Air
        //Food taken care of by a huge bin of freeze dried deliciousness
        //Water recirculation handled by the program
        //Air recirculation handled by the program
        //Cooling is hard, heat transfer is a bitch, but accomplished with radiative fans and an internal AC (AC passes heat to radiators)
        //Heating is easy, just need electricity
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

        //Create the starting environment
        Parameter externalTemp = new Parameter("External Temperature","Kelvin",200.0);
        Parameter internalTemp = new Parameter("Internal Temperature","Kelvin",295.0);
        Environment e = new Environment();
        e.add(externalTemp);
        e.add(internalTemp);

        //Add a bunch of sensors and controls for heat control
        Sensor externalThermometer = new Sensor("External Thermometer",externalTemp,200,500); //-100 degF to 440 degF
        Sensor internalThermometer = new Sensor("Internal Thermometer",internalTemp,200,500); //-100 degF to 440 degF
        Control extendRadiator = new Control("Extend Radiator",1.0);
        Control retractRadiator = new Control("Retract Radiator",-1.0);
        Control increaseAC = new Control("Increase Air Conditioning",1.0);
        Control decreaseAC = new Control("Decrease Air Conditioning",-1.0);
        Control increaseHeater = new Control("Increase Heater",1.0);
        Control decreaseHeater = new Control("Decrease Heater",-1.0);

        /*Start adding components*/

        Component heatRadiator = new Component("Heat Radiator","Radiator Setting",StatusCode.NOMINAL,0.0,100.0);
        heatRadiator.add(externalThermometer);
        heatRadiator.add(extendRadiator);
        heatRadiator.add(retractRadiator);

        Component airConditioner = new Component("Air Conditioner","AC Setting",StatusCode.NOMINAL,0.0,100.0);
        airConditioner.add(internalThermometer);
        airConditioner.add(externalThermometer);
        airConditioner.add(increaseAC);
        airConditioner.add(decreaseAC);

        Component heater = new Component("Heater","Heater Setting",StatusCode.NOMINAL,0.0,100.0);
        heater.add(internalThermometer);
        heater.add(increaseHeater);
        heater.add(decreaseHeater);

        LifeSupportSubsystem temperatureControl = new LifeSupportSubsystem("Temperature Control",StatusCode.NOMINAL);
        temperatureControl.add(heatRadiator);
        temperatureControl.add(airConditioner);
        temperatureControl.add(heater);

        System.out.println(temperatureControl.check());

        airConditioner.incrementControl(increaseAC,10);

        System.out.println(temperatureControl.check());

    }
}
