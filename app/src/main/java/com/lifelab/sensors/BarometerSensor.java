package com.lifelab.sensors;

import android.util.Log;

import com.lifelab.coreclass.Sensor;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;

/***************************************************************************************************
 * @version 1.0 Created by E9949942 Narendra Kalekar on 3/13/2017.
 **************************************************************************************************/
public class BarometerSensor extends Sensor {

    public BarometerSensor(){
        super(  "BAROMETER_SENSOR",
                "f000aa40-0451-4000-b000-000000000000",
                "f000aa41-0451-4000-b000-000000000000",
                "f000aa42-0451-4000-b000-000000000000",
                "f000aa44-0451-4000-b000-000000000000",
                new byte[]{0x01}
        );
    }

    @Override
    public void convertAndSet(byte[] sensorValue) {
        double output;
        if (sensorValue.length > 4) {
            Integer val = twentyFourBitUnsignedAtOffset(sensorValue, 2);
            output = (double) val / 100.0;
        } else {
            int mantissa;
            int exponent;
            Integer sfloat = shortUnsignedAtOffset(sensorValue, 2);
            mantissa = sfloat & 0x0FFF;
            exponent = (sfloat >> 12) & 0xFF;
            double magnitude = pow(2.0f, exponent);
            output = (mantissa * magnitude) / 100.0f;
        }
        Log.i("Pressure ", "" + output/100);
        List<Double> lValue = new ArrayList<>();
        lValue.add(output / 100);
        this.setSensorValue(lValue);
    }

    private Integer twentyFourBitUnsignedAtOffset(byte[] c, int offset) {
        Integer lowerByte = (int) c[offset] & 0xFF;
        Integer mediumByte = (int) c[offset+1] & 0xFF;
        Integer upperByte = (int) c[offset + 2] & 0xFF;
        return (upperByte << 16) + (mediumByte << 8) + lowerByte;
    }
}
