package com.lifelab.sensors;

import android.util.Log;

import com.lifelab.coreclass.Sensor;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;

/***************************************************************************************************
 * @version 1.0 Created by E9949942 Narendra Kalekar on 3/13/2017.
 **************************************************************************************************/
public class IRTemperatureSensor extends Sensor {

    public IRTemperatureSensor(){
        super("IR_TEMPERATURE_SENSOR",
                "f000aa00-0451-4000-b000-000000000000",
                "f000aa01-0451-4000-b000-000000000000",
                "f000aa02-0451-4000-b000-000000000000",
                "f000aa03-0451-4000-b000-000000000000",
                new byte[]{0x01}
        );
    }

    @Override
    public void convertAndSet(byte[] sensorValue) {
        double ambient = extractAmbientTemperature(sensorValue);
        double target = extractTargetTemperature(sensorValue, ambient);
        double output = extractTargetTemperatureTMP007(sensorValue);
        Log.i("IR Temp ", "" + output);
        List<Double> lValue = new ArrayList<>();
        lValue.add(output);
        this.setSensorValue(lValue);
    }

    private double extractAmbientTemperature(byte [] v) {
        int offset = 2;
        return shortUnsignedAtOffset(v, offset) / 128.0;
    }

    private double extractTargetTemperature(byte [] v, double ambient) {
        Integer twoByteValue = shortSignedAtOffset(v, 0);
        double Vobj2 = twoByteValue.doubleValue();
        Vobj2 *= 0.00000015625;
        double Tdie = ambient + 273.15;
        double S0 = 5.593E-14; // Calibration factor
        double a1 = 1.75E-3;
        double a2 = -1.678E-5;
        double b0 = -2.94E-5;
        double b1 = -5.7E-7;
        double b2 = 4.63E-9;
        double c2 = 13.4;
        double Tref = 298.15;
        double S = S0 * (1 + a1 * (Tdie - Tref) + a2 * pow((Tdie - Tref), 2));
        double Vos = b0 + b1 * (Tdie - Tref) + b2 * pow((Tdie - Tref), 2);
        double fObj = (Vobj2 - Vos) + c2 * pow((Vobj2 - Vos), 2);
        double tObj = pow(pow(Tdie, 4) + (fObj / S), .25);
        return tObj - 273.15;
    }

    private double extractTargetTemperatureTMP007(byte [] v) {
        int offset = 0;
        return shortUnsignedAtOffset(v, offset) / 128.0;
    }
}
