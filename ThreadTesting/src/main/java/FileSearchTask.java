import java.io.File;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class FileSearchTask extends RecursiveTask<AtomicIntegerArray> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final File file;
	private CharacterCounter characterCounter = new CharacterCounter();

	FileSearchTask(File file) {
		super();
		this.file = file;
	}

	@Override
	protected AtomicIntegerArray compute() {
		return characterCounter.occurrencesCount(file);
	}

}