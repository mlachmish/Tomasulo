/**
 * 
 * @author mlachmish
 *Clock is a Singletone
 */
public class Clock {

	private static Integer clock;
	
	private Clock() {
	}
	
	public static int getClock() {
		if (clock == null)
			clock = new Integer(0);
		return clock;
	}
	
	public static void incClock() {
		Clock.clock++;
	}
	
	
}
