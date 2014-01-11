

import Constants.Constants;
/**
 *This is the interface for the object that holds all reservation stations in the Tomasulo simulator
 * @see ReservationStation
 */
public interface ReservationStationContainer {

	/**
	 * @param rsName from constants Enum class
	 * @return the reservation station instance fitting that name
	 */
	public ReservationStation getReservationStation(Constants.ReservationStationNames rsName);
	
	/**
	 * @param inst is the instruction you want to issue
	 * @return True iff the issue succeeded
	 */
	public boolean issueInstruction(Instruction inst);
	
	/**
	 * Execute all possible reservation stations
	 */
	public void execute();
	
	/**
	 * update all queued values from cdb
	 */
	public void updateFromCDB();

	/**
	 * @return True iff all cdb values finished updating relevant registers and all the docks are empty 
	 * @see {@link Dock}
	 */
	public boolean isDone();

	/**
	 * for load store implementation
	 * @see LoadStoreReservationStation
	 */
	public void incClock();
}
