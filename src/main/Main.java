/**
 * 
 */
package main;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import view.FrameLogIn;

/**
 * @author Christoph
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// MainFrame mainFrame = new MainFrame();
		System.out.println(">Main<");
		createNewFolder();
		FrameLogIn fli = new FrameLogIn();
	}

	/**
	 * sets the directory for the userfiles (log in data) and the sessions based on the user
	 */
	public static void createNewFolder() {
		System.out.println("Main-createNewFolder()");
		Path userDataPath = Paths.get(System.getProperty("user.home"))
				.resolve("Documents").resolve("MyAnalyzer")
				.resolve("MyAnalyzer_Userfiles");
		Path sessionDataPath = Paths.get(System.getProperty("user.home"))
				.resolve("Documents").resolve("MyAnalyzer").resolve("Sessions");

		String folderUser = userDataPath + ""; // neuer Ordner wird in Documents
												// angelegt
		String folderSession = sessionDataPath + "";

		try {
			File file = new File(folderUser);
			
			File fileSession = new File(folderSession);

			fileSession.mkdirs();
			file.mkdirs();
		} finally {

		}
	}
}