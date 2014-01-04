import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FPRegistersContainer implements RegistersContainer<Float>{

	List<Register<Float>> registers;
	
	
	
	public FPRegistersContainer(List<Register<Float>> registers) {
		super();
		this.registers = new ArrayList<>(16);
		for (int i = 0; i < 15; i++) { //initial registers value are their indexs
			Register<Float> reg = new RegisterImpl<>();
			reg.setData((float)i);
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
