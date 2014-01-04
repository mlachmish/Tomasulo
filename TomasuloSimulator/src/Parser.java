import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Parser {

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

	public static void createMemout(String path) {
		// TODO: add implementation
	}

	public static void createRegint(String path) {
		// TODO: add implementation
	}

	public static void createRegout(String path) {
		// TODO: add implementation
	}

	public static void createTrace(String path) {
		// TODO: add implementation
	}
}
