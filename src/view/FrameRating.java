/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.Analyzer;
import model.TrainingSession;
import panels.PanelGraphHR;
import panels.PanelGraphQuotient;
import panels.PanelGraphWatt;

/**
 * @author Christoph
 * 
 */
public class FrameRating extends JFrame {

	// GUI-Compontents
	JButton buttonHR;
	JButton buttonWatt;
	JButton buttonQ;

	JPanel center;
	PanelGraphHR panelHR;
	PanelGraphWatt panelW;
	PanelGraphQuotient panelQ;

	// Analysins-Components
	Analyzer sessions;
	String recomends;
	boolean ratingOfSession;
	String ratingOfQuotient;
	String ratingOfQuotient_Meaning;

	public FrameRating(Analyzer sessions) {

		this.sessions = sessions;

		recomends = sessions.compareSession();
		ratingOfSession = sessions.getRating();
		ratingOfQuotient = sessions.checkQuotient();

		int windowX = 1430;
		int windowY = 800;
		final Dimension d = this.getToolkit().getScreenSize();

		setLocation((int) ((d.getWidth() - this.getWidth()) / 2) - windowX / 2,
				(int) ((d.getHeight() - this.getHeight()) / 2) - windowY / 2);
		setSize(windowX, windowY);

		setTitle("Analysis of your last Session");
		setIcon();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);

		// BorderLAYOUT
		getContentPane().setLayout(new BorderLayout());

		// init the panels
		panelSouth(); // buttons
		panelCenter();
		panelEast();
		panelWest();
		panelNorth();

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
		this.setIconImage(image.getImage());}

	private void panelSouth() {

		FlowLayout fl = new FlowLayout();
		JPanel panelSouth = new JPanel();
		panelSouth.setLayout(fl);
		panelSouth.setBackground(Color.white);

		JLabel emtpy1 = new JLabel("                ");
		JLabel emtpy2 = new JLabel("                ");

		buttonHR = new JButton("Show graph ov avg. HeartRate");
		buttonWatt = new JButton("Show graph ov avg. Watt");
		buttonQ = new JButton("Show graph ov avg. Quotient");

		panelSouth.add(buttonHR);
		panelSouth.add(emtpy1);
		panelSouth.add(buttonWatt);
		panelSouth.add(emtpy2);
		panelSouth.add(buttonQ);

		getContentPane().add(panelSouth, BorderLayout.SOUTH);
	}

	private void panelCenter() {
		center = new JPanel(new CardLayout());

		panelHR = new PanelGraphHR(sessions);
		panelW = new PanelGraphWatt(sessions);
		panelQ = new PanelGraphQuotient(sessions);

		center.add(panelHR, "1");
		center.add(panelW, "2");
		center.add(panelQ, "3");

		buttonHR.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				validate();
				repaint();
				CardLayout cl = (CardLayout) center.getLayout();
				cl.show(center, "1");
				getContentPane().add(center, BorderLayout.CENTER);
			}
		});

		buttonWatt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				validate();
				repaint();
				CardLayout cl = (CardLayout) center.getLayout();
				cl.show(center, "2");
				getContentPane().add(center, BorderLayout.CENTER);
			}
		});

		buttonQ.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				validate();
				repaint();
				CardLayout cl = (CardLayout) center.getLayout();
				cl.show(center, "3");
				getContentPane().add(center, BorderLayout.CENTER);
			}
		});
	}

	private void panelEast() {
		GridLayout gl = new GridLayout(0, 1);

		JPanel panelEast = new JPanel();
		panelEast.setLayout(gl);
		panelEast.setBackground(Color.WHITE);
		ImageIcon image = null;

		if (!ratingOfSession) {
			BufferedImage bi = null;
			URL img = getClass().getResource("/images/sad.jpg");

			try {
				bi = ImageIO.read(img);
			} catch (IOException e) {
				e.printStackTrace();
			}
			image = new ImageIcon(bi);
			// panelEast.setBackground(Color.red);
		} else if (ratingOfSession) {
			BufferedImage bi = null;
			URL img = getClass().getResource("/images/smile.jpg");

			try {
				bi = ImageIO.read(img);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// panelEast.setBackground(Color.green);
			image = new ImageIcon(bi);

		}

		JLabel lblEast = new JLabel(image);
		panelEast.add(lblEast);
		getContentPane().add(panelEast, BorderLayout.EAST);
	}

	private void panelWest() {
		TrainingSession last = sessions.getLatestSession();

		JPanel panelWest = new JPanel();
		panelWest.setPreferredSize(new Dimension(220, 600));
		panelWest.setLayout(new BorderLayout());
		panelWest.setBackground(Color.WHITE);

		JTextArea textAreaSession = new JTextArea(sessions.getLatestSession()
				.printSession());
		textAreaSession.setEditable(false);
		panelWest.add(textAreaSession, BorderLayout.NORTH);

		// LABELS DER SESSION-DATEN

		ImageIcon image = null;

		String qText = null;

		if (ratingOfQuotient.equals("EXCELLENT")) {
			ratingOfQuotient_Meaning = "Excellent";
			BufferedImage bi = null;
			URL img = getClass().getResource("/images/ampel_gruen.jpg");
			try {
				bi = ImageIO.read(img);
			} catch (IOException e) {
				e.printStackTrace();
			}
			image = new ImageIcon(bi);
			qText = "The proportion between your avgWatt"
					+ "\nand your avgHR is excellent!"
					+ "\nThe quotient is compared with previous"
					+ "\nsessions."
					+ "\n"
					+ "\nFor more information, read the FAQ";

		} else if (ratingOfQuotient.equals("GOOD")) {
			ratingOfQuotient_Meaning = "Good";
			BufferedImage bi = null;
			URL img = getClass().getResource("/images/ampel_gelb.jpg");
			try {
				bi = ImageIO.read(img);
			} catch (IOException e) {
				e.printStackTrace();
			}
			image = new ImageIcon(bi);
			qText = "The proportion between your avgWatt"
					+ "\nand your avgHR could be better!"
					+ "\nThe quotient is compared with previous"
					+ "\nsessions."
					+ "\nTo improve yourself:"
					+ "\n1) keep your avgHR"
					+ "\n    and increase your avgWatt"
					+ "\nOR"
					+ "\n2) try to lower your avgHR"
					+ "\n    and keep your avgWatt!"
					+ "\n"
					+ "\nFor more information, read the FAQ";
		} else if (ratingOfQuotient.equals("NOT EFFICENT")) {
			ratingOfQuotient_Meaning = "Not efficent";
			BufferedImage bi = null;
			URL img = getClass().getResource("/images/ampel_rot.jpg");
			try {
				bi = ImageIO.read(img);
			} catch (IOException e) {
				e.printStackTrace();
			}
			image = new ImageIcon(bi);
			qText = "The proportion between your avgWatt"
					+ "\nand your avgHR is bad!\nThe quotient is compared with previous"
					+ "\nsessions."
					+ "\nThe quotient is compared with previous"
					+ "\nsessions."
					+ "\nTo improve yourself:"
					+ "\n1) keep your avgHR"
					+ "\n   and increase your avgWatt"
					+ "\nOR"
					+ "\n2) try to lower your avgHR"
					+ "\n   and keep your avgWatt!"
					+ "\n"
					+ "\nFor more information, read the FAQ";
		}

		JTextArea qtextVew = new JTextArea(qText);
		qtextVew.setEditable(false);
		JLabel lblEast = new JLabel(image);
		panelWest.add(qtextVew, BorderLayout.SOUTH);
		panelWest.add(lblEast, BorderLayout.CENTER);
		getContentPane().add(panelWest, BorderLayout.WEST);
	}

	private void panelNorth() {

		FlowLayout fl = new FlowLayout();
		JPanel panelNorth = new JPanel();
		panelNorth.setLayout(fl);

		if (!ratingOfSession) {
			panelNorth.setBackground(Color.red);
		} else {
			panelNorth.setBackground(Color.green);
		}

		JLabel heading = new JLabel(recomends);// "Analyze of your last training session in compare to previous sessions:");
		Font f = new Font(Font.SERIF, Font.BOLD, 20);
		heading.setFont(f);

		panelNorth.add(heading);
		getContentPane().add(panelNorth, BorderLayout.NORTH);
	}
}
