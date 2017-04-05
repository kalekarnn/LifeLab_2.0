package com.lifelab.parameters;

import android.util.Log;

import com.lifelab.coreclass.Parameter;
import com.lifelab.coreclass.Sensor;
import com.lifelab.sensors.GyroscopeSensor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/***************************************************************************************************
 * @version 1.0 Created by E9949942 Narendra Kalekar on 3/13/2017.
 **************************************************************************************************/
public class AnemometerRPMParameter extends Parameter {
    public AnemometerRPMParameter(){
        super(  "RPM",
                "unit_RPM",
                "Formula_RPM",
                new ArrayList<Sensor>(){{
                    add(GyroscopeSensor.getInstance());
                }}
        );
    }

    @Override
    public void calculateAndSet() {
        List<Sensor> listOfSensors = getListOfSensors();
        Map mValue = listOfSensors.get(0).getSensorValue();
        Log.i("mVCalue",""+mValue);
        Set<Map.Entry<Long,List<Double>>> setValue = mValue.entrySet();
        Iterator itr = setValue.iterator();
        Map.Entry<Long,List<Double>> entry = null;
        while (itr.hasNext()){
            entry =  (Map.Entry)itr.next();
        }
        int rpm = (int)Math.abs(entry.getValue().get(2))/6;
        this.setParameterValue(String.valueOf(rpm));
        Log.i("RPM", "" + rpm);
    }
}
