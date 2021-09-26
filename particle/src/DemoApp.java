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

    public void settings() { size(800,400); }
	
	public void setup() {
		ctx = this;
        particle = new Particle();
		
		//initial mouseArea is the upper left
		
		// create State Machine
		sm = new RegionStateMachine(this);
		sm.setGlobalState(GlobalRegionState.getInstance());
		sm.setCurrentState(UpperLeftAnimateState.getInstance());

        
	}
	
	public void draw() {
		background(255);
		sm.update();

	}
}

