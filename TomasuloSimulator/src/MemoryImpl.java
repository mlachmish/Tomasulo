public class MemoryImpl implements Memory {

	private String[] memoryArray;

	public MemoryImpl(String[] memoryArr) {
		this.memoryArray = memoryArr;
	}

	@Override
	public float load(int address) {
		
		Long i = Long.parseLong(memoryArray[address], 16);
        return  Float.intBitsToFloat(i.intValue());		
	}

	@Override
	public void store(int address, float data) {
		memoryArray[address] = Integer.toHexString(Float.floatToIntBits(data));
	}

	@Override
	public Instruction getInstruction(int pc) {
		String instructionString = memoryArray[pc];

		Instruction instruction = new InstructionImpl(instructionString);

		return instruction;
	}

	@Override
	public String getWordString(int address) {
		return memoryArray[address];
	}

}
