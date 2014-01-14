import Constants.Constants;

/**
 *Represent a register in Tomasulo model
 */
public interface Register<T> {

	/**
	 * @return the state Enum of this register
	 * @see Constants.State
	 */
	public Constants.State getState();
	public void setState(Constants.State state);

	/**
	 * @return The data this register holds. Can be Int or Float
	 */
	public T getData();
	public void setData(T data);
	
	/**
	 * @return The name of the station this register value is depended on
	 * @see Constants.ReservationStationNames
	 */
	public Constants.ReservationStationNames getStationName();
	public void setStationName(Constants.ReservationStationNames rsName);
	
	/**
	 * @return The number of the dock this register value is depended on
	 * @see Dock
	 */
	public int getDock();
	public void setDock(int dockNum);

	/**
	 * @return A copy of this register
	 */
	public Register<T> copy();
}