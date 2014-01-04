import java.io.IOException;


public  class Sim {

	static ReservationStationContainer reservationStationContainer ;
	static RegistersContainer<Float>  floatRegistersContainer;
	static RegistersContainer<Integer> intRegistersContainer;
	static Memory memory ;


	
	
	public static void main(String[] args) {
		//Construct types
		
		
		//Parse input
		try {
			
			reservationStationContainer.init(Parser.loadConfiguration(args[0]));
			memory = Parser.loadMemory(args[1]);
			
		} catch (IOException e) {
			System.err.println("error reading from files");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Boolean halt = false;
		int pc = 0;
		while (!halt)
		{
			//get instruction
			Instruction currentInstruction = memory.getInstruction(pc);
			
			//issue : check for branch, check if avavilable RS
			if (currentInstruction.getOpcode().)
			//Execute
			
			//Write to CDB?
			
		}
		
		
	}
}
