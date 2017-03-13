package com.lifelab.sensors;

import android.util.Log;

import com.lifelab.coreclass.Sensor;

/**
 * Created by E5254976 on 1/12/2017.
 * This
 */
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
        Log.i("P1","x "+x/128.0);
        Log.i("P1","y "+y/128.0);
        Log.i("P1","z "+z/128.0);

    }
}
