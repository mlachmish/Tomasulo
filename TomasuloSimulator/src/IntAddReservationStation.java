
import Constants.Constants;

public class IntAddReservationStation extends AbstractReservationStation{

	public IntAddReservationStation(int delay, int dockNumber) {
		super(delay, dockNumber);
		this.registers = Sim.intRegistersContainer;
	}

	@Override
	public void excecute() {
		for (int i = 0; i < dockNumber; i++) {
			if (docks[i].isExcecuting() && Clock.getClock() == docks[i].getExcecutionStartTime() + delay) {
				int result=0;
				Dock executionDock = docks[i];
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

				Register<Integer> resultRegister = new RegisterImpl<Integer>(Constants.State.Value, result, getName(), i);
				ReservationStationContainerImpl.CDBIntValues.add(resultRegister);
				//Trace
				executionDock.getInstruction().setCycleWriteCDB(Clock.getClock());
				executionDock.emptyDock();
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
		return Constants.ReservationStationNames.INTADD;
	}

}