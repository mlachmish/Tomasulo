import Constants.Constants;

public class IntAddReservationStation extends AbstractReservationStation{

	public IntAddReservationStation(int delay, int dockNumber) {
		super(delay, dockNumber);
		this.registers = Sim.intRegistersContainer;
	}

	@Override
	public void excecute() {
		if (isExcecuting && (Clock.getClock() == excecutionStartTime + delay)) {
			int result=0;
			Dock executionDock = docks[dockIndexExcecuting];
			Constants.Opcode op = executionDock.getOp(); 
			switch (op) {
			case ADD:
				result = (int)executionDock.getJ().getData() + (int)executionDock.getK().getData();
				break;
			case SUB:
				result = (int)executionDock.getJ().getData() - (int)executionDock.getK().getData();
				break;
			case ADDI:
				result = (int)executionDock.getJ().getData() + executionDock.getInstruction().getIMM(); //need IMM
				break;
			case SUBI:
				result = (int)executionDock.getJ().getData() - executionDock.getInstruction().getIMM(); //need IMM
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
				throw new Error("Internal Error");
			}

		Register<Integer> resultRegister = new RegisterImpl<Integer>(Constants.State.Value, result, getName(), dockIndexExcecuting);
		ReservationStationContainerImpl.CDBIntValues.add(resultRegister);
		executionDock.getInstruction().setCycleWriteCDB(Clock.getClock());
		isExcecuting = false;
		executionDock.emptyDock();
		excecutionStartTime = 0;
		dockIndexExcecuting = -1;
		

		}
		
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
			docks[dockIndexExcecuting].getInstruction().setCycleExcecuteStart(excecutionStartTime);
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
