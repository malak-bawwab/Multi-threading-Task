import java.io.File;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.stream.IntStream;

public class FolderSearchTask extends RecursiveTask<AtomicIntegerArray> {

	private static final long serialVersionUID = 1L;

	private File file;
	public static int NumberOfFiles = 0;

	FolderSearchTask(File file) {
		this.file = file;

	}

	@Override
	protected AtomicIntegerArray compute() {
		AtomicIntegerArray count = new AtomicIntegerArray(26);
		ConcurrentHashMap<Integer, RecursiveTask<AtomicIntegerArray>> tasks = new ConcurrentHashMap<>();

		int key = 0;

		File content[] = file.listFiles();
		if (content != null) {
			for (int i = 0; i < content.length; i++) {
				if (content[i].isDirectory()) {
					FolderSearchTask task = new FolderSearchTask(new File(content[i].getAbsolutePath()));
					task.fork();
					tasks.put(key++, task);

				}

				else {

					FileSearchTask task = new FileSearchTask(content[i]);
					task.fork();

					tasks.put(key++, task);

				}
			}
		}

		NumberOfFiles += tasks.size() - 1;
		addResultsFromTasks(count, tasks);

		return count;
	}

	private void addResultsFromTasks(AtomicIntegerArray count,
			ConcurrentHashMap<Integer, RecursiveTask<AtomicIntegerArray>> tasks) {
		tasks.entrySet().stream().forEach(
				e -> IntStream.range(0, 26).forEach(index -> count.getAndAdd(index, e.getValue().join().get(index))));
	}

	public int getNumberOfFiles() {

		return NumberOfFiles;
	}

}