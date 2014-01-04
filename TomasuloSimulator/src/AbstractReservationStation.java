
public abstract class AbstractReservationStation implements ReservationStation{

	int delay;
	int dockNumber;
	Dock[] docks;
	boolean isExcecuting;
	int excecutionStartTime;
	int dockIndexExcecuting;
	
	public AbstractReservationStation(int delay, int dockNumber) {
		super();
		this.delay = delay;
		this.dockNumber = dockNumber;
		this.isExcecuting = false;
		this.excecutionStartTime = 0;
		this.dockIndexExcecuting = -1;
		this.docks = new Dock[dockNumber];
	}
	
	@Override
	public boolean isReadyToExcecute() {
		if (isExcecuting) {
			return false;
		}

		for (int i = 0; i < dockNumber; i++) {
			if (docks[i].isReady())
				return true;
		}
		return false;
	}
	
	@Override
	public void updateWithRegister(Register<?> cdbRegister) {
		for (int i = 0; i < dockNumber; i++) {
			if (docks[i].j.getState() == Constatns.State.Queued
					&& docks[i].j.getStationName() == cdbRegister.getStationName()
					&& docks[i].j.getDock() == cdbRegister.getDock()) {
				docks[i].j.setData(cdbRegister.getData());
				docks[i].j.setState(Constatns.State.Value);
			}
			if (docks[i].k.getState() == Constatns.State.Queued
					&& docks[i].k.getStationName() == cdbRegister.getStationName()
					&& docks[i].k.getDock() == cdbRegister.getDock()) {
				docks[i].k.setData(cdbRegister.getData());
				docks[i].k.setState(Constatns.State.Value);
			}
		}
	}
}
