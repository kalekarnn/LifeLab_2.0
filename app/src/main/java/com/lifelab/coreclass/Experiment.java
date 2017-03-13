package com.lifelab.coreclass;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/***************************************************************************************************
 * @version 1.0 Created by E9949942 Narendra Kalekar on 12/20/2016.
 **************************************************************************************************/
public abstract class Experiment implements Serializable{
    private String strExpName;
    private String strExpBackground;
    private String strExpPrerequisite;
    private String strExpScientificPrinciple;
    private String strAssesmentQuestions;
    private String strRealLifeExamples;
    private Date tExpStartTime;
    private Date tExpEndTime;
    private boolean isBluetooth_LE_Required;
    private List<Parameter> listOfParameters;
    private static int counter;

    protected Experiment(String strExpName,String strExpBackground,String strExpPrerequisite,String strExpScientificPrinciple,String strAssesmentQuestions,String strRealLifeExamples,boolean isBluetooth_LE_Required,List<Parameter> listOfParameters){
        this.strExpName = strExpName;
        this.strExpBackground = strExpBackground;
        this.strExpPrerequisite = strExpPrerequisite;
        this.strExpScientificPrinciple = strExpScientificPrinciple;
        this.strAssesmentQuestions = strAssesmentQuestions;
        this.strRealLifeExamples = strRealLifeExamples;
        this.isBluetooth_LE_Required = isBluetooth_LE_Required;
        this.listOfParameters = listOfParameters;
        counter = 0;
    }

    public String getStrExpName() {
        return strExpName;
    }

    public String getStrExpBackground() {
        return strExpBackground;
    }

    public String getStrExpPrerequisite() {
        return strExpPrerequisite;
    }

    public String getStrExpScientificPrinciple() {
        return strExpScientificPrinciple;
    }

    public String getStrAssesmentQuestions() {
        return strAssesmentQuestions;
    }

    public String getStrRealLifeExamples() {
        return strRealLifeExamples;
    }

    protected void settExpStartTime(Date tExpStartTime) {
        this.tExpStartTime = tExpStartTime;
    }

    public Date gettExpStartTime() {
        return tExpStartTime;
    }

    protected void settExpEndTime(Date tExpEndTime) {
        this.tExpEndTime = tExpEndTime;
    }

    public Date gettExpEndTime() {
        return tExpEndTime;
    }

    public boolean isBluetooth_LE_Required() {
        return isBluetooth_LE_Required;
    }

    public List<Parameter> getListOfParameters() {
        return listOfParameters;
    }

    public Parameter getNextParameter(){
        counter++;
        if(counter == listOfParameters.size()){
            counter = 0;
        }
        return listOfParameters.get(counter);
    }

    public Parameter getPrevParameter(){
        counter--;
        if(counter < 0){
            counter = listOfParameters.size()-1;
        }
        return listOfParameters.get(counter);
    }

    public String toString(){
        return "\nExperiment Name "+getStrExpName()+"\nNeeded Parameters "+getListOfParameters();
    }
    public abstract void startExperiment();
    public abstract void endExperiment();
}
