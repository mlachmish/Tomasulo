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

	public static Memory loadMemory(String meminInputFile) {
		return null;
		// TODO: add implementation
	}

	public static void createMemout() {
		// TODO: add implementation
	}

	public static void createRegint() {
		// TODO: add implementation
	}

	public static void createRegout() {
		// TODO: add implementation
	}

	public static void createTrace() {
		// TODO: add implementation
	}
}
