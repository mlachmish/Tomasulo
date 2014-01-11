import Constants.Constants.*;

public interface ReservationStation {

	public ReservationStationNames getName();
	
	public boolean isReadyToExcecute();
	
	public void excecute();

	boolean issue(Instruction inst);
	
	public void updateWithRegister(Register<?> cdbregister);
	
	public boolean isEmpty();
}
