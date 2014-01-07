import Constants.Constants;

public class InstructionImpl implements Instruction {
	private int SRC0;
	private int SRC1;
	private int DST;
	private int IMM;
	private Constants.Opcode opcode;
	
	//By issue order
	private int InstructionNumber ;
	// Traces
	private int CycleIsuued;
	private int CycleExecuteStart;
	private int CycleWriteCdb;
	
	private String instructionsString;

	public InstructionImpl(String intructionsString)
	{
		short opcodeNum = Short.parseShort(intructionsString.substring(0, 1),  16);
		this.opcode = Constants.Opcode.values()[opcodeNum];
		
		this.DST = Short.parseShort(intructionsString.substring(1, 2),  16);
		this.SRC0 = Short.parseShort(intructionsString.substring(2, 3), 16);
		this.SRC1 = Short.parseShort(intructionsString.substring(3, 4), 16);
		this.IMM =(short) Integer.parseInt(intructionsString.substring(4,8), 16);
		this.instructionsString = intructionsString;
	}
	
	@Override
	public String toString(){
		return String.format("%s %d %d %d\n", instructionsString, CycleIsuued, CycleExecuteStart, CycleWriteCdb);
	}
	
	@Override
	public Constants.Opcode getOpcode() {
		return opcode;
	}

	@Override
	public int getDST() {
		return DST;
	}

	@Override
	public int getSRC0() {
		return SRC0;
	}

	@Override
	public int getSRC1() {
		return SRC1;
	}

	@Override
	public int getIMM() {
		return IMM;
	}

	@Override
	public int getInstructionNumber() {
		return InstructionNumber;
	}

	@Override
	public int getCycleIssued() {
		return CycleIsuued;
	}

	@Override
	public void setCycleIssued(int cycle) {
		this.CycleIsuued = cycle;
	}

	@Override
	public int getCycleExcecuteStart() {
		return this.CycleExecuteStart;
	}

	@Override
	public void setCycleExcecuteStart(int cycle) {
		this.CycleExecuteStart = cycle;		
	}

	@Override
	public int getCycleWriteCDB() {
		return CycleWriteCdb;
	}

	@Override
	public void setCycleWriteCDB(int cycle) {
		this.CycleWriteCdb = cycle;		
	}

	@Override
	public void setInstructionNumber(int number) {
		this.InstructionNumber = number;	
	}

}
