import java.util.Map;


public interface ReservationStationContainer {

	public ReservationStation getReservationStation(Constatns.ReservationStationNames rsName);
	
	public boolean issueInstraction(Instruction inst);
	
	public void excecute();
	
	public void init(Map<String, Integer> configuration);
	
//	public void updateQueuedFromCDB(T value, Constatns.ReservationStationNames rsName, int dockNumber);
}
