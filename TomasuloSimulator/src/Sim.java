import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

//import Constatns.Opcode;

public class Sim {

	public static ReservationStationContainer reservationStationContainer;
	public static RegistersContainer<Register<Float>> floatRegistersContainer;
	public static RegistersContainer<Register<Integer>> intRegistersContainer;
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
		Instruction currentInstruction;
		Boolean waitingToIssue = true;
		while (true) {

			// "decode"
			currentInstruction = instructionQueue.poll();
			

			// Fetch
			instructionQueue.add(memory.getInstruction(pc++));
			
			if (currentInstruction == null) {
				// must be first Instruction
				continue;
			}
			currentInstruction.setInstructionNumber(instructionumber++);

			// issue : check for branch, check if available RS
			Constatns.Opcode opcode = currentInstruction.getOpcode();
			if (opcode == Constatns.Opcode.HALT) {
				break;
			}
			if (opcode == Constatns.Opcode.BEQ
					|| opcode == Constatns.Opcode.BNE
					|| opcode.equals(Constatns.Opcode.JUMP)) {
				// Jump if possible
				
				traces.add(currentInstruction);
				continue;
			}
			//

			// traces
			traces.add(currentInstruction);
			continue;
			// Boolean issued =
			// reservationStationContainer.issueInstraction(currentInstruction);
			//
			// if (issued)
			// {
			// instructionQueue.poll();
			// }
			//
			// // Execute
			// reservationStationContainer.excecute();
			// // Write to CDB?
			// //reservationStationContainer.updateQueuedFromCDB();
			//
			// Clock.incClock();

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
