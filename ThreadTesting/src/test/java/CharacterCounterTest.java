import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.concurrent.ForkJoinPool;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CharacterCounterTest {
	private static FolderSearchTask folder;

	@BeforeAll
	public static void init() {

		ClassLoader loader = CharacterCounterTest.class.getClassLoader();
		folder = new FolderSearchTask(new File(loader.getResource("test1").getPath()));

	}

	@Test
	public void testFilesReader() {
		AtomicIntegerArray results = folder.invoke();

		// There are 4338 a/4338 b/4337 c/4335 d in the files.
		assertEquals(results.get(0), 4338);
		assertEquals(results.get(1), 4338);
		assertEquals(results.get(2), 4337);
		assertEquals(results.get(3), 4335);
		IntStream.range(4, 26).forEach(index -> assertEquals(results.get(index), 0));

	}

	@Test
	public void testFilesCollector() {

		assertEquals(4337, folder.getNumberOfFiles());
	}



}
