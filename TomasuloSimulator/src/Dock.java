import Constants.Constants;

public class Dock<T> {

	private Constants.Opcode op;
	private Register<T> j;
	private Register<T> k;
	private int instrNumber;
	private Instruction instruction;
	private int excecutionStartTime;
	private int issueTime;

	public Dock() {
		emptyDock();
	}

	public Constants.Opcode getOp() {
		return op;
	}

	public void setOp(Constants.Opcode op) {
		this.op = op;
	}

	public Register<?> getJ() {
		return j;
	}

	public void setJ(Register<T> j) {
		this.j = j;
	}

	public Register<?> getK() {
		return k;
	}

	public void setK(Register<T> k) {
		this.k = k;
	}

	public int getInstrNumber() {
		return instrNumber;
	}

	public void setInstrNumber(int instrNumber) {
		this.instrNumber = instrNumber;
	}

	public int getExcecutionStartTime() {
		return excecutionStartTime;
	}

	public void setExcecutionStartTime(int excecutionStartTime) {
		this.excecutionStartTime = excecutionStartTime;
	}

	public boolean isReady() {
		if (isEmpty()) return false;
		if (instruction.getOpcode() == Constants.Opcode.ADDI ||
				instruction.getOpcode() == Constants.Opcode.SUBI
				|| instruction.getOpcode() == Constants.Opcode.LD)
		{
			return j.getState() == Constants.State.Value;
		}
		return j.getState() == Constants.State.Value
				&& k.getState() == Constants.State.Value;
	}

	public void emptyDock() {
		setInstruction(null);
		op = null;
		j = null;
		k = null;
		instrNumber = -1;
		excecutionStartTime = -1;
		issueTime = Clock.getClock();
	}

	public Boolean isEmpty()
	{
		if ((op == null ) && (j == null) && (k == null))
		{
			return true;
		}
		else return false;
	}

	public Instruction getInstruction() {
		return instruction;
	}

	public void setInstruction(Instruction instruction) {
		this.instruction = instruction;
	}
	
	public boolean isExcecuting() {
		return excecutionStartTime != -1;
	}

	public boolean isReadyForIssue() {		
		return ((Clock.getClock() > issueTime ) &&  isEmpty());
	}
}
