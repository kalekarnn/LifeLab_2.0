package com.lifelab.experiments;

import com.lifelab.coreclass.Experiment;
import com.lifelab.coreclass.Parameter;

import java.util.ArrayList;

/***************************************************************************************************
 * @version 1.0 Created by E9949942 Narendra Kalekar on 3/13/2017.
 **************************************************************************************************/
public class AnemometerExperiment extends Experiment {
    public AnemometerExperiment(){
        super(  "TestExp1",
                "For Testing",
                "Nothing",
                "Dont know",
                "NOt yett",
                "Will See",
                true,
                new ArrayList<Parameter>(){{
                    add(new AnemometerNoOfRotationsParameter());
                    }});
    }
    @Override
    public void startExperiment() {

    }

    @Override
    public void endExperiment() {

    }
}
