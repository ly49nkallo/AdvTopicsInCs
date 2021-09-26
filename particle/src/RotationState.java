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

    }

    @Override
    public void exit(DemoApp r) {

    }
}