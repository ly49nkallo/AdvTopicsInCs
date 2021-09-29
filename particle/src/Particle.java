import processing.core.PApplet;
import processing.core.PVector;
import java.util.Random;

public class Particle extends MoveableGameEntity{
    // protected DemoApp app;
    public static final float speed = 1.7f;
    public static final float FRICTION = 0.1f;
    public RegionStateMachine sm;
    // Option to allow particles to spawn anywhere
    private final boolean OnlySpawnInDefaultRegion = false;

    protected PApplet app = null;

    public static final Integer UPPERLEFT = 0;
	public static final Integer UPPERRIGHT = 1;
	public static final Integer LOWERLEFT = 2;
	public static final Integer LOWERRIGHT = 3;

    Region[] regions;
    Region currentRegion;

    Particle(DemoApp app) {
        this.app = app;
    }
    Random rand = new Random();

    public void setup() {
        

        float height = app.height;
        float width = app.width;

        regions = new Region[4];
		regions[UPPERLEFT] = new Region(0,height*0.5,0,width*0.5,UPPERLEFT);
		regions[UPPERRIGHT] = new Region(0,height*0.5,width*0.5,width,UPPERRIGHT);
		regions[LOWERLEFT] = new Region(height*0.5,height,0,width*0.5,LOWERLEFT);
		regions[LOWERRIGHT] = new Region(height*0.5, height, width*0.5, width, LOWERRIGHT);

        
        currentRegion = regions[0];
        if (OnlySpawnInDefaultRegion) {
            pos.x = rand.nextInt((int)currentRegion.right());
            pos.y = rand.nextInt((int)currentRegion.bottom());
        }
        else {
            pos.x = rand.nextInt((int)width);
            pos.y = rand.nextInt((int)height);
        }
            
		// create State Machine
		sm = new RegionStateMachine(this);
		sm.setGlobalState(GlobalRegionState.getInstance());
		sm.setCurrentState(UpperLeftAnimateState.getInstance());
        
        sm.setGlobalRotationState(GlobalRotationState.getInstance());
        sm.setCurrentRotationState(ClockwiseRotationState.getInstance());
    }
}
