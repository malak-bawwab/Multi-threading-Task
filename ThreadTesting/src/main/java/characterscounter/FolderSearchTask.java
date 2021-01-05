package characterscounter;

import java.io.File;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.stream.IntStream;

/**
 * This class represent FolderSearchTask that divide folder task into subfolders
 * tasks and file tasks.
 * 
 * @author Malak
 *
 */

public class FolderSearchTask extends RecursiveTask<AtomicIntegerArray> {

	private static final long serialVersionUID = 1L;

	private File file;

	FolderSearchTask(File file) {
		this.file = file;

	}

	/**
	 *
	 * This method simply forks file and folder tasks for each element of the folder
	 * that it has been passed through its constructor and add them to
	 * ConcurrentHashMap tasks.
	 *
	 * @param content the folder
	 * @param tasks ConcurrentHashMap to put tasks on it
	 * 
	 */
	private void addTasks(File content[], ConcurrentHashMap<Integer, RecursiveTask<AtomicIntegerArray>> tasks) {

		if (content != null) {
			for (int i = 0; i < content.length; i++) {
				if (content[i].isDirectory()) {
					FolderSearchTask task = new FolderSearchTask(new File(content[i].getAbsolutePath()));
					task.fork();
					tasks.put(i, task);

				}

				else {

					FileSearchTask task = new FileSearchTask(content[i]);
					task.fork();

					tasks.put(i, task);

				}
			}
		}
	}

	/**
	 * Implement compute method that returns the final result.
	 *
	 * @return AtomicIntegerArray that contains the occurrence of small characters of all files.
	 */
	@Override
	protected AtomicIntegerArray compute() {
		AtomicIntegerArray count = new AtomicIntegerArray(26);
		ConcurrentHashMap<Integer, RecursiveTask<AtomicIntegerArray>> tasks = new ConcurrentHashMap<>();

		File content[] = file.listFiles();
		addTasks(content, tasks);

		addResultsFromTasks(count, tasks);

		return count;
	}

	/**
	 *
	 * For each task stored in the map of tasks, call the join() method that will
	 * wait for its finalization and then will return the result of the task. Add
	 * that result to the count array .
	 * 
	 * @param count AtomicIntegerArray to store join results.
	 * @param tasks ConcurrentHashMap that contains tasks.
	 * 
	 */
	private void addResultsFromTasks(AtomicIntegerArray count,
			ConcurrentHashMap<Integer, RecursiveTask<AtomicIntegerArray>> tasks) {
		tasks.entrySet().parallelStream().forEach(
				e -> IntStream.range(0, 26).forEach(index -> count.getAndAdd(index, e.getValue().join().get(index))));
	}

}