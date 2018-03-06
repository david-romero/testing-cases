package com.davromalc.testing;

import org.junit.runner.RunWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;



@RunWith(JUnitPlatform.class)
@IncludeEngines("junit-jupiter")
@SelectPackages("com.davromalc.testing")
public class TestRunner {

}
