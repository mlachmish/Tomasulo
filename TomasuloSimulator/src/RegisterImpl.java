
public class RegisterImpl<T> implements Register<T> {
	
	Constatns.State state;
	T data;
	Constatns.ReservationStationNames rsName;
	int dock;
	
//	/**
//	 * empty constructor
//	 */
//	public RegisterImpl() { //Empty register
//		super();
//		this.state = null;
//		this.data = null;
//		this.rsName = null;
//		dock = 0;
//	}
	
	/**
	 * 
	 * @param state current state
	 * @param data the value if there is one
	 * @param rsName the if state is queued, the name of the RS we're waiting for
	 * @param dock the dock within the RS we're waiting for
	 */
	public RegisterImpl(Constatns.State state, T data, Constatns.ReservationStationNames rsName,
			int dock) {
		super();
		this.state = state;
		this.data = data;
		this.rsName = rsName;
		this.dock = dock;
	}

	@Override
	public Constatns.State getState() {
		return state;
	}

	@Override
	public void setState(Constatns.State state) {
		this.state = state;
		
	}

	@Override
	public T getData() {
		return data;
	}

	@Override
	public void setData(T data) {
		this.data = data;		
	}

	@Override
	public Constatns.ReservationStationNames getStationName() {
		return rsName;
	}

	@Override
	public void setStationName(Constatns.ReservationStationNames rsName) {
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
		return new RegisterImpl<T>(state, data, rsName, dock);
	}

}
