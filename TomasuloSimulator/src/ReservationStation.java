
import Constants.Constants;
import Constants.Constants.*;

/**
 *Represent a reservation station in Tomasulo model
 */
public interface ReservationStation {

	/**
	 * @return the reservation station name Enum
	 * @see Constants.ReservationStationNames
	 */
	public ReservationStationNames getName();
	
	/**
	 * @return True iff there exist a dock with no queued values ready to be executed
	 * @see Dock
	 */
	public boolean isReadyToExcecute();
	
	/**
	 * Execute an instruction if you can
	 */
	public void excecute();

	/**
	 * @param inst Instruction to be issued
	 * @return True iff issue succeeded
	 */
	boolean issue(Instruction inst);
	
	/**
	 * @param cdbregister the register from the cdb who's value is the updated one
	 */
	public void updateWithRegister(Register<?> cdbregister);
	
	/**
	 * @return True iff all docks are empty
	 * @see Dock
	 */
	public boolean isEmpty();

	/**
	 * for load store implementation
	 * @see LoadStoreReservationStation
	 */
	public void incClock();
}
