public class Dock<T> {

	Constatns.Opcode op;
	Register<T> j;
	Register<T> k;
	int instrNumber;

	public Dock() {
		emptyDock();
	}

	public Constatns.Opcode getOp() {
		return op;
	}

	public void setOp(Constatns.Opcode op) {
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
		return j.getState() == Constatns.State.Value
				&& k.getState() == Constatns.State.Value;
	}

	public void emptyDock() {
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
}
