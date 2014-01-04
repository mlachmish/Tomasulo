public class Buffer {

	Constatns.Opcode op;
	Register<Integer> j;
	Register<Float> k;
	int immidiate;
	int instrNumber;

	public int getImmidiate() {
		return immidiate;
	}
	public void setImmidiate(int immidiate) {
		this.immidiate = immidiate;
	}

	public Constatns.Opcode getOp() {
		return op;
	}
	public void setOp(Constatns.Opcode op) {
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
		if (Constatns.Opcode.ST == op) {
			return j.getState() == Constatns.State.Value &&
					k.getState() == Constatns.State.Value;
		} 
		return j.getState() == Constatns.State.Value; 
	}

	public void emptyDock() {
		op = null;
		j = null;
		k = null;
		immidiate = 0;
		instrNumber = -1;
	}
}
