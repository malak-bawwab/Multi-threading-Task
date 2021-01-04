import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.concurrent.ForkJoinPool;

import java.util.concurrent.atomic.AtomicIntegerArray;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CharacterCounterTest {
	private static ForkJoinPool forkJoinPool;
	private static FolderSearchTask folder;

	@BeforeAll
	public static void init() {
		int numOfCores = Runtime.getRuntime().availableProcessors();

		ClassLoader loader = CharacterCounterTest.class.getClassLoader();
		folder = new FolderSearchTask(new File(loader.getResource("test1").getPath()));
		forkJoinPool = new ForkJoinPool(numOfCores);

		forkJoinPool.execute(folder);

	}

	@Test
	public void testFilesReader() {

		while ((!folder.isDone()))
			;

		AtomicIntegerArray results;
		results = folder.join();

		// There are 4333 a/4333 b/4332 c/4330 d in the files.
		assertEquals(results.get(0), 4333);
		assertEquals(results.get(1), 4333);
		assertEquals(results.get(2), 4332);
		assertEquals(results.get(3), 4330);
		for (int i = 4; i < 26; i++) {

			assertEquals(results.get(i), 0);
		}

	}

	@Test
	public void testFilesCollector() {

		while ((!folder.isDone()))
			;

		assertEquals(4332, folder.getNumberOfFiles());
	}

	@AfterAll
	public static void tearDownAll() {
		forkJoinPool.shutdown();

	}

}
