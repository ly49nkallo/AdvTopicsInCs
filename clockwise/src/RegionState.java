import processing.core.PVector;

public abstract class RegionState {
	protected String name;
	
	abstract public void execute(DemoApp r);
	
	protected RegionState(String name) {
		this.name = name;
	}
	
	//
	public void enter(DemoApp r) { 
		System.out.printf("entering state %s\n",name,r);
	}
	
	//
	public void exit(DemoApp r) { 
		System.out.printf("exiting state %s\n",name,r);
	}	
	
	//	
	public String name() { return name;}
	
	public String toString() {
		return name();
	}
}

/*
 * In the global state we always check to see if the state needs to be changed based on where the mouse is
 */
class GlobalRegionState extends RegionState {
	private static String myName = "GlobalRegionState";
	private static GlobalRegionState instance = null;
	
	private GlobalRegionState() {
		super(myName);
	}
	
	public static GlobalRegionState getInstance() {
		if(instance == null)
			instance = new GlobalRegionState();
		return instance;	
	}
	
	@Override
	public void execute(DemoApp r) {
		int mX = r.mouseX;
		int mY = r.mouseY;
		MouseArea currentArea = r.hoverMouseArea;
		
		// we shouldn't need to do anything because we are 
		// in the same area
		if(currentArea.contains(mX, mY)) {
			return;
		} else {			
			for(MouseArea ma : r.mArea) {
				if(ma.contains(mX, mY)) {
					r.hoverMouseArea = ma;
					
					if(ma.id() == DemoApp.UPPERLEFT) {
						r.sm.changeState(UpperLeftAnimateState.getInstance());
					} else if(ma.id() == DemoApp.UPPERRIGHT) {
						r.sm.changeState(UpperRightAnimateState.getInstance());
					} else if(ma.id() == DemoApp.LOWERLEFT) {
						r.sm.changeState(LowerLeftAnimateState.getInstance());
					} else if(ma.id() == DemoApp.LOWERRIGHT) {
						r.sm.changeState(LowerRightAnimateState.getInstance());
					}
					
					break;
				}
			}
		}
	}
	
	// Override enter and exit so we don't print the default messages
	@Override
	public void enter(DemoApp r) {}
	@Override
	public void exit(DemoApp r) {}	
}


class UpperLeftAnimateState extends RegionState {
	private static String myName = "UpperLeftAnimateState";
	private static UpperLeftAnimateState instance = null;
	
	
	private UpperLeftAnimateState() {
		super(myName);
	}
	
	public static UpperLeftAnimateState getInstance() {
		if(instance == null)
			instance = new UpperLeftAnimateState();
		return instance;	
	}
	
	@Override
	public void execute(DemoApp r) {
		for(MouseArea ma : r.mArea) {
			if(ma.id() != DemoApp.UPPERLEFT) {
				ma.draw();
			} else {
				ma.update();
				ma.draw();
			}
		}
		if (r.currentRotation == r.CLOCKWISE) {
			r.p.applyForce(new PVector(r.p.speed, 0));
			r.p.update();
			r.p.draw();
		}
		
	}
	
	public void exit(DemoApp r) {
		super.exit(r);
		r.mArea[DemoApp.UPPERLEFT].resetColor();
	}
}


class UpperRightAnimateState extends RegionState {
	private static String myName = "UpperRightAnimateState";
	private static UpperRightAnimateState instance = null;
	
	
	private UpperRightAnimateState() {
		super(myName);
	}
	
	public static UpperRightAnimateState getInstance() {
		if(instance == null)
			instance = new UpperRightAnimateState();
		return instance;	
	}
	
	@Override
	public void execute(DemoApp r) {
		
		for(MouseArea ma : r.mArea) {
			if(ma.id() != DemoApp.UPPERRIGHT) {
				ma.draw();
			} else {
				ma.update();
				ma.draw();
			}
		}		
		if (r.currentRotation == r.CLOCKWISE) {
			r.p.applyForce(new PVector(0, r.p.speed));
			r.p.update();
			r.p.draw();
		}
		
	}

	public void exit(DemoApp r) {
		super.exit(r);
		r.mArea[DemoApp.UPPERRIGHT].resetColor();
	}
}


class LowerLeftAnimateState extends RegionState {
	private static String myName = "LowerLeftAnimateState";
	private static LowerLeftAnimateState instance = null;
	
	
	private LowerLeftAnimateState() {
		super(myName);
	}
	
	public static LowerLeftAnimateState getInstance() {
		if(instance == null)
			instance = new LowerLeftAnimateState();
		return instance;	
	}
	
	@Override
	public void execute(DemoApp r) {
		for(MouseArea ma : r.mArea) {
			if(ma.id() != DemoApp.LOWERLEFT) {
				ma.draw();
			} else {
				ma.update();
				ma.draw();
			}
		}	
		if (r.currentRotation == r.CLOCKWISE) {
			r.p.applyForce(new PVector(0, -r.p.speed));
			r.p.update();
			r.p.draw();
		}
	}

	public void exit(DemoApp r) {
		super.exit(r);
		r.mArea[DemoApp.LOWERLEFT].resetColor();
	}	
}


class LowerRightAnimateState extends RegionState {
	private static String myName = "LowerRightAnimateState";
	private static LowerRightAnimateState instance = null;
	
	
	private LowerRightAnimateState() {
		super(myName);
	}
	
	public static LowerRightAnimateState getInstance() {
		if(instance == null)
			instance = new LowerRightAnimateState();
		return instance;	
	}
	
	@Override
	public void execute(DemoApp r) {
		for(MouseArea ma : r.mArea) {
			if(ma.id() != DemoApp.LOWERRIGHT) {
				ma.draw();
			} else {
				ma.update();
				ma.draw();
			}
		}		
		if (r.currentRotation == r.CLOCKWISE) {
			r.p.applyForce(new PVector(-r.p.speed, 0));
			r.p.update();
			r.p.draw();
		}
	}
	
	public void exit(DemoApp r) {
		super.exit(r);
		r.mArea[DemoApp.LOWERRIGHT].resetColor();
	}	
}