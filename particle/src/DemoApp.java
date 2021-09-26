import processing.core.PApplet;
import processing.core.PVector;

public class DemoApp extends PApplet {
    
    public static final Integer UPPERLEFT = 0;
	public static final Integer UPPERRIGHT = 1;
	public static final Integer LOWERLEFT = 2;
	public static final Integer LOWERRIGHT = 3;

    public static void main(String[] args) {
        PApplet.main(DemoApp.class);
    }

    public static PApplet ctx;
    RegionStateMachine sm;
    public Particle particle;
    Region[] regions;
    Region currentRegion;

    public void settings() { size(800,400); }
	
	public void setup() {
		ctx = this;
        particle = new Particle();
		
		//initial mouseArea is the upper left
        regions = new Region[4];
		regions[UPPERLEFT] = new Region(0,height*0.5,0,width*0.5,UPPERLEFT);
		regions[UPPERRIGHT] = new Region(0,height*0.5,width*0.5,width,UPPERRIGHT);
		regions[LOWERLEFT] = new Region(height*0.5,height,0,width*0.5,LOWERLEFT);
		regions[LOWERRIGHT] = new Region(height*0.5, height, width*0.5, width, LOWERRIGHT);

        currentRegion = regions[0];
        
		// create State Machine
		sm = new RegionStateMachine(this);
		sm.setGlobalState(GlobalRegionState.getInstance());
		sm.setCurrentState(UpperLeftAnimateState.getInstance());
        
        sm.setGlobalRotationState(GlobalRotationState.getInstance());
        sm.setCurrentRotationState(ClockwiseRotationState.getInstance());
	}
	
	public void draw() {
		background(255);
		sm.update();

        particle.update();
        particle.draw();
	}
}

