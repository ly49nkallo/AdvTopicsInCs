
public class RegionStateMachine {
	protected Particle p = null;
	protected RegionState currentState = null;
	protected RegionState previousState = null;
	protected RegionState globalState = null;
	protected RotationState currentRotationState = null;
	protected RotationState globalRotationState = null;

	
	public RegionStateMachine (Particle owner) {
		this.p = owner;
	}
	
	// the next 3 methods are used to initialize the State Machine
	public void setCurrentState(RegionState state) {
		System.out.println("setting current state to " + state);
		currentState = state;
	}
	public void setCurrentRotationState(RotationState state) {
		System.out.println("setting current rotation state to " + state);
		currentRotationState = state;
	}
	
	public void setPreviousState(RegionState state) {
		previousState = state;
	}
	public void setPreviousState(RotationState state) {
		//not implemented
		return;
	}
	
	public void setGlobalState(RegionState state) {
		globalState = state;
	}

	public void setGlobalRotationState(RotationState state) {
		globalRotationState = state;
	}

	// update the State Machine
	public void update() {
		// only run this if we have a global state
		if(globalState != null) {
			globalState.execute( p );
		}
		
		// only run if we have a current state
		if(currentState != null) {
			currentState.execute(p);
		}

		if(globalRotationState != null) {
			globalRotationState.execute(p);
		}
		else System.out.println("help");

		if(currentRotationState != null) {
			currentRotationState.execute(p);
		}
		else System.out.println("help");
	}
	
	
	public void changeState(RegionState newState) {
		assert newState != null : "<StateMachine::changeState> trying to assign null state to current";
		
		//keep a record of the previous state
		previousState = currentState;
		
		//call exit on the current state
		currentState.exit(p);
		
		// update the current state...
		currentState = newState;
		
		// ...run entry logic for current state
		currentState.enter(p);
	}
	
	public void changeRotation(RotationState newState) {
		assert newState != null : "<StateMachine::changeState> trying to assign null state to rotation current";

		currentRotationState.exit(p);

		currentRotationState = newState;

		currentRotationState.enter(p);

	}
	public void revertToPreviousState() {
		changeState(previousState);
	}
		
	// note this does straight object comparison. So even though the 2 states might be of the 
	// class they will not be equal unless they are the same object. 
	// TODO: make this more fool proof

	public boolean isInState(RegionState state) {
		return currentState.getClass().getName().equals(state.getClass().getName());
	}

	public boolean isInRotationState (RotationState state) {
		return currentRotationState.getClass().getName().equals(state.getClass().getName());
	}

}
