import Constants.Constants;

public class IntAddReservationStation extends AbstractReservationStation{

	public IntAddReservationStation(int delay, int dockNumber) {
		super(delay, dockNumber);
		this.registers = Sim.intRegistersContainer;
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
			int result=0;
			Constants.Opcode op = docks[dockIndexExcecuting].getOp(); 
			switch (op) {
			case ADD:
				result = (int)docks[dockIndexExcecuting].getJ().getData() + (int)docks[dockIndexExcecuting].getK().getData();
				break;
			case SUB:
				result = (int)docks[dockIndexExcecuting].getJ().getData() - (int)docks[dockIndexExcecuting].getK().getData();
				break;
			case ADDI:
				result = (int)docks[dockIndexExcecuting].getJ().getData() + docks[dockIndexExcecuting].getInstruction().getIMM(); //need IMM
				break;
			case SUBI:
				result = (int)docks[dockIndexExcecuting].getJ().getData() - docks[dockIndexExcecuting].getInstruction().getIMM(); //need IMM
				break;
			case ADDS:				
			case BEQ:
			case BNE:
			case HALT:
			case JUMP:
			case LD:
			case MULTS:
			case ST:
			
			
			case SUBS:

			default:
				break;
			}
//			if (docks[dockIndexExcecuting].getOp() == Constatns.Opcode.ADD){
//				result = (int)docks[dockIndexExcecuting].getJ().getData() + (int)docks[dockIndexExcecuting].getK().getData();
//			} else if (docks[dockIndexExcecuting].getOp() == Constatns.Opcode.SUB){
//				result = (int)docks[dockIndexExcecuting].getJ().getData() - (int)docks[dockIndexExcecuting].getK().getData();
//			} else if (docks)
//			{
//				
//			} 
//			
//			else
//			{				
//				throw new Error("Internal Error");
//			}
		Register<Integer> resultRegister = new RegisterImpl<Integer>(Constants.State.Value, result, getName(), dockIndexExcecuting);
		ReservationStationContainerImpl.CDBIntValues.add(resultRegister);
		isExcecuting = false;
		docks[dockIndexExcecuting].emptyDock();
		excecutionStartTime = 0;
		dockIndexExcecuting = -1;
		}
	}


//	@Override
//	public boolean issue(Instruction inst) {
//		for (int i = 0; i < dockNumber; i++) {
//			if (docks[i].getOp() == null) {
//				docks[i].setOp(inst.getOpcode());
//				docks[i].setInstrNumber(inst.getInstructionNumber());
//				docks[i].setJ(Sim.intRegistersContainer.getRegister(inst.getSRC0()).copy());
//				docks[i].setK(Sim.intRegistersContainer.getRegister(inst.getSRC1()).copy());
//				
//				Sim.intRegistersContainer.getRegister(inst.getDST()).setState(Constatns.State.Queued);
//				Sim.intRegistersContainer.getRegister(inst.getDST()).setStationName(getName());
//				Sim.intRegistersContainer.getRegister(inst.getDST()).setDock(i);
//				
//				return true;
//			}
//		}
//		return false;
//	}
	
	@Override
	public Constants.ReservationStationNames getName() {
		return Constants.ReservationStationNames.INTADD;
	}

}
