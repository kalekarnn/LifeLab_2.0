package com.lifelab.sensors;

import android.util.Log;

import com.lifelab.coreclass.Sensor;

import java.util.ArrayList;
import java.util.List;

/***************************************************************************************************
 * @version 1.0 Created by E9949942 Narendra Kalekar on 3/13/2017.
 **************************************************************************************************/
public class GyroscopeSensor extends Sensor {
    private static GyroscopeSensor gyroscopeSensor;
    private GyroscopeSensor(){
        super (
                "GYROSCOPE_SENSOR",
                "f000aa80-0451-4000-b000-000000000000",
                "f000aa81-0451-4000-b000-000000000000",
                "f000aa82-0451-4000-b000-000000000000",
                "f000aa83-0451-4000-b000-000000000000",
                 new byte[]{(byte)0x07,(byte)0xff}
        );
    }

    //thread safe singleton implementation 
    public static GyroscopeSensor getInstance(){
    if(gyroscopeSensor == null){
        synchronized (GyroscopeSensor.class){
            if(gyroscopeSensor == null)
                gyroscopeSensor = new GyroscopeSensor();
        }
    }
    return gyroscopeSensor;
    }

    @Override
    public void convertAndSet(byte[] sensorValue) {

        double x = (sensorValue[1]<<8) + sensorValue[0];
        double y = (sensorValue[3]<<8) + sensorValue[2];
        double z = (sensorValue[5]<<8) + sensorValue[4];
        List<Double> lValue = new ArrayList<>();
        lValue.add(x/128.0);
        lValue.add(y/128.0);
        lValue.add(z/128.0);
        this.setSensorValue(lValue);
    }
}
