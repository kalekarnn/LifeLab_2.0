package com.lifelab.coreclass;

import java.io.Serializable;
import java.util.List;

/***************************************************************************************************
 * @version 1.0 Created by E9949942 Narendra Kalekar on 12/20/2016.
 **************************************************************************************************/
public abstract class Parameter implements Serializable{
    private String strParameterName;
    private String strParameterValue;
    private String strParameterUnit;
    private String strParameterFormula;
    private List<Sensor> listOfSensors;

    protected Parameter(String strParameterName,String strParameterUnit,String strParameterFormula,List<Sensor> listOfSensors){
        this.strParameterName = strParameterName;
        this.strParameterUnit = strParameterUnit;
        this.strParameterFormula = strParameterFormula;
        this.listOfSensors = listOfSensors;
        this.strParameterValue="0";
    }

    public String getParameterName() {
        return strParameterName;
    }

    public String getParameterUnit() {
        return strParameterUnit;
    }

    public String getParameterFormula() {
        return strParameterFormula;
    }

    public List<Sensor> getListOfSensors() {
        return listOfSensors;
    }

    public String getParameterValue() {
        return strParameterValue;
    }

    protected void setParameterValue(String strParameterValue) {
        this.strParameterValue = strParameterValue;
    }

    public String toString(){
        return "\nParameter Name "+getParameterName()+"\nParameter Value "+getParameterValue()+"\nRequired Sensors "+getListOfSensors();
    }
    /***********************************************************************************************
     * This method should read data from list of sensors, process/apply formula on it.
     * And should set the final value
     * of underlying parameter.
     **********************************************************************************************/
    public abstract void calculateAndSet();
}
