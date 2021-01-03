import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Folder {
	private final ArrayList<Folder> subFolders;
	private final ArrayList<File> files;

	Folder(ArrayList<Folder> subFolders,ArrayList<File> files) {
		this.subFolders = subFolders;
		this.files = files;
	}

	ArrayList<Folder> getSubFolders() {
		return this.subFolders;
	}

	ArrayList<File> getFiles() {
		return this.files;
	}

	static Folder fromDirectory(File dir) throws IOException {
		ArrayList<File> files = new ArrayList<>();
		ArrayList<Folder> subFolders = new ArrayList<>();
		for (File entry : dir.listFiles()) {
			if (entry.isDirectory()) {
				subFolders.add(Folder.fromDirectory(entry));
			} else {
				files.add(entry);
			}
		}
		return new Folder(subFolders, files);
	}
}
