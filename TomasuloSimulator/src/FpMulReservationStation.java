
import Constants.Constants;

public class FpMulReservationStation extends AbstractReservationStation{

	public FpMulReservationStation(int delay, int dockNumber) {
		super(delay,dockNumber);
		this.registers = Sim.floatRegistersContainer;
	}

	@Override
	public void excecute() {		
		for (int i = 0; i < dockNumber; i++) {
			if (docks[i].isExcecuting() && Clock.getClock() == docks[i].getExcecutionStartTime() + delay) {
				// if execution is done calculate and put in cdb
				float result=0f;
				Dock excecutionDock = docks[i];
				if (excecutionDock.getOp() == Constants.Opcode.MULTS){
					result = (float)excecutionDock.getJ().getData() * (float)excecutionDock.getK().getData();
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
			//start a new inst
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
		return Constants.ReservationStationNames.FPMULT;
	}
}
