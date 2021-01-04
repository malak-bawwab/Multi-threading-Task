import java.io.File;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class FileSearchTask extends RecursiveTask<AtomicIntegerArray> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File file;
	private FilesReader filesReader;

	FileSearchTask(File file) {
		super();
		this.file = file;
		filesReader = new FilesReader();
	}

	@Override
	protected AtomicIntegerArray compute() {

		filesReader.readFile(file);
		return filesReader.getCharsCountArray();
	}

}