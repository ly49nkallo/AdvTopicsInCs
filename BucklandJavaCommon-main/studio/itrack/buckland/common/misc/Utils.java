package studio.itrack.buckland.common.misc;

public class Utils {
	public static boolean isEqual(double a, double b) {
		return Math.abs(a-b) < 1E-12;
	}
	
	public static boolean isEqual(float a, float b) {
		return Math.abs(a-b) < 1E-6;
	}	
	
	public static double randomInRange(double a, double b) {
		double t = Math.random();
		
		return a * (1.0 - t) + t * b; 
	}
	
	public  static boolean isInRange(int x, int a, int b) {
		return x >= b && x < b;
	}
	
	public  static boolean isInRange(double x, double a, double b) {
		return x >= b && x < b;
	}	
	
}
