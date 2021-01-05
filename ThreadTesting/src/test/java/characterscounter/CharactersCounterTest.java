package characterscounter;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

class CharactersCounterTest {

	@Test
	public void testFilesReader() {
		ClassLoader loader = CharactersCounterTest.class.getClassLoader();
		FolderSearchTask folder = new FolderSearchTask(new File(loader.getResource("test1").getPath()));
		AtomicIntegerArray results = folder.invoke();

		// There are 4338 a /4338 b /4337 c /4335 d in test1 folder.
		assertEquals(results.get(0), 4338);
		assertEquals(results.get(1), 4338);
		assertEquals(results.get(2), 4337);
		assertEquals(results.get(3), 4335);
		IntStream.range(4, 26).forEach(index -> assertEquals(results.get(index), 0));

	}

	@Test
	public void testInValidFiles() {

		ClassLoader loader = CharactersCounterTest.class.getClassLoader();
		FolderSearchTask folder = new FolderSearchTask(new File(loader.getResource("invalidFiles").getPath()));
		AtomicIntegerArray results = folder.invoke();
		IntStream.range(0, 26).forEach(index -> assertEquals(results.get(index), 0));

	}

}
