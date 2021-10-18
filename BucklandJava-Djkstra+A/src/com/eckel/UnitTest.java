package com.eckel;
//import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class UnitTest {
	static String testID;
	static Set errors = new HashSet();
	static Set passed = new HashSet();
	// Override cleanup() if test object
	// creation allocates non-memory
	// resources that must be cleaned up:
	protected void cleanup() {}
	// Verify the truth of a condition:
	// assert is keyword 
	protected final void assertTrue(boolean condition) {
		if(!condition)
			errors.add("** failed: " + testID);
		else
			passed.add("passed: " + testID);
	}
}
