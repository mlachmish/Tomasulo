import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Parser {

	/**
	 * Parse the configuration file
	 */
	public static Map<String, Integer> loadConfiguration(String cfgInputFile) throws IOException {
		BufferedReader reader;

		reader = new BufferedReader(new FileReader(cfgInputFile));
		Map<String, Integer> configuratios = new HashMap<>();
		for (int i = 0; i < 9; i++) {
			String line = reader.readLine();
			String[] keyValue = line.split(" = ");
			configuratios.put(keyValue[0], Integer.parseInt(keyValue[1]));
			
		}
		reader.close();
		return configuratios;
	}

	/**
	 * Read the memin file to memory
	 * @param path the path to file
	 * @return A new Memory module
	 */
	public static Memory loadMemory(String path) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String[] memoryArr = new String[1024];
		String currentLine;
		for (int i = 0; i < 1024 ; i++) {
			currentLine = reader.readLine().trim();
			memoryArr[i] = currentLine;
		}		
		return new MemoryImpl(memoryArr);

	}

	/**
	 * Write the memout file from the memory module
	 * @param path Where to write to
	 */
	public static void createMemout(String path) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		Memory mem = Sim.memory;
		for (int i = 0; i < 1024 ; i++) {						
			writer.write( mem.getWordString(i) + "\n");
		}
		writer.close();
	}

	/**
	 * write the integer registers file
	 */
	public static void createRegint(String path) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		for (int i = 0; i < 16; i++) {
			writer.write(Sim.intRegistersContainer.getRegister(i).getData() + "\n");
		}
		writer.close();
	}

	/**
	 * write the float registers file
	 */
	public static void createRegout(String path) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		for (int i = 0; i < 16; i++) {
			writer.write(Sim.floatRegistersContainer.getRegister(i).getData() + "\n");
		}
		writer.close();
	}

	/**
	 * Write trace file
	 */
	public static void createTrace(String path) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		for (Instruction trace : Sim.traces) {
			writer.write(trace.toString());
		}
		writer.close();
	}
}
