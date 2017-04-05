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
public class AnemometerNoOfRotationsParameter extends Parameter {
    public AnemometerNoOfRotationsParameter(){
        super(  "No of Rotations",
                "unit_NOR",
                "Formula_NOR",
                new ArrayList<Sensor>(){{
                    add(GyroscopeSensor.getInstance());
                }}
        );
    }

    @Override
    public void calculateAndSet() {
        List<Sensor> listOfSensors = getListOfSensors();
        Map mValue = listOfSensors.get(0).getSensorValue();
        Log.i("mVCalue", "" + mValue);
        Set<Map.Entry<Long,List<Double>>> setValue = mValue.entrySet();
        Iterator itr = setValue.iterator();
        Map.Entry<Long,List<Double>> entry = null;
        double prevValue = 0.0,currValue;
        long prevKey = 0,currKey;
        boolean flag = false;
        double noOfRotations = 0.0;
        while (itr.hasNext()){
            if(!flag){
                entry =  (Map.Entry)itr.next();
               // prevValue = Math.signum(entry.getValue().get(2));
                prevKey = (long)Math.abs(entry.getKey());
                flag = true;
            }
            else{
                entry =  (Map.Entry)itr.next();
                currValue = Math.abs(entry.getValue().get(2));
                currKey = (long)Math.abs(entry.getKey());
                noOfRotations += currValue * (currKey - prevKey)/(360*1000);
                prevKey = currKey;
            }
        }
        this.setParameterValue(String.valueOf((int)(noOfRotations)));
    }
}
