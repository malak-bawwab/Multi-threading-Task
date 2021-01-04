
import java.io.*;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class FilesReader {

	private AtomicIntegerArray charsCountArray;

	public FilesReader() {

		charsCountArray = new AtomicIntegerArray(26);
	}

	void readFile(File file) {

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			int character = 0;
		
			while ((character = reader.read()) != -1) {
				updateCharsCountArray(character);
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

		
	}

	public AtomicIntegerArray getCharsCountArray() {
		return charsCountArray;
	}

	private void updateCharsCountArray(int character) {
		if (character > 96 && character < 123) {

			charsCountArray.getAndIncrement(character - 97);

		}

	}
}
