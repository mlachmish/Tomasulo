
public class FpAddReservationStation extends AbstractReservationStation{

	public FpAddReservationStation(int delay, int dockNumber) {
		super(delay,dockNumber);
	}
	
	@Override
	public boolean issue(Instruction inst) {
		for (int i = 0; i < dockNumber; i++) {
			if (docks[i].getOp() == null) {
				docks[i].setOp(inst.getOpcode());
				docks[i].setJ(Sim.floatRegistersContainer.getRegister(inst.getSRC0()).copy());
				docks[i].setK(Sim.floatRegistersContainer.getRegister(inst.getSRC1()).copy());
				
				Sim.floatRegistersContainer.getRegister(inst.getDST()).setState(Constatns.State.Queued);
				Sim.floatRegistersContainer.getRegister(inst.getDST()).setStationName(getName());
				Sim.floatRegistersContainer.getRegister(inst.getDST()).setDock(i);
				
				return true;
			}
		}
		return false;
	}

	@Override
	public void excecute() {
		if (isReadyToExcecute()) {
			isExcecuting = true;
			excecutionStartTime = Clock.getClock();
			
			int minInstrNum = Integer.MAX_VALUE;
			for (int i = 0; i < dockNumber; i++) {
				if (docks[i].isReady()) {
					if (docks[i].instrNumber < minInstrNum) {
						minInstrNum = docks[i].instrNumber;
						dockIndexExcecuting = i;
					}
				}
			}
		}
		
		if (isExcecuting && (Clock.getClock() == excecutionStartTime + delay)) {
			float result=0f;
			if (docks[dockIndexExcecuting].getOp() == Constatns.Opcode.ADDS){
				result = (float)docks[dockIndexExcecuting].getJ().getData() + (float)docks[dockIndexExcecuting].getK().getData();
			} else if (docks[dockIndexExcecuting].getOp() == Constatns.Opcode.SUBS){
				result = (float)docks[dockIndexExcecuting].getJ().getData() - (float)docks[dockIndexExcecuting].getK().getData();
			}
		Register<Float> resultRegister = new RegisterImpl<Float>(Constatns.State.Value, result, getName(), dockIndexExcecuting);
		ReservationStationContainerImpl.CDBFloatValues.add(resultRegister);
		isExcecuting = false;
		docks[dockIndexExcecuting].emptyDock();
		excecutionStartTime = 0;
		dockIndexExcecuting = -1;
		}
	}

	@Override
	public Constatns.ReservationStationNames getName() {
		return Constatns.ReservationStationNames.FPADD;
	}

}
