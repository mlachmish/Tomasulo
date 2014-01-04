
public interface ReservationStationContainer<T> extends Container<T> {

	public ReservationStation<T> getReservationStation(Constatns.ReservationStationNames rsName);
	
	public boolean issueInstraction(Instruction inst);
	
	public void excecute();
}
