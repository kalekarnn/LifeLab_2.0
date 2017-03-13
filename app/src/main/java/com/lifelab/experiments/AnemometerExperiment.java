package com.lifelab.experiments;

import com.lifelab.parameters.AngularVelocityParameter;
import com.lifelab.coreclass.Experiment;
import com.lifelab.coreclass.Parameter;

import java.util.ArrayList;

/**
 * Created by E5254976 on 1/12/2017.
 */
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
                    add(new AngularVelocityParameter());
                    }});
    }
    @Override
    public void startExperiment() {

    }

    @Override
    public void endExperiment() {

    }
}
