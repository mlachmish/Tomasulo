import java.util.ArrayList;
import Constants.Constants;
import java.util.Iterator;
import java.util.List;

public class FPRegistersContainer implements RegistersContainer<Float>{

	List<Register<Float>> registers;
	
	
	
	public FPRegistersContainer() {
		super();
		this.registers = new ArrayList<>(16);
		for (int i = 0; i < 16; i++) { //initial registers value are their indexs
			Register<Float> reg = new RegisterImpl<>(Constants.State.Value,(float) i, null, 0 );			
			this.registers.add(i, reg);			
		}
	}

	@Override
	public Iterator<Register<Float>> iterator() {
		Iterator<Register<Float>> registerIterator = registers.iterator();
		return registerIterator;
	}

	@Override
	public Register<Float> getRegister(int num) {
		return registers.get(num);
	}

}
