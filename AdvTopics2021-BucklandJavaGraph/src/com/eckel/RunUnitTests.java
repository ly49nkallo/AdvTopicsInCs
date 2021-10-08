package com.eckel;
import java.lang.reflect.*;
import java.util.Iterator;

public class RunUnitTests {
	
	public static void require(boolean requirement, String errmsg) {
		if(!requirement) {
			System.err.println(errmsg);
			System.exit(1);
		}
	
	}
	
	public static void main(String[] args) {
		// Checking the internal state of the runner..
		// System.out.println("I am running in: " + System.getProperty("user.dir"));
		
		require(args.length == 1, "Usage: RunUnitTests qualified-class");
		
		// number of tests run
		int testCount = 0;
		
		try {
			Class c = Class.forName(args[0]);
			// Only finds the inner classes
			// declared in the current class:
			Class[] classes = c.getDeclaredClasses();
			Class ut = null;
			for(int j = 0; j < classes.length; j++) {
				// Slip inner classes that are 
				// not derived from UnitTest:
				if(!UnitTest.class.isAssignableFrom(classes[j]))
					continue;
				ut = classes[j];
				break;  // Find the first test class only
			}
			// If it found an inner class,
			// that class must be static:
			if(ut != null)
				require(Modifier.isStatic(ut.getModifiers()),
						"inner UnitTest class must be static");
			// if it couldn't find the inner class,
			// maybe it's a regular class (for black-box testing:
			if(ut == null)
				if(UnitTest.class.isAssignableFrom(c))
					ut = c;
			require( ut != null, "No UnitTest class found");
			require( Modifier.isPublic(ut.getModifiers()),
					"UnitTest class must be public");
			Method[] methods = ut.getDeclaredMethods();
			
			for(int k=0; k< methods.length; k++) {
				Method m = methods[k];
				// Ignore overridden UnitTest methods:
				if(m.getName().contentEquals("cleanup"))
					continue;
				// Only public methods with no arguments
				// and void return types will be used as
				// test code:
				if(m.getParameterTypes().length == 0 &&
				   m.getReturnType() == void.class &&
				   Modifier.isPublic(m.getModifiers()) &&
				   m.getName().startsWith("test")) {
					testCount++;
					// The name of the test is 
					// used in error messages:
					UnitTest.testID = m.getName();
					System.out.println("...testing: " + UnitTest.testID + "...");
					// A new instance of the test object
					// is created and cleaned up for each test:
					Object test = ut.newInstance();
					m.invoke(test,  new Object[0]);
					((UnitTest)test).cleanup();
					
				}
			}
			
			// Exit if there are no tests
			require(testCount > 0, "No tests were run, You need to write test methods in the form of:\n\t 'public void test...()' ");		
			
		} catch(Exception e) {
			e.printStackTrace(System.err);
			// Any exception will return a nonzero
			// value to the console, so that
			// 'make' will abort:
			System.exit(0);
		}
		
		
		System.out.println("\n+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");
		System.out.println("                 RESULTS					");
		System.out.println("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");
		
		// After all tests in this class are run,
		// display passed results. 
		if(UnitTest.passed.size() != 0) {
			Iterator it = UnitTest.passed.iterator();
			while(it.hasNext())
				System.out.println(it.next());
		}
		
		
		// After all tests in this class are run,
		// display any results. If there were errors,
		// abort 'make' by returning a nonzero value.
		if(UnitTest.errors.size() != 0) {
			Iterator it = UnitTest.errors.iterator();
			while(it.hasNext())
				System.err.println(it.next());
			
			//System.exit(1);
		}
		
		System.out.println("==========================================");
		
		// Finally print out the summary of the test results
		if(UnitTest.errors.size() != 0)
			System.err.println(String.format("\n**ERRORS**\nRESULT:  %d of %d tests passed\n", UnitTest.passed.size(), testCount));
		else
			System.out.println("\nALL PASSED");
	}
} ///:~
