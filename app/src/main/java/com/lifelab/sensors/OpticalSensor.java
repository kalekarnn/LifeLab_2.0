package com.lifelab.sensors;

import com.lifelab.coreclass.Sensor;

/**
 * Created by E9949942 on 12/25/2016.
 */
public class OpticalSensor extends Sensor {

    public OpticalSensor(){
        super(  "OPTICAL_SENSOR",
                "f000aa70-0451-4000-b000-000000000000",
                "f000aa71-0451-4000-b000-000000000000",
                "f000aa72-0451-4000-b000-000000000000",
                "f000aa73-0451-4000-b000-000000000000",
                new byte[]{0x01}
        );
    }

    @Override
    public void convertAndSet(byte[] sensorValue) {
        int mantissa;
        int exponent;
        Integer lowerByte = (int) sensorValue[0] & 0xFF;
        Integer upperByte = (int) sensorValue[0+1] & 0xFF; // // Interpret MSB as signed
        Integer sfloat = (upperByte << 8) + lowerByte;
        mantissa = sfloat & 0x0FFF;
        exponent = (sfloat >> 12) & 0xFF;
        double magnitude = Math.pow(2.0f, exponent);
        double output = (mantissa * magnitude);
        output = output / 100.0f;
        this.setSensorValue(output);
    }
}
