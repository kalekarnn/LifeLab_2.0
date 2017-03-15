package com.lifelab.parameters;

import com.lifelab.coreclass.Parameter;
import com.lifelab.coreclass.Sensor;
import com.lifelab.sensors.GyroscopeSensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***************************************************************************************************
 * @version 1.0 Created by E9949942 Narendra Kalekar on 3/13/2017.
 **************************************************************************************************/
public class AnemometerRPMParameter extends Parameter {
    public AnemometerRPMParameter(){
        super(  "Angular Velocity",
                "rad/s",
                "Formula_AngVel",
                new ArrayList<Sensor>(){{
                    add(new GyroscopeSensor());
                }}
        );
    }

    @Override
    public void calculateAndSet() {
        List<Sensor> listOfSensors = getListOfSensors();
        Map vel = listOfSensors.get(0).getSensorValue();
        this.setParameterValue(String.valueOf(vel));
    }
}
