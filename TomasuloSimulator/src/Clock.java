/**
 * 
 * @author mlachmish
 *Clock is a Singletone
 */
public class Clock {

	private static Integer clock;
	
	private Clock() {
	}
	
	public int getClock() {
		if (clock == null)
			clock = new Integer(0);
		return clock;
	}
	
	public void incClock() {
		Clock.clock++;
	}
	
	
}
