package com.lifelab.parameters;

import android.util.Log;

import com.lifelab.coreclass.Parameter;
import com.lifelab.coreclass.Sensor;
import com.lifelab.sensors.AmbientTemperatureSensor;
import com.lifelab.sensors.BarometerSensor;
import com.lifelab.sensors.GyroscopeSensor;
import com.lifelab.sensors.HumiditySensor;
import com.lifelab.sensors.IRTemperatureSensor;
import com.lifelab.sensors.MagnetometerSensor;
import com.lifelab.sensors.OpticalSensor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/***************************************************************************************************
 * @version 1.0 Created by E9949942 Narendra Kalekar on 3/13/2017.
 **************************************************************************************************/
public class AnemometerDirectionParameter extends Parameter {
    public AnemometerDirectionParameter(){
        super(  "Direction",
                "NA",
                "Formula",
                new ArrayList<Sensor>(){{
                    add(new GyroscopeSensor());
                }}
        );
    }

    @Override
    public void calculateAndSet() {
        List<Sensor> listOfSensors = getListOfSensors();
        //Log.i("Sensors ", "" + listOfSensors.get(0));
        Map mValue = listOfSensors.get(0).getSensorValue();
        Set<Map.Entry<Long,List<Double>>> setValue = mValue.entrySet();
        Iterator itr = setValue.iterator();
        Map.Entry<Long,List<Double>> entry = null;
        while (itr.hasNext()){
           entry =  (Map.Entry)itr.next();
        }
        String direction = "";
        Log.i("Direction ", "" + entry.getValue().get(2));
        if(Math.signum(entry.getValue().get(0)) > 0)
            direction = "CLOCKWISE";
        else if(Math.signum(entry.getValue().get(0)) < 0)
            direction = "ANTI-CLOCKWISE";
        else
            direction = "NOT MOVING";
        this.setParameterValue(direction);
    }
}
