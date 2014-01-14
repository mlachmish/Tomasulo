/**
 *This is the interface for the object that holds all registers in the Tomasulo simulator
 * @see Register
 */
public interface RegistersContainer<T> extends Iterable<Register<T>>{

	/**
	 * @param num Index of register
	 * @return The register consisted with this index
	 */
	public Register<T> getRegister(int num);
}