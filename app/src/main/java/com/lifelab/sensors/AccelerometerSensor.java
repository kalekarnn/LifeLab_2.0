package com.lifelab.sensors;

import android.util.Log;

import com.lifelab.coreclass.Sensor;

import java.util.ArrayList;
import java.util.List;

/***************************************************************************************************
 * @version 1.0 Created by E9949942 Narendra Kalekar on 3/13/2017.
 **************************************************************************************************/
public class AccelerometerSensor extends Sensor {
    public AccelerometerSensor(){
        super (
                "ACCELEROMETER_SENSOR",
                "F000AA80-0451-4000-B000-000000000000",
                "F000AA81-0451-4000-B000-000000000000",
                "F000AA82-0451-4000-B000-000000000000",
                "F000AA83-0451-4000-B000-000000000000",
                 new byte[]{(byte)0x38,(byte)0xff}
        );
    }

    @Override
    public void convertAndSet(byte[] sensorValue) {

        double x = (sensorValue[7]<<8) + sensorValue[6];
        double y = (sensorValue[9]<<8) + sensorValue[8];
        double z = (sensorValue[11]<<8) + sensorValue[10];
        Log.i("Acc","x "+x/4096.0*-1);
        Log.i("Acc","y "+y/4096.0);
        Log.i("Acc","z "+z/4096.0*-1);
        List<Double> lValue = new ArrayList<>();
        lValue.add(x/4096.0*-1);
        lValue.add(y/4096.0);
        lValue.add(z/4096.0*-1);
        this.setSensorValue(lValue);
    }
}
