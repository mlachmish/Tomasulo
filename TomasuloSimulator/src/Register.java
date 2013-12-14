
public interface Register<T> {

	public Constatns.State getState();
	public void setState(Constatns.State state);

	public T getData();
	public void setData(T data);
	
	public Constatns.ReservationStationNames getStationName();
	public void setStationName(Constatns.ReservationStationNames rsName);
	
	public int getDock();
	public void setDock(int dockNum);
	
	public Register<T> clone();
}
