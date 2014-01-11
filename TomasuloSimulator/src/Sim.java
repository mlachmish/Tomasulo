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

		Integer pc = 0;
		int instructionumber = 0;
		Instruction currentInstruction = null;
		Boolean halt = false;
		Boolean issued = true;
		while (!(halt && instructionQueue.isEmpty()
				&& reservationStationContainer.isDone())) {
			int clk = Clock.getClock();
			// // remove
			// if (pc > 16 || Clock.getClock() > 655)
			// break;

			// Write to CDB?
			reservationStationContainer.updateFromCDB();

			// Execute
			reservationStationContainer.excecute();

			// "decode"
			if (issued && !instructionQueue.isEmpty()) {
				currentInstruction = instructionQueue.poll();
				currentInstruction.setInstructionNumber(instructionumber++);
				issued = false;
			}

			if (currentInstruction != null) {
				Constants.Opcode opcode = currentInstruction.getOpcode();
				if (opcode == Constants.Opcode.HALT) {
					halt = true;					
					// Clock.incClock();
					//
					// break;
//					return -1;
				} else if (opcode == Constants.Opcode.BEQ
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
//							return 0;
						} else if ((opcode == Constants.Opcode.BEQ)
								&& (SRC0.getData() != SRC1.getData())) {
							// don't jump
							traces.add(currentInstruction);
							currentInstruction.setCycleIssued(Clock.getClock());
							currentInstruction.setCycleExcecuteStart(Clock
									.getClock());
							currentInstruction.setCycleWriteCDB(-1);
							issued = true;
//							return 0;

						} else if ((opcode == Constants.Opcode.BNE)
								&& (SRC0.getData() == SRC1.getData())) {
							// don't jump
							traces.add(currentInstruction);
							issued = true;
							currentInstruction.setCycleIssued(Clock.getClock());
							currentInstruction.setCycleExcecuteStart(Clock
									.getClock());
							currentInstruction.setCycleWriteCDB(-1);
//							return 0;
						} else {
							// JUMP!!!
							traces.add(currentInstruction);
							currentInstruction.setCycleIssued(Clock.getClock());
							currentInstruction.setCycleExcecuteStart(Clock
									.getClock());
							currentInstruction.setCycleWriteCDB(-1);
							pc += currentInstruction.getIMM() - 1; // -1 because
																	// pc
																	// jumps
																	// twice
																	// by the
																	// time
																	// we get
																	// here
							instructionQueue.clear();
							currentInstruction = null;
							issued = true;
							Clock.incClock();
							continue;
						}
					} else {
						// JUMP!!!
						traces.add(currentInstruction);
						currentInstruction.setCycleIssued(Clock.getClock());
						currentInstruction.setCycleExcecuteStart(Clock
								.getClock());
						currentInstruction.setCycleWriteCDB(-1);
						instructionQueue.clear();
						pc += currentInstruction.getIMM();
						currentInstruction = null;
						issued = true;
						Clock.incClock();
						continue;
					}
				} else {
					// Issue to reservation station
					issued = reservationStationContainer
							.issueInstruction(currentInstruction);
					if (issued) {
						traces.add(currentInstruction);
						currentInstruction.setCycleIssued(Clock.getClock());
					}
				}
			}

			if (issued && !halt) {
				// Fetch
				instructionQueue.add(memory.getInstruction(pc++));
			}

			Clock.incClock();
		}
		traces.add(currentInstruction);
		currentInstruction.setCycleIssued(Clock.getClock() - 1);
		currentInstruction.setCycleExcecuteStart(Clock
				.getClock() - 1);
		currentInstruction.setCycleWriteCDB(-1);
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
