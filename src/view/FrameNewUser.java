/**
 * 
 */
package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author Christoph
 * 
 */
public class FrameNewUser extends JFrame {

	boolean isUser;

	JButton register = new JButton("Register");
	JButton cancel = new JButton("Cancel");

	JPanel panel = new JPanel();
	JTextField userName = new JTextField(15);
	JPasswordField passWord = new JPasswordField(15);
	JPasswordField repeatPW = new JPasswordField(15);

	JLabel labelUserName = new JLabel("UserName");
	JLabel labelPassword = new JLabel("Password");
	JLabel labelrptPW = new JLabel("repeat Password");

	public FrameNewUser() {
		System.out.println("FrameNewUser-Opened");
		/*
		 * Fenster in Bildschrimmitte positionieren
		 */
		int windowX = 400;
		int windowY = 250;
		final Dimension d = this.getToolkit().getScreenSize();
		
		setLocation((int) ((d.getWidth() - this.getWidth()) / 2)-windowX/2,
				(int) ((d.getHeight() - this.getHeight()) / 2)-windowY/2);
		setSize(windowX, windowY);
		
		setTitle("Register a new User");
		setIcon();
		panel.setLayout(null);

		userName.setBounds(120, 30, 150, 20);
		passWord.setBounds(120, 70, 150, 20);
		repeatPW.setBounds(120, 110, 150, 20);
		register.setBounds(40, 150, 100, 20);
		cancel.setBounds(250, 150, 100, 20);
		labelUserName.setBounds(20, 30, 100, 20);
		labelPassword.setBounds(20, 70, 100, 20);
		labelrptPW.setBounds(20, 110, 100, 20);

		panel.add(register);
		panel.add(userName);
		panel.add(passWord);
		panel.add(repeatPW);
		panel.add(cancel);
		panel.add(labelUserName);
		panel.add(labelPassword);
		panel.add(labelrptPW);

		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setActionListener();
		setVisible(true);

	}

	public void setActionListener() {
		System.out.println("FrameNewUser-setActionListener()");
		register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String stringUser = userName.getText();
				String stringPW1 = passWord.getText();
				String stringPW2 = repeatPW.getText();

				try {
					isUser(stringUser);
				} catch (NullPointerException e1) {
					//e1.printStackTrace();
				}

				if (!isUser) {
					if (stringPW1.equals(stringPW2)) {
						writeToUserfiles(stringUser, stringPW1);
						dispose();
					} else {
						String string = "Check your passwords!";
						JOptionPane.showMessageDialog(null, string);
						passWord.setText("");
						repeatPW.setText("");
						passWord.requestFocus();
					}
				} else {
					String string = "The User #" + stringUser
							+ " already exists.\nPlease choose other UserName";
					JOptionPane.showMessageDialog(null, string);

					userName.setText("");
					passWord.setText("");
					repeatPW.setText("");
					userName.requestFocus();
				}

			}
		});

		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

			}
		});
	}

	public void writeToUserfiles(String userName, String passWord) {
		System.out.println("FrameNewUser-writeToUserfiles()");
		try {

			Path userDataPath = Paths.get(System.getProperty("user.home"))
					.resolve("Documents").resolve("MyAnalyzer")
					.resolve("MyAnalyzer_Userfiles").resolve("Userfiles.csv");

			System.out.println(userDataPath);

			String dataPath = userDataPath + "";
			String current = userName + ";" + passWord;

			File file = new File(dataPath);
			FileWriter fw = null;

			if (file.exists())
				System.out
						.println("Data already exists. \nNew information will be appended!");

			else
				System.out.println("Data generated");

			fw = new FileWriter(file.getPath(), true);

			PrintWriter pw = new PrintWriter(fw);
			pw.println(current);
			System.out.println("Written to Userfiles: \n" + current + "\n");

			fw.flush();
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void isUser(String userName) throws NullPointerException {
		System.out.println("FrameNewUser-isUser()");
		isUser = false;

		Path userDataPath = Paths.get(System.getProperty("user.home"))
				.resolve("Documents").resolve("MyAnalyzer")
				.resolve("MyAnalyzer_Userfiles").resolve("Userfiles.csv");
		String path = userDataPath + "";

		try {

			BufferedReader br = new BufferedReader(new FileReader(path));
			String current;

			while (br != null) {

				current = br.readLine();

				StringTokenizer st = new StringTokenizer(current, ";");
				String[] tokenArray = new String[2];

				// extract tokens from the coloumns and put them into an array
				while (st.hasMoreTokens()) {

					for (int i = 0; i < 2; i++) {
						tokenArray[i] = st.nextToken();
					}
					if (tokenArray[0].equals(userName)) {
						isUser = true;
						return;
					}
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

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
		this.setIconImage(image.getImage());}

}
