import processing.core.PVector;


public abstract class RotationState {
	protected String name;
	
	abstract public void execute(Particle r);
	
	protected RotationState(String name) {
		this.name = name;
	}
	//
	public void enter(Particle r) { 
		System.out.printf("rotation entering state %s\n",name,r);
	}
	//
	public void exit(Particle r) { 
		System.out.printf("rotation exiting state %s\n",name,r);
	}	
	//	
	public String name() { return name;}
	
	public String toString() {
		return name();
	}
}

class GlobalRotationState extends RotationState {
    private static String myname = "GlobalRotationState";
    private static GlobalRotationState instance = null;
    
    private final Double delay = 200d;

    private GlobalRotationState() {
        super(myname);
    }

    public static GlobalRotationState getInstance() {
        if (instance == null) {
            instance = new GlobalRotationState();
        }
        return instance;
    }

    @Override
    public void execute(Particle r) {
        //detect user input to change directions
        // r.app.keyPressed == true && 
        if (r.app.keyPressed == true && System.currentTimeMillis() > (r.previousInput + delay)) {
            r.previousInput = System.currentTimeMillis();
            System.out.println("Key Pressed");
            if (r.sm.isInRotationState(ClockwiseRotationState.getInstance())) {
                r.sm.setCurrentRotationState(CClockwiseRotationState.getInstance());
            }
            else if (r.sm.isInRotationState(CClockwiseRotationState.getInstance())) {
                r.sm.setCurrentRotationState(ClockwiseRotationState.getInstance());
            } 
        }
    }

    @Override
	public void enter(Particle r) {}
	@Override
	public void exit(Particle r) {}	
}

class ClockwiseRotationState extends RotationState {
    private static String myname = "ClockwiseRotationState";
    private static ClockwiseRotationState instance = null;

    private ClockwiseRotationState() {
        super(myname);
    }

    public static ClockwiseRotationState getInstance() {
        if (instance == null) {
            instance = new ClockwiseRotationState();
        }
        return instance;
    }

    @Override
    public void execute(Particle r) {
        
    }

    @Override
    public void exit(Particle r) {
        r.vel = new PVector();
    }
}

// Counter clockwise rotation state
class CClockwiseRotationState extends RotationState {
    private static String myname = "CClockwiseRotationState";
    private static CClockwiseRotationState instance = null;

    private CClockwiseRotationState() {
        super(myname);
    }

    public static CClockwiseRotationState getInstance() {
        if (instance == null) {
            instance = new CClockwiseRotationState();
        }
        return instance;
    }

    @Override
    public void execute(Particle r) {
        r.changeHeading();
    }

    @Override
    public void exit(Particle r) {
        r.vel = new PVector();
    }
}