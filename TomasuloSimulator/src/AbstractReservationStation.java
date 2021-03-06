import Constants.Constants;

/**
 *This is an abstract class for all ALU reservation station
 */
public abstract class AbstractReservationStation implements ReservationStation{

	int delay;
	int dockNumber;
	Dock[] docks;
	RegistersContainer<?> registers;


	public AbstractReservationStation(int delay, int dockNumber) {
		super();
		this.delay = delay;
		this.dockNumber = dockNumber;
		this.docks = new Dock[dockNumber];
		for (int i = 0; i < docks.length; i++) {
			docks[i] = new Dock<>();
		}
	}

	@Override
	public boolean isEmpty() {
		for (int i = 0; i < dockNumber; i++) {
			if (docks[i].getOp() != null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean issue(Instruction inst) {
		for (int i = 0; i < dockNumber; i++) {
			if (docks[i].isReadyForIssue()) {
				docks[i].setOp(inst.getOpcode());
				docks[i].setInstrNumber(inst.getInstructionNumber());
				docks[i].setJ(registers.getRegister(inst.getSRC0()).copy());
				docks[i].setK(registers.getRegister(inst.getSRC1()).copy());
				docks[i].setInstruction(inst);

				registers.getRegister(inst.getDST()).setState(Constants.State.Queued);
				registers.getRegister(inst.getDST()).setStationName(getName());
				registers.getRegister(inst.getDST()).setDock(i);

				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isReadyToExcecute() {
		for (int i = 0; i < dockNumber; i++) {
			if (docks[i].isReady() && !docks[i].isExcecuting())
				return true;
		}
		return false;
	}

	@Override
	public void updateWithRegister(Register<?> cdbRegister) {
		for (int i = 0; i < dockNumber; i++) {
			if (docks[i].isEmpty()) continue;
			if (docks[i].getJ().getState() == Constants.State.Queued
					&& docks[i].getJ().getStationName() == cdbRegister.getStationName()
					&& docks[i].getJ().getDock() == cdbRegister.getDock()) {
				docks[i].getJ().setData(cdbRegister.getData());
				docks[i].getJ().setState(Constants.State.Value);
				docks[i].getJ().setStationName(null);
				docks[i].getJ().setDock(-1);
			}
			if (docks[i].getK().getState() == Constants.State.Queued
					&& docks[i].getK().getStationName() == cdbRegister.getStationName()
					&& docks[i].getK().getDock() == cdbRegister.getDock()) {
				docks[i].getK().setData(cdbRegister.getData());
				docks[i].getK().setState(Constants.State.Value);
				docks[i].getK().setStationName(null);
				docks[i].getK().setDock(-1);
			}
		}
	}
	
	public void incClock()
	{		
	}
}