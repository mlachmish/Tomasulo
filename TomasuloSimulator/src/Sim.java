import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;



//import Constatns.Opcode;


public  class Sim {

	static ReservationStationContainer reservationStationContainer ;
	static RegistersContainer<Float>  floatRegistersContainer;
	static RegistersContainer<Integer> intRegistersContainer;
	static Memory memory ;
	static Queue<Instruction> instructionQueue;
	


	
	
	public static void main(String[] args) {
		//Construct types
		instructionQueue = new LinkedList<Instruction>();
		
		//Parse input
		try {
			
			reservationStationContainer.init(Parser.loadConfiguration(args[0]));
			memory = Parser.loadMemory(args[1]);
			
		} catch (IOException e) {
			System.err.println("error reading from files");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		int pc = 0;
		while (true)
		{
			//Fetch
			instructionQueue.add(memory.getInstruction(pc));
			
			
			//"decode"
			Instruction currentInstruction =instructionQueue.peek();
			if (currentInstruction == null) {
				//must be first Instruction
				continue;
			}
			
			//issue : check for branch, check if available RS
			Constatns.Opcode opcode = currentInstruction.getOpcode();
			if (opcode == Constatns.Opcode.HALT)
			{
				break;			
			}
			if (opcode == Constatns.Opcode.BEQ 
					||  opcode == Constatns.Opcode.BNE  
					|| opcode.equals(Constatns.Opcode.JUMP))
			{
				//Jump if possible
				instructionQueue.poll();
				continue;
			}			
			// we 
			instructionQueue.poll();
			reservationStationContainer.issueInstraction(currentInstruction);
			
			//Execute
			reservationStationContainer.excecute();
			//Write to CDB?
			reservationStationContainer.updateQueuedFromCDB();
			
			Clock.incClock();		}
		
		
	}
}
