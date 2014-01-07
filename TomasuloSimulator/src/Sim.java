import java.io.IOException;
import Constants.Constants;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

//import Constatns.Opcode;

public class Sim {

	public static ReservationStationContainer reservationStationContainer;
	public static RegistersContainer<Float> floatRegistersContainer;
	public static RegistersContainer<Integer> intRegistersContainer;
	public static Memory memory;
	public static Queue<Instruction> instructionQueue;
	public static List<Instruction> traces;

	public static void main(String[] args) {

		// Construct types
		instructionQueue = new LinkedList<Instruction>();
		traces = new LinkedList<>();
		floatRegistersContainer = new FPRegistersContainer();
		intRegistersContainer = new INTRegistersContainer();

		// Parse input
		try {
			Map<String, Integer> cfg = Parser.loadConfiguration(args[0]);
			memory = Parser.loadMemory(args[1]);
			reservationStationContainer = new ReservationStationContainerImpl(
					cfg);

		} catch (IOException e) {
			System.err.println("error reading from files");
			e.printStackTrace();
		}

		int pc = 0;
		int instructionumber = 0;
		Instruction currentInstruction = null;
		Boolean waitingToIssue = true;
		Boolean issued = true;
		while (true) {

			// "decode"
			if (issued && !instructionQueue.isEmpty()) {
				currentInstruction = instructionQueue.poll();
				currentInstruction.setInstructionNumber(instructionumber++);
			}
			// Fetch
			instructionQueue.add(memory.getInstruction(pc++));

			if (currentInstruction == null) {
				// must be first Instruction
				Clock.incClock();
				continue;
			}

			// traces
			traces.add(currentInstruction);

			// issue : check for branch, check if available RS
			Constants.Opcode opcode = currentInstruction.getOpcode();
			if (opcode == Constants.Opcode.HALT) {
				Clock.incClock();
				break;
			}
			if (opcode == Constants.Opcode.BEQ
					|| opcode == Constants.Opcode.BNE
					|| opcode.equals(Constants.Opcode.JUMP)) {

				// Jump if possible
				if (opcode != Constants.Opcode.JUMP) {
					Register SRC0 = intRegistersContainer
							.getRegister(currentInstruction.getSRC0());
					Register SRC1 = intRegistersContainer
							.getRegister(currentInstruction.getSRC1());
					if (SRC0.getState() == Constants.State.Queued
							|| SRC1.getState() == Constants.State.Queued) {
						// if we don't have values yet, continue to execute
						// don't jump
					} else if ((opcode == Constants.Opcode.BEQ)
							&& (SRC0.getData() == SRC1.getData())) {
						// don't jump
						issued = true;

					} else if ((opcode == Constants.Opcode.BNE)
							&& (SRC0.getData() != SRC1.getData())) {
						// don't jump
						issued = true;
					} else {
						// JUMP!!!
						pc += currentInstruction.getIMM();
						currentInstruction = null;
						issued = true;
						Clock.incClock();
						continue;
					}
				}
				// JUMP!!!
				pc += currentInstruction.getIMM();
				currentInstruction = null;
				issued = true;
				Clock.incClock();
				continue;
			}
			//

			// continue;
			issued = reservationStationContainer
					.issueInstruction(currentInstruction);

			if (issued) {

			}

			// Execute
			reservationStationContainer.excecute();
			// Write to CDB?
			// reservationStationContainer.updateQueuedFromCDB();

			Clock.incClock();

		}

		// Outputs
		try {
			Parser.createMemout(args[2]);
			Parser.createRegint(args[3]);
			Parser.createRegout(args[4]);
			Parser.createTrace(args[5]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
