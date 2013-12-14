
public interface ReservationStationContainer<T> extends Container<T> {

	public ReservationStation<T> getReservationStation(int num);
	
	/**
	 * 
	 * @param inst
	 * @return true iff issued successful
	 */
	public boolean issueInstraction(Instruction inst);
	
	public void excecute();
}
