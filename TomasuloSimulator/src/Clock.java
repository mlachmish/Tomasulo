/**
 * 
 * @author mlachmish
 *Clock is a Singletone
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
	}
	
	
}
