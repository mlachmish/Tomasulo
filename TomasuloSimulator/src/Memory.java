
public interface Memory {

	/**
	 * Get a Float value from the memory module
	 * @param address The Address
	 * @return A float value
	 */
	public float load(int address);
	
	/**
	 * Store the data in the memory at given address
	 * @param address The address to store in
	 * @param data The float value to store
	 */
	public void store(int address, float data);
	
	/**
	 * Fetch an instruction from the memory
	 * @param pc address to fetch from
	 * @return A parsed (Decoded) instruction
	 */
	public Instruction getInstruction(int pc);
	
	/**
	 * Get the data in given address without parsing
	 * @param address the address to read from
	 * @return The string as is
	 */
	public String getWordString(int address);
}
