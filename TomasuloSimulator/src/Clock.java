/**
 * Represent the clock of the entire system
 *Clock is a Singleton
 */
public class Clock {

	private static Integer clock = new Integer(0);
	
	private Clock() {
	}
	
	public static int getClock() {		
		return clock;
	}
	
	public static void incClock() {
		Clock.clock++;
		Sim.reservationStationContainer.incClock();
	}
	
	
}
