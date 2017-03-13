package com.lifelab.parameters;

import com.lifelab.sensors.GyroscopeSensor;
import com.lifelab.coreclass.Parameter;
import com.lifelab.coreclass.Sensor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by E5254976 on 1/12/2017.
 */
public class AngularVelocityParameter extends Parameter {
    public AngularVelocityParameter(){
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
        double vel = listOfSensors.get(0).getSensorValue();
        this.setParameterValue(String.valueOf(vel));
    }
}
