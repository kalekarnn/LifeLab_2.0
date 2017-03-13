package com.lifelab.parameters;

import com.lifelab.sensors.HumiditySensor;
import com.lifelab.sensors.OpticalSensor;
import com.lifelab.coreclass.Parameter;
import com.lifelab.coreclass.Sensor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by E9949942 on 12/25/2016.
 */
public class TestParameter2 extends Parameter {

    public TestParameter2(){
        super(  "Parameter_2",
                "Unit_2",
                "Formula_div",
                new ArrayList<Sensor>(){{
                    add(new HumiditySensor());
                    add(new OpticalSensor());
                }}
        );
    }

    @Override
    public void calculateAndSet() {
        List<Sensor> listOfSensors = getListOfSensors();
        double d1 = listOfSensors.get(0).getSensorValue();
        double d2 = listOfSensors.get(1).getSensorValue();
        double res = d1 - d2;
        setParameterValue(String.valueOf(res));
      //  Log.i("in TP2 ", String.valueOf(res));
    }
}
