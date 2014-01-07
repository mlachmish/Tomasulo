import Constants.Constants;

public class Dock<T> {

	Constants.Opcode op;
	Register<T> j;
	Register<T> k;
	int instrNumber;
	private Instruction instruction;

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
}
