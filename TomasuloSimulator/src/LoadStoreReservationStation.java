
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import Constants.Constants;

public class LoadStoreReservationStation implements ReservationStation {

	int delay;
	LinkedList<Buffer> buffer;
	boolean isExcecuting;
	int excecutionStartTime;
	int loadBufferSize;
	int storeBufferSize;
	int storeCounter;
	int loadCounter;
	int prevStoreCounter;
	int prevLoadCounter;

	public LoadStoreReservationStation(int delay, int loadBufferSize,
			int storeBufferSize) {
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

	public Buffer excecuteNext() {
		Buffer currentBuffer = null;
		for (Iterator iterator2 = buffer.iterator(); iterator2
				.hasNext();) {
			currentBuffer = (Buffer) iterator2.next();
			if (currentBuffer != null && !currentBuffer.isExcecuting()) {
				return currentBuffer;
			} else {
				currentBuffer = null;
			}
		}
		return currentBuffer;
	}

	@Override
	public void excecute() {
		Buffer currInst = buffer.peek();
		if (currInst == null)
			return;
		if (currInst.isExcecuting()
				&& (Clock.getClock() == currInst.getExcecutionStartTime()
						+ delay)) {

			float result = 0;
			if (currInst.getOp() == Constants.Opcode.ST) {
				Sim.memory.store(
						currInst.getJ().getData() + currInst.getImmidiate(),
						currInst.getK().getData());
				currInst.getInstruction().setCycleWriteCDB(-1);
				storeCounter--;
			} else if (currInst.getOp() == Constants.Opcode.LD) {
				result = Sim.memory.load(currInst.getJ().getData()
						+ currInst.getImmidiate());
				Register<Float> resultRegister = new RegisterImpl<Float>(
						Constants.State.Value, result, getName(),
						currInst.getInstrNumber());
				ReservationStationContainerImpl.CDBFloatValues
						.add(resultRegister);
				currInst.getInstruction().setCycleWriteCDB(Clock.getClock());
				loadCounter--;
			}
			buffer.poll();
		}
		if (isReadyToExcecute()) {
			Buffer newBufferToStart = excecuteNext();
			if (newBufferToStart != null) {
				newBufferToStart.setExcecutionStartTime(Clock.getClock());
				newBufferToStart.getInstruction().setCycleExcecuteStart(
						Clock.getClock());
			}
		}
	}

	@Override
	public boolean issue(Instruction inst) {
		if (inst.getOpcode() == Constants.Opcode.ST) {
			if (prevStoreCounter < storeBufferSize) {
				Buffer newBuffer = new Buffer(inst.getOpcode(),
						Sim.intRegistersContainer.getRegister(inst.getSRC0())
								.copy(), Sim.floatRegistersContainer
								.getRegister(inst.getSRC1()).copy(),
						inst.getIMM(), inst.getInstructionNumber(), inst);
				buffer.add(newBuffer);
				storeCounter++;
				return true;
			}
		} else if (inst.getOpcode() == Constants.Opcode.LD) {
			if (prevLoadCounter < loadBufferSize) {
				Buffer newBuffer = new Buffer(inst.getOpcode(),
						Sim.intRegistersContainer.getRegister(inst.getSRC0())
								.copy(), null, inst.getIMM(),
						inst.getInstructionNumber(), inst);
				buffer.add(newBuffer);
				loadCounter++;
				Sim.floatRegistersContainer.getRegister(inst.getDST())
						.setState(Constants.State.Queued);
				Sim.floatRegistersContainer.getRegister(inst.getDST())
						.setStationName(getName());
				Sim.floatRegistersContainer.getRegister(inst.getDST()).setDock(
						newBuffer.instrNumber);
				return true;
			}
		}
		return false;
	}

	@Override
	public void updateWithRegister(Register<?> cdbRegister) {
		for (Buffer curBuf : buffer) {
			if (curBuf.getJ().getState() == Constants.State.Queued
					&& curBuf.getJ().getStationName() == cdbRegister
							.getStationName()
					&& curBuf.getJ().getDock() == cdbRegister.getDock()) {
				curBuf.getJ().setData((Integer) cdbRegister.getData());
				curBuf.getJ().setState(Constants.State.Value);
				curBuf.getJ().setStationName(null);
				curBuf.getJ().setDock(-1);
			}
			if (curBuf.getInstruction().getOpcode() != Constants.Opcode.LD) {
				if (curBuf.getK().getState() == Constants.State.Queued
						&& curBuf.getK().getStationName() == cdbRegister
								.getStationName()
						&& curBuf.getK().getDock() == cdbRegister.getDock()) {
					curBuf.getK().setData((Float) cdbRegister.getData());
					curBuf.getK().setState(Constants.State.Value);
					curBuf.getK().setStationName(null);
					curBuf.getK().setDock(-1);
				}
			}
		}
	}

	@Override
	public Constants.ReservationStationNames getName() {
		return Constants.ReservationStationNames.LDST;
	}

	@Override
	public boolean isEmpty() {
		return (loadCounter == 0) && (storeCounter == 0) && buffer.isEmpty();
	}

	@Override
	public boolean isReadyToExcecute() {
		Buffer currentBuffer = null;
		for (Iterator iterator2 = buffer.iterator(); iterator2
				.hasNext();) {
			currentBuffer = (Buffer) iterator2.next();
			if (currentBuffer != null && !currentBuffer.isExcecuting()) {
				if (currentBuffer.getOp() == Constants.Opcode.ST) {
					return (currentBuffer.getJ().getState() == Constants.State.Value
							&& currentBuffer.getK().getState() == Constants.State.Value);
				} else if (currentBuffer.getOp() == Constants.Opcode.LD) {
					return (currentBuffer.getJ().getState() == Constants.State.Value);
				}
			} else {
				currentBuffer = null;
			}
		}
		return false;
	}

	@Override
	public void incClock() {
		prevLoadCounter = loadCounter;
		prevStoreCounter = storeCounter;
		
	}
}
