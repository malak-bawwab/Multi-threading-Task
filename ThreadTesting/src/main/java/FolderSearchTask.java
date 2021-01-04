import java.io.File;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class FolderSearchTask extends RecursiveTask<AtomicIntegerArray> {

	private static final long serialVersionUID = 1L;

	private File file;

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

				} else {
					FileSearchTask task = new FileSearchTask(content[i]);
					task.fork();

					tasks.put(key++, task);
				

				}
			}
		}

		if (tasks.size() > 50) {
			System.out.printf("%s: %d tasks ran.\n", file.getAbsolutePath(), tasks.size());
		}

		addResultsFromTasks(count, tasks);

		return count;
	}

	private void addResultsFromTasks(AtomicIntegerArray count,
			ConcurrentHashMap<Integer, RecursiveTask<AtomicIntegerArray>> tasks) {

		Iterator<Entry<Integer, RecursiveTask<AtomicIntegerArray>>> itr = tasks.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<Integer, RecursiveTask<AtomicIntegerArray>> entry = itr.next();

			for (int index = 0; index < 26; index++) {
				int newValue = entry.getValue().join().get(index);
				count.getAndAdd(index, newValue);

			}

		}

	}

}