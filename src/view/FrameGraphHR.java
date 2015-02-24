/**
 * 
 */
package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.*;

import javax.imageio.*;
import javax.swing.*;

import model.Analyzer;
import model.TrainingSession;

/**
 * @author Christoph diese Klasse zeichnet uns den Graphen der avgHR von allen
 *         Trainingseinheiten soll dynamisch, also anhand der Größe der Liste
 *         gezeichent werden!!!!
 */
public class FrameGraphHR extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Analyzer sessions;
	LinkedList<TrainingSession> sessionList;
	int listSize;

	public FrameGraphHR(Analyzer sessions) {

		System.out.println("FrameGraphHR-Opened");
		setTitle("avg HeartRate of all Sessions");
		initSessions(sessions);
		initFrame();
		
		}

	/**
	 * prepares parameters for the graph reads the log-file
	 * 
	 * @param sessions
	 */
	public void initSessions(Analyzer sessions) {
		System.out.println("FrameGraphHR-initSessions()");
		this.sessions = sessions;
		sessionList = sessions.deliverList();
		listSize = sessionList.size();
		System.out.println(listSize);
	}

	/**
	 * inits the frame of the view
	 */
	public void initFrame() {
		System.out.println("FrameGraphHR-initFrame()");
		this.setSize(1000, 700);
		final Dimension d = this.getToolkit().getScreenSize();
		this.setLocation((int) ((d.getWidth() - this.getWidth()) / 2),
				(int) ((d.getHeight() - this.getHeight()) / 2));

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setIcon();
		this.setVisible(true);
	}

	public void paint(Graphics g) {
		System.out.println("FrameGraphHR-paint()");		
		g.drawLine(100, 600, 100, 100); // y-axis
		g.drawLine(100, 600, 900, 600); // x-axis

		try {
			paintGraphHR(g, sessionList);
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	public void paintGraphHR(Graphics g, LinkedList<TrainingSession> sessionList) throws ArrayIndexOutOfBoundsException{
		System.out.println("FrameGraphHR-paintGraphHR()");
		int positionOfXes = (int) (800 / (listSize + 1));
		int x = positionOfXes;

		int hr = 20;

		// arrays for the coordinates of the dots
		int[] yCoordinates = new int[listSize];
		int[] xCoordinates = new int[listSize];

		// i is the number of x seperators
		for (int i = 0; i < listSize; i++) {

			// x-axes & dots of HR

			String string = "#" + (i + 1);
			g.drawLine(100 + positionOfXes, 595, 100 + positionOfXes, 605);
			g.drawString(string, 95 + positionOfXes, 625);

			xCoordinates[i] = positionOfXes + 100;

			int yPos = (int) (100 + (500 - (sessionList.get(i).getAvgHR() * 2.5)));
			g.drawOval(positionOfXes + 100, yPos, 2, 2);

			yCoordinates[i] = yPos;

			System.out
					.println("Done for Session #"
							+ (i + 1)
							+ "\nx-pos: "
							+ (100 + positionOfXes)
							+ "\ny-pos: "
							+ ((int) (100 + (500 - (sessionList.get(i)
									.getAvgHR() * 2.5)))) + "\nHR: "
							+ sessionList.get(i).getAvgHR());

			positionOfXes += x;

		}

		for (int y = 550; y > 100; y -= 50) {

			// y-axes
			g.drawLine(95, y, 105, y);
			g.drawString("" + hr, 65, (y + 5));
			hr += 20;

		}
		g.drawString("Session", 900, 625);
		g.drawString("avg HeartRate", 45, 85);

		//lines between points
		for (int c = 0; c < listSize; c++) {

			g.drawLine(xCoordinates[c], yCoordinates[c], xCoordinates[c + 1],
					yCoordinates[c + 1]);

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
