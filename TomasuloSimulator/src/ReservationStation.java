
public interface ReservationStation {

	public Constatns.ReservationStationNames getName();
	
	public boolean isReadyToExcecute();
	
	public void excecute();

	boolean issue(Instruction inst);
	
	public void updateWithRegister(Register<?> cdbregister);
}
