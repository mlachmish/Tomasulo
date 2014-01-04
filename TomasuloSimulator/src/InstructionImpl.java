

public class InstructionImpl implements Instruction {
	private int SRC0;
	private int SRC1;
	private int DST;
	private int IMM;
	private Constatns.Opcode opcode;
	
	//By issue order
	private int InstructionNumber ;
	// Traces
	private int CycleIsuued;
	private int CycleExecuteStart;
	private int CycleWriteCdb;

	public InstructionImpl(String intructionsString)
	{
		short opcodeNum = Short.parseShort(intructionsString.substring(0, 1),  16);
		this.opcode = Constatns.Opcode.values()[opcodeNum];
	}
	
	@Override
	public Constatns.Opcode getOpcode() {
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
