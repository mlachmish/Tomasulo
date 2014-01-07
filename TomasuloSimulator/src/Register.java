import Constants.Constants;

public interface Register<T> {

	public Constants.State getState();
	public void setState(Constants.State state);

	public T getData();
	public void setData(T data);
	
	public Constants.ReservationStationNames getStationName();
	public void setStationName(Constants.ReservationStationNames rsName);
	
	public int getDock();
	public void setDock(int dockNum);

	public Register<T> copy();
}
