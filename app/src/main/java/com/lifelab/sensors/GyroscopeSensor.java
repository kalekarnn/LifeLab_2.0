package com.lifelab.sensors;

import android.util.Log;

import com.lifelab.coreclass.Sensor;

import java.util.ArrayList;
import java.util.List;

/***************************************************************************************************
 * @version 1.0 Created by E9949942 Narendra Kalekar on 3/13/2017.
 **************************************************************************************************/
public class GyroscopeSensor extends Sensor {
    public GyroscopeSensor(){
        super (
                "GYROSCOPE_SENSOR",
                "F000AA80-0451-4000-B000-000000000000",
                "F000AA81-0451-4000-B000-000000000000",
                "F000AA82-0451-4000-B000-000000000000",
                "F000AA83-0451-4000-B000-000000000000",
                 new byte[]{(byte)0x07,(byte)0xff}
        );
    }

    @Override
    public void convertAndSet(byte[] sensorValue) {

        double x = (sensorValue[1]<<8) + sensorValue[0];
        double y = (sensorValue[3]<<8) + sensorValue[2];
        double z = (sensorValue[5]<<8) + sensorValue[4];
        Log.i("Gyro","x "+x/128.0);
        Log.i("Gyro","y "+y/128.0);
        Log.i("Gyro","z "+z/128.0);
        List<Double> lValue = new ArrayList<>();
        lValue.add(x/128.0);
        lValue.add(y/128.0);
        lValue.add(z/128.0);
        this.setSensorValue(lValue);

    }
}
