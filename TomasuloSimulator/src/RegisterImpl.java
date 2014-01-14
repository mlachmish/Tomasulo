import Constants.Constants;
import Constants.Constants.State;

public class RegisterImpl<T> implements Register<T> {
	
	Constants.State state;
	T data;
	Constants.ReservationStationNames rsName;
	int dock;
	
	/**
	 * 
	 * @param state current state
	 * @param data the value if there is one
	 * @param rsName the if state is queued, the name of the RS we're waiting for
	 * @param dock the dock within the RS we're waiting for
	 */
	public RegisterImpl(Constants.State state, T data, Constants.ReservationStationNames rsName,
			int dock) {
		super();
		this.state = state;
		this.data = data;
		this.rsName = rsName;
		this.dock = dock;
	}

	@Override
	public Constants.State getState() {
		return state;
	}

	@Override
	public void setState(Constants.State state) {
		this.state = state;
		
	}

	@Override
	public T getData() {
		return data;
	}

	@Override
	public void setData(T data) {
		this.data = data;
		this.state = State.Value;
	}

	@Override
	public Constants.ReservationStationNames getStationName() {
		return rsName;
	}

	@Override
	public void setStationName(Constants.ReservationStationNames rsName) {
		this.rsName = rsName;
	}

	@Override
	public int getDock() {
		return dock;
	}

	@Override
	public void setDock(int dockNum) {
		this.dock = dockNum;
	}
	
	@Override
	public Register<T> copy() {
		// real copy!!!!!
//		State newState = new State 
		return new RegisterImpl<T>(state, data, rsName, dock);
	}

}
