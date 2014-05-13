package com.umass.healthos.database.test;

import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;
import junit.framework.TestSuite;


public class AllTests extends TestSuite {

    public static Test suite() {
        TestSuite ret = new TestSuiteBuilder(AllTests.class).includeAllPackagesUnderHere().build();
      //  ret.addTest(new ControllerTest());
      //  ret.addTest(new ControllerTestDB());
      //  ret.addTest(new SMDTest());
      //  ret.addTest(new SMDTestDB());
        return ret;
    }

}