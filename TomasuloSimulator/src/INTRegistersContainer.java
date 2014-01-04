import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class INTRegistersContainer implements RegistersContainer<Integer>{

	List<Register<Integer>> registers;
	
	public INTRegistersContainer(List<Register<Integer>> registers) {
		super();
		this.registers = new ArrayList<>(16);
		for (int i = 0; i < 15; i++) { //initial registers value are their indexs
			Register<Integer> reg = new RegisterImpl<>();
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