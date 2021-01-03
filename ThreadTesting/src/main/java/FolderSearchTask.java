import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class FolderSearchTask extends RecursiveTask<AtomicIntegerArray> {

	private static final long serialVersionUID = 1L;

	private final File file;

	FolderSearchTask(File file) {
		this.file = file;

	}

	@Override
	protected AtomicIntegerArray compute() {
		AtomicIntegerArray count = new AtomicIntegerArray(26);

		List<RecursiveTask<AtomicIntegerArray>> tasks = new ArrayList<>();

		File content[] = file.listFiles();
		if (content != null) {
			for (int i = 0; i < content.length; i++) {
				if (content[i].isDirectory()) {
					FolderSearchTask task = new FolderSearchTask(new File(content[i].getAbsolutePath()));
					task.fork();
					tasks.add(task);
				} else {
					FileSearchTask task = new FileSearchTask(content[i]);
					task.fork();
					tasks.add(task);

				}
			}
		}

		if (tasks.size() > 50) {
			System.out.printf("%s: %d tasks ran.\n", file.getAbsolutePath(), tasks.size());
		}

		addResultsFromTasks(count, tasks);

		return count;
	}

	private void addResultsFromTasks(AtomicIntegerArray count, List<RecursiveTask<AtomicIntegerArray>> tasks) {
		for (RecursiveTask<AtomicIntegerArray> item : tasks) {
			for (int index = 0; index < 26; index++) {
				int newValue = item.join().get(index);
				count.getAndAdd(index, newValue);

			}

		}
	}

}