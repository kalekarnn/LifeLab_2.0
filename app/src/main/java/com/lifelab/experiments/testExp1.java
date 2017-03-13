package com.lifelab.experiments;

import com.lifelab.coreclass.Experiment;
import com.lifelab.coreclass.Parameter;
import com.lifelab.parameters.TestParameter1;
import com.lifelab.parameters.TestParameter2;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by E9949942 on 12/25/2016.
 */
public class testExp1 extends Experiment {

    public testExp1() {
        super(  "TestExp1",
                "For Testing",
                "Nothing",
                "Dont know",
                "NOt yett",
                "Will See",
                true,
                new ArrayList<Parameter>(){{
                    add(new TestParameter1());
                    add(new TestParameter2());}});
    }

    @Override
    public void startExperiment() {
        Date currentDate = new Date();
        this.settExpStartTime(currentDate);
        //start displayimg it on UI
    }

    @Override
    public void endExperiment() {
        Date currentDate = new Date();
        this.settExpEndTime(currentDate);
        //stop updating
    }
}
