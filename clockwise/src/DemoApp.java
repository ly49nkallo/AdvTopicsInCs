import processing.core.PApplet;
import processing.core.PVector;
import java.util.HashMap;

public class DemoApp extends PApplet {
	
	// Static Attributes
	public static final Integer UPPERLEFT = 0;
	public static final Integer UPPERRIGHT = 1;
	public static final Integer LOWERLEFT = 2;
	public static final Integer LOWERRIGHT = 3;
	
	public static final Integer CLOCKWISE = 0;
	public static final Integer COUNTERCLOCKWISE = 1;

	// have the demoapp handle the extremely simple state machine
	public Integer currentRotation = CLOCKWISE;

	public static PApplet ctx;
	
	// Attributes
	MouseArea[] mArea;
	RegionStateMachine sm;
	MouseArea hoverMouseArea;

	public static particle p;
	
	public static void main(String[] args) {
		PApplet.main(DemoApp.class);	
	}
	
	public void settings() { size(800,400); }
	
	public void setup() {
		ctx = this;
		
		//initialize areas
		mArea = new MouseArea[4];
		mArea[UPPERLEFT] = new MouseArea(0,height*0.5,0,width*0.5,color(0),color(255),UPPERLEFT);
		mArea[UPPERRIGHT] = new MouseArea(0,height*0.5,width*0.5,width,color(255),color(0),UPPERRIGHT);
		mArea[LOWERLEFT] = new MouseArea(height*0.5,height,0,width*0.5,color(200,0,0), color(0,200,0),LOWERLEFT);
		mArea[LOWERRIGHT] = new MouseArea(height*0.5, height, width*0.5, width, color(0,200,0), color(200,0,0),LOWERRIGHT);
		
		//initial mouseArea is the upper left
		hoverMouseArea = mArea[0];
		p = new particle();
		
		// create State Machine
		sm = new RegionStateMachine(this);
		sm.setGlobalState(GlobalRegionState.getInstance());
		sm.setCurrentState(UpperLeftAnimateState.getInstance());

	}
	
	public void draw() {
		background(255);
		sm.update();

	}
	
	public void mouseClicked() {
		PVector f = new PVector(10,3);
//		vehicle.applyForce(f);
	}

	@Override
	public void keyPressed() {
		if (currentRotation == CLOCKWISE) currentRotation = COUNTERCLOCKWISE;
		else currentRotation = CLOCKWISE;
	}
	
	Region currentState() {
		return null;
	}
	
	public static void printf(String formatString, Object ... args ) {
		System.out.printf(formatString, args);
	}
}
