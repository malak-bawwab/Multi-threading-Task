package characterscounter;

import java.io.*;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * This class will read the file and count the small characters and store the
 * count for each char in AtomicIntegerArray charsCountArray.
 * 
 * @author Malak
 *
 */

public class FilesReader {

	private AtomicIntegerArray charsCountArray;

	public FilesReader() {

		charsCountArray = new AtomicIntegerArray(26);
	}

	/**
	 * Read the passed file line by line and for each line call the
	 * updateCharsCountArray.
	 *
	 * @param file file to read.
	 */
	void readFile(File file) {
		AtomicIntegerArray tempCharsCountArray = new AtomicIntegerArray(26);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;

			while ((line = reader.readLine()) != null) {
				updateCharsCountArray(line, tempCharsCountArray);
			}

			charsCountArray = tempCharsCountArray;
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

	/**
	 * Update charsCountArray by incrementing the occurrence in case the passed
	 * character is a small character.
	 */

	private void updateCharsCountArray(String line, AtomicIntegerArray tempCharsCountArray) {
		line.codePoints().parallel().filter(c -> c >= 'a' && c <= 'z')
				.forEach(e -> tempCharsCountArray.getAndIncrement(e - 97));

	}

	/**
	 * This method returns the charsCountArray.
	 * 
	 * @return AtomicIntegerArray that contains the occurrence of small characters
	 *         of this file.
	 */
	public AtomicIntegerArray getCharsCountArray() {
		return charsCountArray;
	}
}
