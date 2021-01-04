import java.io.File;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class CharacterCounter {

	public static void main(String[] args) throws Exception {

		if (args.length == 0) {
			throw new IllegalArgumentException("Please provide a path for the folder");
		}
		int numOfCores = Runtime.getRuntime().availableProcessors();

		ForkJoinPool forkJoinPool = new ForkJoinPool(numOfCores);

		FolderSearchTask folder = new FolderSearchTask(new File(args[0]));

		forkJoinPool.execute(folder);

		do {
			System.out.printf("******************************************\n");
			System.out.printf("Pool Size: %d\n", forkJoinPool.getPoolSize());
			System.out.printf("Main: Parallelism: %d\n", forkJoinPool.getParallelism());
			System.out.printf("Main: Active Threads: %d\n", forkJoinPool.getActiveThreadCount());
			System.out.printf("Main: Task Count: %d\n", forkJoinPool.getQueuedTaskCount());
			System.out.printf("Main: Steal Count: %d\n", forkJoinPool.getStealCount());
			System.out.printf("******************************************\n");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while ((!folder.isDone()));

		forkJoinPool.shutdown();

		AtomicIntegerArray results;
		results = folder.join();

		for (int i = 0; i < 26; i++) {

			char c = (char) (i + 97);

			System.out.println(c + "\t" + results.get(i));
		}

	}
}
