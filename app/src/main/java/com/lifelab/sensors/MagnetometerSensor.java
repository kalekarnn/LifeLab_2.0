package com.lifelab.sensors;

import android.util.Log;

import com.lifelab.coreclass.Sensor;

import java.util.ArrayList;
import java.util.List;

/***************************************************************************************************
 * @version 1.0 Created by E9949942 Narendra Kalekar on 3/13/2017.
 **************************************************************************************************/
public class MagnetometerSensor extends Sensor {
    public MagnetometerSensor(){
        super (
                "ACCELEROMETER_SENSOR",
                "F000AA80-0451-4000-B000-000000000000",
                "F000AA81-0451-4000-B000-000000000000",
                "F000AA82-0451-4000-B000-000000000000",
                "F000AA83-0451-4000-B000-000000000000",
                 new byte[]{(byte)0x40,(byte)0xff}
        );
    }

    @Override
    public void convertAndSet(byte[] sensorValue) {
        if(sensorValue.length >= 18) {
            double x = (sensorValue[13] << 8) + sensorValue[12];
            double y = (sensorValue[15] << 8) + sensorValue[14];
            double z = (sensorValue[17] << 8) + sensorValue[16];
            Log.i("P1", "x " + x / (32768 / 4912));
            Log.i("P1", "y " + y / (32768 / 4912));
            Log.i("P1", "z " + z / (32768 / 4912));
            List<Double> lValue = new ArrayList<>();
            lValue.add(x/(32768 / 4912));
            lValue.add(y/(32768 / 4912));
            lValue.add(z/(32768 / 4912));
            this.setSensorValue(lValue);
        }else {
            List<Double> lValue = new ArrayList<>();
            lValue.add(0.0);
            lValue.add(0.0);
            lValue.add(0.0);
            Log.i("P1", "x 0" );
            Log.i("P1", "y 0" );
            Log.i("P1", "z 0" );
            this.setSensorValue(lValue);
        }

    }
}
