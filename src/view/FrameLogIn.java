/**
 * 
 */
package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
public class FrameLogIn extends JFrame {

	JButton login = new JButton("Login");
	JButton newUser = new JButton("New User");

	JPanel panel = new JPanel();
	JTextField userName = new JTextField(15);
	JPasswordField passWord = new JPasswordField(15);

	boolean isUser;

	public FrameLogIn() {
		System.out.println("FrameLogIn-Opened");
		/*
		 * Fenster in Bildschrimmitte positionieren
		 */
		int windowX = 300;
		int windowY = 200;
		final Dimension d = this.getToolkit().getScreenSize();
		
		setLocation((int) ((d.getWidth() - this.getWidth()) / 2)-windowX/2,
				(int) ((d.getHeight() - this.getHeight()) / 2)-windowY/2);
		setSize(windowX, windowY);
		
		setTitle("Login Authentification");
		setIcon();
		panel.setLayout(null);

		userName.setBounds(70, 30, 150, 20);
		passWord.setBounds(70, 65, 150, 20);
		login.setBounds(40, 100, 100, 20);
		newUser.setBounds(160, 100, 100, 20);

		panel.add(login);
		panel.add(userName);
		panel.add(passWord);
		panel.add(newUser);

		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		initActionListeners();
	}

	public void initActionListeners() {
		System.out.println("FrameLogIn-initActionListeners()");
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				String stringUser = userName.getText();
				String stringPW = passWord.getText();

				try {
					isUser(stringUser, stringPW);
				} catch (NullPointerException e) {
					JOptionPane.showMessageDialog(null,
							"Wrong Password / Username");
					userName.setText("");
					passWord.setText("");
					userName.requestFocus();
				}

				if (isUser) {
					MainFrame mf = new MainFrame(stringUser);
					dispose();

				} else {


					userName.setText("");
					passWord.setText("");
					userName.requestFocus();
				}

			}
		});

		newUser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				FrameNewUser fnu = new FrameNewUser();

			}
		});
	}

	public void isUser(String userName, String passWord)
			throws NullPointerException {
		System.out.println("FrameLogIn-isUser()");
		
		isUser = false;

		Path userDataPath = Paths.get(System.getProperty("user.home"))
				.resolve("Documents").resolve("MyAnalyzer")
				.resolve("MyAnalyzer_Userfiles").resolve("Userfiles.csv");
		String path = userDataPath + "";

		try {

			BufferedReader br = new BufferedReader(new FileReader(path));
			String current;

			while (br != null) {// br != null) {

				current = br.readLine();

				StringTokenizer st = new StringTokenizer(current, ";");
				String[] tokenArray = new String[2];
				// extract tokens from the coloumns and put them into an array
				while (st.hasMoreTokens() && current != null) {

					for (int i = 0; i < 2; i++) {
						tokenArray[i] = st.nextToken();
						System.out.println(tokenArray[0] + ";" + tokenArray[1]);
					}

					if (tokenArray[0].equals(userName)
							&& tokenArray[1].equals(passWord)) {
						isUser = true;
						System.out.println(isUser);
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
