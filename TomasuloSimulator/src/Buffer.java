import Constants.Constants;
import Constants.Constants.Opcode;

public class Buffer {

	Constants.Opcode op;
	Register<Integer> j;
	Register<Float> k;
	int immidiate;
	int instrNumber;
	private Instruction instruction;

	public Buffer(Opcode op, Register<Integer> j, Register<Float> k,
			int immidiate, int instrNumber, Instruction instruction) {
		super();
		this.op = op;
		this.j = j;
		this.k = k;
		this.immidiate = immidiate;
		this.instrNumber = instrNumber;
		this.instruction = instruction;
	}
	public int getImmidiate() {
		return immidiate;
	}
	public void setImmidiate(int immidiate) {
		this.immidiate = immidiate;
	}

	public Constants.Opcode getOp() {
		return op;
	}
	public void setOp(Constants.Opcode op) {
		this.op = op;
	}
	public Register<Integer> getJ() {
		return j;
	}
	public void setJ(Register<Integer> j) {
		this.j = j;
	}
	public Register<Float> getK() {
		return k;
	}
	public void setK(Register<Float> k) {
		this.k = k;
	}
	public int getInstrNumber() {
		return instrNumber;
	}
	public void setInstrNumber(int instrNumber) {
		this.instrNumber = instrNumber;
	}


	public boolean isReady() {
		if (Constants.Opcode.ST == op) {
			return j.getState() == Constants.State.Value &&
					k.getState() == Constants.State.Value;
		} 
		return j.getState() == Constants.State.Value; 
	}

	public void emptyDock() {
		op = null;
		j = null;
		k = null;
		immidiate = 0;
		instrNumber = -1;
	}
	public Instruction getInstruction() {
		return instruction;
	}
	public void setInstruction(Instruction instruction) {
		this.instruction = instruction;
	}
}
