import processing.core.PVector;


public abstract class RotationState {
	protected String name;
	
	abstract public void execute(DemoApp r);
	
	protected RotationState(String name) {
		this.name = name;
	}
	//
	public void enter(DemoApp r) { 
		System.out.printf("rotation entering state %s\n",name,r);
	}
	//
	public void exit(DemoApp r) { 
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
    
    private Long previousInput = 0L;
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
    public void execute(DemoApp r) {
        //detect user input to change directions
        
        if (r.keyPressed == true && System.currentTimeMillis() > (previousInput + delay)) {
            previousInput = System.currentTimeMillis();
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
	public void enter(DemoApp r) {}
	@Override
	public void exit(DemoApp r) {}	
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
    public void execute(DemoApp r) {
        
    }

    @Override
    public void exit(DemoApp r) {
        r.particle.vel = new PVector();
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
    public void execute(DemoApp r) {
        r.particle.changeHeading();
    }

    @Override
    public void exit(DemoApp r) {
        r.particle.vel = new PVector();
    }
}