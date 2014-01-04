import java.io.IOException;
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
		// Parse input
		try {
			Map<String, Integer> cfg = Parser.loadConfiguration(args[0]);
			memory = Parser.loadMemory(args[1]);

			// reservationStationContainer.init();

		} catch (IOException e) {
			System.err.println("error reading from files");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int pc = 0;
		int instructionumber = 0;
		Instruction currentInstruction = null;
		Boolean waitingToIssue = true;
		Boolean issued = true;
		while (true) {

			// "decode"
			if (issued) {
				currentInstruction = instructionQueue.poll();
			}
			// Fetch
			instructionQueue.add(memory.getInstruction(pc++));

			if (currentInstruction == null) {
				// must be first Instruction
				continue;
			}
			currentInstruction.setInstructionNumber(instructionumber++);
			issued = false;

			// traces
			traces.add(currentInstruction);

			// issue : check for branch, check if available RS
			Constatns.Opcode opcode = currentInstruction.getOpcode();
			if (opcode == Constatns.Opcode.HALT) {
				break;
			}
			if (opcode == Constatns.Opcode.BEQ
					|| opcode == Constatns.Opcode.BNE
					|| opcode.equals(Constatns.Opcode.JUMP)) {

				// Jump if possible
				if (opcode != Constatns.Opcode.JUMP) {
					Register SRC0 = intRegistersContainer
							.getRegister(currentInstruction.getSRC0());
					Register SRC1 = intRegistersContainer
							.getRegister(currentInstruction.getSRC1());
					if (SRC0.getState() == Constatns.State.Queued
							|| SRC1.getState() == Constatns.State.Queued) {
						continue;
					}
					if ((opcode == Constatns.Opcode.BEQ)
							&& (SRC0.getData() == SRC1.getData())) {
						issued = true;
						continue;
					}
					if ((opcode == Constatns.Opcode.BNE)
							&& (SRC0.getData() != SRC1.getData())) {
						issued = true;
						continue;
					}
				}
				// JUMP!!!
				pc += currentInstruction.getIMM();
				currentInstruction = null;
				issued = true;
				continue;
			}
			//

			// continue;
			issued = reservationStationContainer
					.issueInstraction(currentInstruction);

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
