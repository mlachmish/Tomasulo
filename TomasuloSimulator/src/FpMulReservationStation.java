import Constants.Constants;

public class FpMulReservationStation extends AbstractReservationStation{

	public FpMulReservationStation(int delay, int dockNumber) {
		super(delay,dockNumber);
		this.registers = Sim.floatRegistersContainer;
	}
	
	@Override
	public void excecute() {		
		if (isExcecuting && (Clock.getClock() == excecutionStartTime + delay)) {
			float result=0f;
			Dock excecutionDock = docks[dockIndexExcecuting];
			if (excecutionDock.getOp() == Constants.Opcode.MULTS){
				result = (float)excecutionDock.getJ().getData() * (float)excecutionDock.getK().getData();
			}
		Register<Float> resultRegister = new RegisterImpl<Float>(Constants.State.Value, result, getName(), dockIndexExcecuting);
		ReservationStationContainerImpl.CDBFloatValues.add(resultRegister);
		excecutionDock.getInstruction().setCycleWriteCDB(Clock.getClock());
		isExcecuting = false;
		excecutionDock.emptyDock();		
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

	@Override
	public Constants.ReservationStationNames getName() {
		return Constants.ReservationStationNames.FPMULT;
	}
}
