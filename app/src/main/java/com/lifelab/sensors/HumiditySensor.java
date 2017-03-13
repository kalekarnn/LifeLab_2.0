package com.lifelab.sensors;

import android.util.Log;

import com.lifelab.coreclass.Sensor;

import java.util.ArrayList;
import java.util.List;

/***************************************************************************************************
 * @version 1.0 Created by E9949942 Narendra Kalekar on 3/13/2017.
 **************************************************************************************************/
public class HumiditySensor extends Sensor {

    public HumiditySensor(){
        super("HUMIDITY_SENSOR",
                "F000AA20-0451-4000-B000-000000000000",
                "F000AA21-0451-4000-B000-000000000000",
                "F000AA22-0451-4000-B000-000000000000",
                "F000AA23-0451-4000-B000-000000000000",
                new byte[]{0x01}
        );
    }

    @Override
    public void convertAndSet(byte[] sensorValue) {
        Integer lowerByte = (int) sensorValue[2] & 0xFF;
        Integer upperByte = (int) sensorValue[2+1] & 0xFF; // // Interpret MSB as signed
        int a  = (upperByte << 8) + lowerByte;
        a = a - (a % 4);
        double output = (-6f) + 125f * (a / 65535f);
        Log.i("Humidity ", "" + output);
        List<Double> lValue = new ArrayList<>();
        lValue.add(output);
        this.setSensorValue(lValue);
    }
}
