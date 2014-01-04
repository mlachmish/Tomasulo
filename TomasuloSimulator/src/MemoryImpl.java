
public class MemoryImpl implements Memory {

	private String[] memoryArray ; 
	
	public MemoryImpl(String[] memoryArr) {
		this.memoryArray = memoryArr;
	}

	@Override
	public float load(int address) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void store(int address, float data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Instruction getInstruction(int pc) {
		String instructionString = memoryArray[pc]; 
		
		Instruction instruction = new InstructionImpl(instructionString);
		
		return instruction;
	}

}
