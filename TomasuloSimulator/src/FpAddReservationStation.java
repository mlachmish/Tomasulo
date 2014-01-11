
import Constants.Constants;

public class FpAddReservationStation extends AbstractReservationStation{

	public FpAddReservationStation(int delay, int dockNumber) {
		super(delay,dockNumber);
		this.registers = Sim.floatRegistersContainer;
	}
	
//	@Override
//	public boolean issue(Instruction inst) {
//		for (int i = 0; i < dockNumber; i++) {
//			if (docks[i].getOp() == null) {
//				docks[i].setOp(inst.getOpcode());
//				docks[i].setInstrNumber(inst.getInstructionNumber());
//				docks[i].setJ(Sim.floatRegistersContainer.getRegister(inst.getSRC0()).copy());
//				docks[i].setK(Sim.floatRegistersContainer.getRegister(inst.getSRC1()).copy());
//				docks[i].setInstruction(inst);
//				
//				Sim.floatRegistersContainer.getRegister(inst.getDST()).setState(Constatns.State.Queued);
//				Sim.floatRegistersContainer.getRegister(inst.getDST()).setStationName(getName());
//				Sim.floatRegistersContainer.getRegister(inst.getDST()).setDock(i);
//				
//				return true;
//			}
//		}
//		return false;
//	}

	@Override
	public void excecute() {
		for (int i = 0; i < dockNumber; i++) {
			if (docks[i].isExcecuting() && Clock.getClock() == docks[i].getExcecutionStartTime() + delay) {
				float result=0f;
				Dock excecutionDock = docks[i];
				if (excecutionDock.getOp() == Constants.Opcode.ADDS){
					result = (float)excecutionDock.getJ().getData() + (float)excecutionDock.getK().getData();
				} else if (excecutionDock.getOp() == Constants.Opcode.SUBS){
					result = (float)excecutionDock.getJ().getData() - (float)excecutionDock.getK().getData();
				}
				Register<Float> resultRegister = new RegisterImpl<Float>(Constants.State.Value, result, getName(), i);
				ReservationStationContainerImpl.CDBFloatValues.add(resultRegister);
				//Trace
				excecutionDock.getInstruction().setCycleWriteCDB(Clock.getClock());
				excecutionDock.emptyDock();
				docks[i].setExcecutionStartTime(-1);
				break;
			}
		}
		
		if (isReadyToExcecute()) {
			int dockIndexExcecuting = -1;
			int excecutionStartTime = Clock.getClock();
			
			int minInstrNum = Integer.MAX_VALUE;
			for (int i = 0; i < dockNumber; i++) {
				if (docks[i].isReady() && !docks[i].isExcecuting()) {
					if (docks[i].getInstrNumber() < minInstrNum) {
						minInstrNum = docks[i].getInstrNumber();
						dockIndexExcecuting = i;
					}
				}
			}
			docks[dockIndexExcecuting].setExcecutionStartTime(excecutionStartTime);
			docks[dockIndexExcecuting].getInstruction().setCycleExcecuteStart(excecutionStartTime);
		}
		
		
	}

	@Override
	public Constants.ReservationStationNames getName() {
		return Constants.ReservationStationNames.FPADD;
	}

}
