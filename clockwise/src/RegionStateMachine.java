
public class RegionStateMachine {
	protected DemoApp app = null;
	protected RegionState currentState = null;
	protected RegionState previousState = null;
	protected RegionState globalState = null;
	
	public RegionStateMachine (DemoApp owner) {
		this.app = owner;
	}
	
	// the next 3 methods are used to initialize the State Machine
	public void setCurrentState(RegionState state) {
		currentState = state;
	}
	
	public void setPreviousState(RegionState state) {
		previousState = state;
	}
	
	public void setGlobalState(RegionState state) {
		globalState = state;
	}

	// update the State Machine
	public void update() {
		// only run this if we have a global state
		if(globalState != null) {
			globalState.execute( app );
		}
		
		// only run if we have a current state
		if(currentState != null) {
			currentState.execute(app);
		}
	}
	
	
	public void changeState(RegionState newState) {
		assert newState != null : "<StateMachine::changeState> trying to assign null state to current";
		
		//keep a record of the previous state
		previousState = currentState;
		
		//call exit on the current state
		currentState.exit(app);
		
		// update the current state...
		currentState = newState;
		
		// ...run entry logic for current state
		currentState.enter(app);
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

}
