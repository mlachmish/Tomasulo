import Constants.Constants;

/**
 *Represent an instruction in the Tomasulo model
 */
public interface Instruction {

	/**
	 * @return instruction opcode
	 */
	public Constants.Opcode getOpcode();
	
	/**
	 * 
	 * @return instruction destination
	 */
	public int getDST();

	/**
	 * 
	 * @return source0 register
	 */
	public int getSRC0();
	
	/**
	 * 
	 * @return source 1 register
	 */
	public int getSRC1();
	
	/**
	 * 
	 * @return immediate 
	 */
	public int getIMM();

	//For trace use
	@Override
	public String toString();

	/**
	 * @return the number of the instruction in the exectution
	 */
	public int getInstructionNumber();
	public void setInstructionNumber(int number );
	
	public int getCycleIssued();
	public void setCycleIssued(int cycle);
	
	public int getCycleExcecuteStart();
	public void setCycleExcecuteStart(int cycle);

	public int getCycleWriteCDB();
	public void setCycleWriteCDB(int cycle);
}