import Constants.Constants;

public abstract class AbstractReservationStation implements ReservationStation{

	int delay;
	int dockNumber;
	Dock[] docks;
	boolean isExcecuting;
	int excecutionStartTime;
	int dockIndexExcecuting;
	RegistersContainer<?> registers;
	
	
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
	public boolean issue(Instruction inst) {
		for (int i = 0; i < dockNumber; i++) {
			if (docks[i].getOp() == null) {
				docks[i].setOp(inst.getOpcode());
				docks[i].setInstrNumber(inst.getInstructionNumber());
				docks[i].setJ(registers.getRegister(inst.getSRC0()).copy());
				docks[i].setK(registers.getRegister(inst.getSRC1()).copy());
//				docks[i].setJ(Sim.floatRegistersContainer.getRegister(inst.getSRC0()).copy());
//				
//				docks[i].setK(Sim.floatRegistersContainer.getRegister(inst.getSRC1()).copy());
				docks[i].setInstruction(inst);
				
				Sim.floatRegistersContainer.getRegister(inst.getDST()).setState(Constants.State.Queued);
				Sim.floatRegistersContainer.getRegister(inst.getDST()).setStationName(getName());
				Sim.floatRegistersContainer.getRegister(inst.getDST()).setDock(i);
				
				return true;
			}
		}
		return false;
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
			if (docks[i].getJ().getState() == Constants.State.Queued
					&& docks[i].getJ().getStationName() == cdbRegister.getStationName()
					&& docks[i].getJ().getDock() == cdbRegister.getDock()) {
				docks[i].getJ().setData(cdbRegister.getData());
				docks[i].getJ().setState(Constants.State.Value);
			}
			if (docks[i].getK().getState() == Constants.State.Queued
					&& docks[i].getK().getStationName() == cdbRegister.getStationName()
					&& docks[i].getK().getDock() == cdbRegister.getDock()) {
				docks[i].getK().setData(cdbRegister.getData());
				docks[i].getK().setState(Constants.State.Value);
			}
		}
	}
}
