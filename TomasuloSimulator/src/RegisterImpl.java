
public class RegisterImpl<T> implements Register<T> {
	
	Constatns.State state;
	T data;
	Constatns.ReservationStationNames rsName;
	int dock;
	
	public RegisterImpl() { //Empty register
		super();
		this.state = null;
		this.data = null;
		this.rsName = null;
		dock = 0;
	}
	
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
