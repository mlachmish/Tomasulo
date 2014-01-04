import java.util.LinkedList;
import java.util.Queue;


public class LoadStoreReservationStation implements ReservationStation{

	int delay;
	Queue<Buffer> buffer;
	boolean isExcecuting;
	int excecutionStartTime;
	int loadBufferSize;
	int storeBufferSize;
	int storeCounter;
	int loadCounter;

	public LoadStoreReservationStation(int delay, int loadBufferSize, int storeBufferSize) {
		super();
		this.delay = delay;
		this.isExcecuting = false;
		this.excecutionStartTime = 0;
		this.buffer = new LinkedList<>();
		this.loadBufferSize = loadBufferSize;
		this.storeBufferSize = storeBufferSize;
		this.storeCounter = 0;
		this.loadCounter = 0;
	}

	@Override
	public boolean isReadyToExcecute() {
		if (isExcecuting) {
			return false;
		}

		Buffer firstBuf = buffer.peek();

		if (firstBuf != null) {
			if (firstBuf.getOp() == Constatns.Opcode.ST) {
				if (firstBuf.getJ().getState() == Constatns.State.Value
						&& firstBuf.getK().getState() == Constatns.State.Value) {
					return true;
				}
			} else if (firstBuf.getOp() == Constatns.Opcode.LD) {
				if (firstBuf.getJ().getState() == Constatns.State.Value)
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
		}
		if (isExcecuting && (Clock.getClock() == excecutionStartTime + delay)) {
			Buffer currInst = buffer.peek();
			float result = 0;
			if (currInst.getOp() == Constatns.Opcode.ST){
				Sim.memory.store(currInst.getJ().getData() + currInst.getImmidiate(), currInst.getK().getData());
				storeCounter--;
			} else if (currInst.getOp() == Constatns.Opcode.LD){
				result = Sim.memory.load(currInst.getJ().getData() + currInst.getImmidiate());
				Register<Float> resultRegister = new RegisterImpl<Float>(Constatns.State.Value, result, getName(), currInst.getInstrNumber());
				ReservationStationContainerImpl.CDBFloatValues.add(resultRegister);
				loadCounter--;
			}
			isExcecuting = false;
			buffer.poll();
			excecutionStartTime = 0;
		}
	}

	@Override
	public boolean issue(Instruction inst) {
		if (inst.getOpcode() == Constatns.Opcode.ST) {
			if (storeCounter < storeBufferSize) {
				Buffer newBuffer = new Buffer();
				newBuffer.setOp(inst.getOpcode());
				newBuffer.setInstrNumber(inst.getInstructionNumber());
				newBuffer.setImmidiate(inst.getIMM());
				newBuffer.setJ(Sim.intRegistersContainer.getRegister(inst.getSRC0()).copy());
				newBuffer.setK(Sim.floatRegistersContainer.getRegister(inst.getSRC1()).copy());
				buffer.add(newBuffer);
				storeCounter++;
				return true;
			}
		} else if (inst.getOpcode() == Constatns.Opcode.LD) {
			if (loadCounter < loadBufferSize) {
				Buffer newBuffer = new Buffer();
				newBuffer.setOp(inst.getOpcode());
				newBuffer.setInstrNumber(inst.getInstructionNumber());
				newBuffer.setImmidiate(inst.getIMM());
				newBuffer.setJ(Sim.intRegistersContainer.getRegister(inst.getSRC0()).copy());
				buffer.add(newBuffer);
				loadCounter++;

				Sim.floatRegistersContainer.getRegister(inst.getDST()).setState(Constatns.State.Queued);
				Sim.floatRegistersContainer.getRegister(inst.getDST()).setStationName(getName());
				Sim.floatRegistersContainer.getRegister(inst.getDST()).setDock(newBuffer.instrNumber);
				return true;
			}
		}
		return false;
	}

	@Override
	public void updateWithRegister(Register<?> cdbRegister) {
		for (Buffer curBuf : buffer) {
			if (curBuf.getJ().getState() ==Constatns.State.Queued
					&& curBuf.getJ().getStationName() == cdbRegister.getStationName()
					&& curBuf.getJ().getDock() == cdbRegister.getDock()) {
				curBuf.getJ().setData((Integer)cdbRegister.getData());
				curBuf.getJ().setState(Constatns.State.Value);
			}
			if (curBuf.getK().getState() ==Constatns.State.Queued
					&& curBuf.getK().getStationName() == cdbRegister.getStationName()
					&& curBuf.getK().getDock() == cdbRegister.getDock()) {
				curBuf.getK().setData((Float)cdbRegister.getData());
				curBuf.getK().setState(Constatns.State.Value);
			}
		}
	}

	@Override
	public Constatns.ReservationStationNames getName() {
		return Constatns.ReservationStationNames.LDST;
	}
}
