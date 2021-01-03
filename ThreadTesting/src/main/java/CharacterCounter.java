
import java.util.stream.Collectors;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class CharacterCounter {
	private final ForkJoinPool forkJoinPool = new ForkJoinPool();

	private int[] charsCountArray = new int[26];

	int[] occurrencesCount(File file) {

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

		return charsCountArray;
	}

	int[] countOccurrencesInParallel(Folder folder) {
		return forkJoinPool.invoke(new FolderSearchTask(folder));
	}

	private void computeCount(int character) {
		if (character > 96 && character < 123) {
			// 97 = a = first index in the array = 0
			charsCountArray[character - 97] = ++charsCountArray[character - 97];

		}
	}
}

class FileSearchTask extends RecursiveTask<int[]> {
	private final File file;
	private CharacterCounter characterCounter = new CharacterCounter();

	FileSearchTask(File file) {
		super();
		this.file = file;
	}

	@Override
	protected int[] compute() {
		return characterCounter.occurrencesCount(file);
	}

}

class FolderSearchTask extends RecursiveTask<int[]> {
	private final Folder folder;

	FolderSearchTask(Folder folder) {
		super();
		this.folder = folder;
	}

	@Override
	protected int[] compute() {
		int[] count = new int[26];
		ArrayList<RecursiveTask<int[]>> forks = new ArrayList<>();
		for (Folder subFolder : folder.getSubFolders()) {
			FolderSearchTask task = new FolderSearchTask(subFolder);
			forks.add(task);
			task.fork();
		}
		for (File file : folder.getFiles()) {
			FileSearchTask task = new FileSearchTask(file);
			forks.add(task);
			task.fork();
		}
		for (RecursiveTask<int[]> task : forks) {
			int c[] = task.join();

			for (int index = 0; index < 26; index++) {

				count[index] = count[index] + c[index];
			}
		}
		return count;
	}
}
