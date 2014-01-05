
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
		for (int i = 0; i < docks.length; i++) {
			docks[i] = new Dock<>();
		}
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
			if (docks[i].getJ().getState() == Constatns.State.Queued
					&& docks[i].getJ().getStationName() == cdbRegister.getStationName()
					&& docks[i].getJ().getDock() == cdbRegister.getDock()) {
				docks[i].getJ().setData(cdbRegister.getData());
				docks[i].getJ().setState(Constatns.State.Value);
			}
			if (docks[i].getK().getState() == Constatns.State.Queued
					&& docks[i].getK().getStationName() == cdbRegister.getStationName()
					&& docks[i].getK().getDock() == cdbRegister.getDock()) {
				docks[i].getK().setData(cdbRegister.getData());
				docks[i].getK().setState(Constatns.State.Value);
			}
		}
	}
}
