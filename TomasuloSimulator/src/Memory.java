
public interface Memory {

	public float load(int address);
	
	public void store(int address, float data);
	
	public Instruction getInstruction(int pc);
	
	public String getWordString(int address);
}
