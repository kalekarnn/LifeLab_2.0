package com.lifelab.coreclass;

import android.util.Log;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/***************************************************************************************************
 * @version 1.0 Created by E9949942 Narendra Kalekar on 12/20/2016.
 **************************************************************************************************/
public abstract class Sensor implements Serializable{
    private String strSensorName;
    private UUID strUUID_SERV;
    private UUID strUUID_DATA;
    private UUID strUUID_CONF;
    private UUID strUUID_PERI;
    private Map<Long,List<Double>> sensorValue;
    private byte[] bitValue;


    protected Sensor(String strSensorName,String strUUID_SERV,String strUUID_DATA, String strUUID_CONF, String strUUID_PERI, byte[] bitValue){
        this.strSensorName = strSensorName;
        this.strUUID_SERV = UUID.fromString(strUUID_SERV);
        this.strUUID_DATA = UUID.fromString(strUUID_DATA);
        this.strUUID_CONF = UUID.fromString(strUUID_CONF);
        this.strUUID_PERI = UUID.fromString(strUUID_PERI);
        this.bitValue = bitValue;
        sensorValue = new LinkedHashMap<>();
    }

    public String getSensorName() {
        return strSensorName;
    }

    public UUID getUUID_SERV() {
        return strUUID_SERV;
    }

    public UUID getUUID_CONF() {
        return strUUID_CONF;
    }

    public UUID getUUID_DATA() {
        return strUUID_DATA;
    }

    public UUID getUUID_PERI() {
        return strUUID_PERI;
    }

    public Map<Long,List<Double>> getSensorValue() {
        return sensorValue;
    }

    public byte[] getBitValue() {
        return bitValue;
    }

    protected void setSensorValue(List<Double> listSensorValues) {
        long timeKey = Calendar.getInstance().getTimeInMillis();
        this.sensorValue.put(timeKey,listSensorValues);
    }

    public String toString(){
        return "\nSensor Name : "+getSensorName()+"\nSensor Service UUID "+getUUID_SERV();
    }
    public Integer shortUnsignedAtOffset(byte[] c, int offset) {
        Integer lowerByte = (int) c[offset] & 0xFF;
        Integer upperByte = (int) c[offset+1] & 0xFF; // // Interpret MSB as signed
        return (upperByte << 8) + lowerByte;
    }
    public Integer shortSignedAtOffset(byte[] c, int offset) {
        Integer lowerByte = (int) c[offset] & 0xFF;
        Integer upperByte = (int) c[offset+1]; // // Interpret MSB as signed
        return (upperByte << 8) + lowerByte;
    }
    /***********************************************************************************************
     * This method should read the sensor value on change, in the form of byte array.
     * Then it should convert the same into actual value using predefined conversion logic
     * and it should set this value to the field named strSensorValue.
     * @param sensorValue
     **********************************************************************************************/
    public abstract void convertAndSet(byte[] sensorValue);
}
