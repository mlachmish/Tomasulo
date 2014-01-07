import Constants.Constants;

public interface Instruction {

	public Constants.Opcode getOpcode();
	
	public int getDST();

	public int getSRC0();
	
	public int getSRC1();
	
	public int getIMM();

	//For trace use
	@Override
	public String toString();

	public int getInstructionNumber();
	public void setInstructionNumber(int number );
	
	public int getCycleIssued();
	public void setCycleIssued(int cycle);
	
	public int getCycleExcecuteStart();
	public void setCycleExcecuteStart(int cycle);

	public int getCycleWriteCDB();
	public void setCycleWriteCDB(int cycle);
}
