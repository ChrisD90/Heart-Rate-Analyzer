/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.Analyzer;
import model.TrainingSession;

/**
 * @author Christoph the main frame contains the control elements tha allows us
 *         to analyze or look at specific sessions and proposes us how to go on
 *         with our training - analyzing is done by the Analyzer class
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	/*
	 * the analyzer receives all session and gives us the possibility to work
	 * with a linked list that contains all sessions from the log file
	 */
	Analyzer sessions;
	String user;
	/*
	 * menu
	 */
	JMenuBar menuBar = new JMenuBar();

	JMenu data = new JMenu("Data");
	JMenuItem open = new JMenuItem("Import new Session");
	JMenuItem logout = new JMenuItem("Logout");
	JMenuItem close = new JMenuItem("Close");

	JMenu session = new JMenu("Session(s)");
	JMenuItem showLog = new JMenuItem("Show all Sessions"); // list of sessions
	JMenuItem getlatestSession = new JMenuItem("Get latest Session");
	JMenuItem deleteSessionX = new JMenuItem("Delete Session X");

	JMenu analyze = new JMenu("Analyze");
	JMenuItem rateSession = new JMenuItem("Rate my latest Session");
	JMenuItem graphHR = new JMenuItem("Show graph of avgHR");
	JMenuItem graphWatt = new JMenuItem("Show graph of avgWatt");
	JMenuItem graphQuotient = new JMenuItem("Show graph of Quotient");

	JMenu help = new JMenu("Help");
	JMenuItem faq = new JMenuItem("FAQ's");
	JMenuItem about = new JMenuItem("About...");

	/**
	 * a new window is generated in the main class
	 */
	public MainFrame(String userName) {
		System.out.println("MainFrame-Opened");
		
		user = userName;
		createNewFolder(userName);

		/*
		 * Fenster in Bildschrimmitte positionieren
		 */
		int windowX = 800;
		int windowY = 600;
		final Dimension d = this.getToolkit().getScreenSize();

		setLocation((int) ((d.getWidth() - this.getWidth()) / 2) - windowX / 2,
				(int) ((d.getHeight() - this.getHeight()) / 2) - windowY / 2);
		setSize(windowX, windowY);

		setTitle("Analyzer of " + user);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		
		setIcon();
		initMenue();
		initBackground();
		setVisible(true);
	}

	private void setIcon() {
		BufferedImage bi = null;
		URL img = getClass().getResource("/images/logo2.jpg");
		
		try {
			bi = ImageIO.read(img);
		} catch(IOException e) {
			e.printStackTrace();
		}
			
		ImageIcon image = new ImageIcon(bi);
		this.setIconImage(image.getImage());
	}
	private void initBackground() {
		BufferedImage bi = null;
		URL img = getClass().getResource("/images/ergometer.jpg");
		
		try {
			bi = ImageIO.read(img);
		} catch(IOException e) {
			e.printStackTrace();
		}
			
		ImageIcon image = new ImageIcon(bi);
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);

		JLabel lbl = new JLabel(image);
		lbl.setBounds(0, 0, 800, 600);
		panel.add(lbl);
		getContentPane().add(panel, BorderLayout.CENTER);
	}
	
	/**
	 * adds components to the menu bar initializes action listeners
	 */
	public void initMenue() {
		System.out.println("MainFrame-initMenu()");
		
		this.setJMenuBar(menuBar);

		menuBar.add(data);
		data.add(open);
		data.add(logout);
		data.add(close);

		menuBar.add(session);
		session.add(getlatestSession);
		session.add(showLog);
		//session.add(deleteSessionX);------------------------------------------------------------------------------------------TODO

		menuBar.add(analyze);
		analyze.add(rateSession);
		analyze.add(graphHR);
		analyze.add(graphWatt);
		analyze.add(graphQuotient);

		menuBar.add(help);
		help.add(faq);
		help.add(about);

		/*
		 * reads the log file (if it exists) after logging in you can immeditely
		 * start analyzing
		 */
		createList();

		initActionListener();
	}

	/**
	 * inints all the action listener for the menu items
	 */
	public void initActionListener() {
		System.out.println("MainFrame-initActionListener()");
		
		/**
		 * reads the latest session
		 */
		open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();
				TrainingSession x = new TrainingSession();
				x.writeToLog(file.getAbsolutePath(), user);

				createList();
			}
		});

		/**
		 * 
		 */
		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});

		/**
		 * throws the average heartrate based on all sessions - extracted from
		 * the linked list ;)
		 */
		rateSession.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				FrameRating frame_new = new FrameRating(sessions);
			}
		});

		/**
		 * displays the latest session added to the log file
		 */
		getlatestSession.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				@SuppressWarnings("unused")
				FrameLatestSession fls = new FrameLatestSession(sessions
						.getLatestSession());
			}
		});

		graphHR.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				FrameGraphHR frame = new FrameGraphHR(sessions);

			}
		});

		graphWatt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				FrameGraphWatt frame = new FrameGraphWatt(sessions);

			}
		});

		graphQuotient.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("unused")
				FrameGraphQuotient frame = new FrameGraphQuotient(sessions);

			}
		});

		showLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String string = sessions.printLog();
				@SuppressWarnings("unused")
				FrameAllSessions fas = new FrameAllSessions(string);
			}
		});

		logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				FrameLogIn fli = new FrameLogIn();
				dispose();

			}
		});

		faq.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FrameFAQ frame = new FrameFAQ();

			}
		});
		
		about.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FrameAbout frame = new FrameAbout();
				
			}
		});

		deleteSessionX.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int counter = countLines();
				/*try {
					//deleteSessionX(counter);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}*/

			}
		});
	}

	public static void createNewFolder(String userName) {
		System.out.println("MainFrame-createNewFolder()");
		
		Path sessionDataPath = Paths.get(System.getProperty("user.home"))
				.resolve("Documents").resolve("MyAnalyzer").resolve("Sessions")
				.resolve(userName);

		String folderSession = sessionDataPath + "";

		File fileSession = new File(folderSession);

		fileSession.mkdirs();

	}

	/**
	 * creates the Linked List from the log file of the user
	 */
	private void createList() {
		System.out.println("MainFrame-createList()");
		
		sessions = new Analyzer();

		try {
			sessions.readInSessions(user);

			/*JOptionPane
					.showMessageDialog(
							null,
							"Session added to Log - List was created\nYou can start analyzing...",
							"Session added", JOptionPane.INFORMATION_MESSAGE);*/
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null,
					"No Log-File found!! \nPlease add session(s) first!!\n",
					"No File found", JOptionPane.ERROR_MESSAGE);
			System.out.println("MainFrame: Error - No List created");
			e1.printStackTrace();

		}
	}


	/**
	 * deletes the line from the log file the user chooses
	 * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	 * GEHT NET!!!! <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	 * 
	 * @param lineToDelete
	 * @throws FileNotFoundException
	 */
	public void deleteSessionX(int lineToDelete) throws FileNotFoundException {
		System.out.println("MainFrame-deleteSessionX <--- TODO()");
		
		int counter = countLines();

		Path userDataPath = Paths.get(System.getProperty("user.home"))
				.resolve("Documents").resolve("MyAnalyzer").resolve("Sessions")
				.resolve(user).resolve(user + "_Log-Data.csv");
		String dataPath = userDataPath + "";

		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(dataPath));

		for (int i = 0; i <= counter; i++) {
			try {

				String current;
				current = br.readLine();

				if (i == lineToDelete) {
					current = br.readLine(); // Zeile überspringen
				}
				File file = new File(dataPath);
				FileWriter fw = null;

				/*
				 * if (file.exists()) System.out .println(
				 * "Data already exists. \nNew information will be appended!");
				 * else System.out.println("Data generated");
				 */

				fw = new FileWriter(file.getPath(), true);

				PrintWriter pw = new PrintWriter(fw);
				pw.println(current);
				System.out.println("Written to Log: \n" + current + "\n");

				fw.flush();
				fw.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		System.out.println("\n" + counter);
		createList();
	}

	/**
	 * counts the amount of sessions in the log file
	 * 
	 * @return
	 */
	public int countLines() {
		System.out.println("MainFrame-countLines()");
		
		int counter = 0;

		try {
			Path userDataPath = Paths.get(System.getProperty("user.home"))
					.resolve("Documents").resolve("MyAnalyzer")
					.resolve("Sessions").resolve(user)
					.resolve(user + "_Log-Data.csv");
			String dataPath = userDataPath + "";

			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(dataPath));

			String current = br.readLine();

			// Zeilen zählen
			while (current != null) {
				counter++;
				current = br.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return counter;
	}
}
