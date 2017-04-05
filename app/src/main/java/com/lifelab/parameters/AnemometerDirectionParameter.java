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
                    add(GyroscopeSensor.getInstance());
                }}
        );
    }

    @Override
    public void calculateAndSet() {
        List<Sensor> listOfSensorsDir = getListOfSensors();
        Log.i("Sensors ", "" + listOfSensorsDir.get(0));
        Map mValueDir = listOfSensorsDir.get(0).getSensorValue();
        Set<Map.Entry<Long,List<Double>>> setValueDir = mValueDir.entrySet();
        Iterator itrDir = setValueDir.iterator();
        Map.Entry<Long,List<Double>> entryDir = null;
        while (itrDir.hasNext()){
           entryDir =  (Map.Entry)itrDir.next();
        }
        String direction = "";
        Log.i("Direction ", "" + entryDir.getValue().get(2));
        if(Math.signum(entryDir.getValue().get(2)) > 0)
            direction = "CLOCKWISE";
        else if(Math.signum(entryDir.getValue().get(2)) < 0)
            direction = "ANTI-CLOCKWISE";
        else
            direction = "NOT MOVING";
        Log.i("Direction by END", "" + direction);
        this.setParameterValue(direction);
        Log.i("Direction by END2", "" + direction);

    }
}
