import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.concurrent.ForkJoinPool;

import java.util.concurrent.atomic.AtomicIntegerArray;

import org.junit.jupiter.api.Test;

class CharacterCounterTest {

	@Test
	public void testFilesReader() {
		ClassLoader loader = CharacterCounterTest.class.getClassLoader();
		
		FolderSearchTask folder = new FolderSearchTask(new File(loader.getResource("test1").getPath()));
		int numOfCores = Runtime.getRuntime().availableProcessors();

		ForkJoinPool forkJoinPool = new ForkJoinPool(numOfCores);


		forkJoinPool.execute(folder);

		 while ((!folder.isDone()));

		forkJoinPool.shutdown();

		AtomicIntegerArray results;
		results = folder.join();
		

		// There are  4333 a in the files.
		assertEquals(results.get(0), 4333);
		assertEquals(results.get(1), 4333);
		assertEquals(results.get(2), 4332);
		assertEquals(results.get(3), 4330);
		for (int i = 4; i < 26; i++) {

			assertEquals(results.get(i), 0);
		}
		
	}

}
