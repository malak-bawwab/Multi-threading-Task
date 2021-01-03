import java.io.File;
import java.io.IOException;

public class Main {
	
public static void main(String[] args) throws Exception{
	
	if (args.length == 0) {
		throw new IllegalArgumentException("Please provide a path for the folder");
	}
	 CharacterCounter characterCounter = new CharacterCounter();
	
	    Folder folder = Folder.fromDirectory(new File(args[0]));
	    for (int i=0;i<26;i++) {
	    	
	     char c=(char)(i+97);

	    System.out.println(c+"\t"+characterCounter .countOccurrencesInParallel(folder)[i]);
	    }
	
}
}
