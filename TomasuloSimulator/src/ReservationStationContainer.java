import Constants.Constants;

public interface ReservationStationContainer {

	public ReservationStation getReservationStation(Constants.ReservationStationNames rsName);
	
	public boolean issueInstruction(Instruction inst);
	
	public void excecute();
	
	public void updateFromCDB();

	public boolean isDone();
}
