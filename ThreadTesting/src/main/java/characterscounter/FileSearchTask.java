package characterscounter;

import java.io.File;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * This class represent FileSearchTask that uses FileReader class to compute the
 * count of small characters in the file that has been passed through its
 * constructor.
 * 
 * @author Malak
 *
 */
public class FileSearchTask extends RecursiveTask<AtomicIntegerArray> {

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
