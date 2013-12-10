package org.treytomes.util.testing;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTest(FileIOTests.suite());
		//$JUnit-END$
		return suite;
	}
}
