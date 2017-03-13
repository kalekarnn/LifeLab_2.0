package com.lifelab.sensors;

import android.util.Log;

import com.lifelab.coreclass.Sensor;

import java.util.ArrayList;
import java.util.List;

/***************************************************************************************************
 * @version 1.0 Created by E9949942 Narendra Kalekar on 3/13/2017.
 **************************************************************************************************/
public class AmbientTemperatureSensor extends Sensor {

    public AmbientTemperatureSensor(){
        super("AMBIENT_TEMPERATURE_SENSOR",
                "f000aa00-0451-4000-b000-000000000000",
                "f000aa01-0451-4000-b000-000000000000",
                "f000aa02-0451-4000-b000-000000000000",
                "f000aa03-0451-4000-b000-000000000000",
                new byte[]{0x01}
        );
    }

    @Override
    public void convertAndSet(byte[] sensorValue) {
        double output = extractAmbientTemperature(sensorValue);
        Log.i("Ambient Temp", "" + output);
        List<Double> lValue = new ArrayList<>();
        lValue.add(output);
        this.setSensorValue(lValue);
    }

    private double extractAmbientTemperature(byte [] v) {
        int offset = 2;
        return shortUnsignedAtOffset(v, offset) / 128.0;
    }
}
