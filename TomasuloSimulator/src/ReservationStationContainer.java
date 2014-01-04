
public interface ReservationStationContainer {

	public ReservationStation getReservationStation(Constatns.ReservationStationNames rsName);
	
	public boolean issueInstruction(Instruction inst);
	
	public void excecute();
	
	public void updateFromCDB();
}
