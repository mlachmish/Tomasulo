package Constants;

public class Constants {
	
	public enum ReservationStationNames {
		FPADD,
		FPMULT,
		INTADD,
		LDST;
	}

	public enum State {
		Value,
		Queued;
	}
	
	public enum Opcode {
		LD,
		ST,
		JUMP,
		BEQ,
		BNE,
		ADD,
		ADDI,
		SUB,
		SUBI,
		ADDS,
		SUBS,
		MULTS,
		HALT;
	}
}
