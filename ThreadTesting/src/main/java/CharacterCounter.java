
import java.io.*;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class CharacterCounter {

	private AtomicIntegerArray charactersCount;

	public CharacterCounter() {
	
		charactersCount = new AtomicIntegerArray(26);
	}

	AtomicIntegerArray occurrencesCount(File file) {

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			int character = 0;
			while ((character = reader.read()) != -1) {
				computeCount(character);
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return charactersCount;
	}

	private AtomicIntegerArray computeCount(int character) {
		if (character > 96 && character < 123) {
	
			charactersCount.getAndIncrement(character - 97);

		}
		return charactersCount;
	}
}
