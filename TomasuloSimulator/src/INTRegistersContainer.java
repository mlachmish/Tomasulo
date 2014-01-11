import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import Constants.Constants;

public class INTRegistersContainer implements RegistersContainer<Integer>{

	List<Register<Integer>> registers;
	
	public INTRegistersContainer() {
		super();
		this.registers = new ArrayList<>(16);
		for (int i = 0; i < 16; i++) { //initial registers value are 0
			Register<Integer> reg = new RegisterImpl<>(Constants.State.Value, 0, null, 0 );
			this.registers.add(i, reg);
			
		}
	}

	@Override
	public Iterator<Register<Integer>> iterator() {
		Iterator<Register<Integer>> registerIterator = registers.iterator();
		return registerIterator;
	}

	@Override
	public Register<Integer> getRegister(int num) {
		return registers.get(num);
	}
	
}
