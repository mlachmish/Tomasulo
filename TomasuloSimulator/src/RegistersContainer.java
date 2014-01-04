
public interface RegistersContainer<T> extends Iterable<Register<T>>{

	public Register<T> getRegister(int num);
}
