/**
 * 
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import model.Analyzer;
import model.TrainingSession;

/**
 * @author Christoph
 *
 */
public class FrameGraphQuotient extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Analyzer sessions;
	LinkedList<TrainingSession> sessionList;
	int listSize;

	public FrameGraphQuotient(Analyzer sessions) {
		System.out.println("FrameGraphQuotient-Opened");
		setTitle("avg Quotient of all Sessions");
		initSessions(sessions);
		initFrame();

	}

	/**
	 * prepares parameters for the graph reads the log-file
	 * 
	 * @param sessions
	 */
	public void initSessions(Analyzer sessions) {
		System.out.println("FrameGraphQuotient-initSessions()");
		this.sessions = sessions;
		sessionList = sessions.deliverList();
		listSize = sessionList.size();
		System.out.println(listSize);
	}

	/**
	 * inits the frame of the view
	 */
	public void initFrame() {
		System.out.println("FrameGraphQuotient-initFrame()");
		this.setSize(1000, 700);
		final Dimension d = this.getToolkit().getScreenSize();
		this.setLocation((int) ((d.getWidth() - this.getWidth()) / 2),
				(int) ((d.getHeight() - this.getHeight()) / 2));
		this.setBackground(Color.white);
		setIcon();
		this.setVisible(true);
	}

	public void paint(Graphics g) {
		System.out.println("FrameGraphQuotient-paint()");		
		g.drawLine(100, 600, 100, 100); // y-axis
		g.drawLine(100, 600, 900, 600); // x-axis

		paintGraphQuotient(g, sessionList);
		

	}

	public void paintGraphQuotient(Graphics g, LinkedList<TrainingSession> sessionList) {
		System.out.println("FrameGraphQuotient-initSessions()");
		int positionOfXes = (int) (800 / (listSize + 1));
		int x = positionOfXes;

		int q = 1;

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

			int yPos = (int) (100 + (500 - (sessionList.get(i).getAvgQoutient() * 500)));
			g.drawOval(positionOfXes + 100, yPos, 2, 2);

			yCoordinates[i] = yPos;

			System.out
					.println("Done for Session #"
							+ (i + 1)
							+ "\nx-pos: "
							+ (100 + positionOfXes)
							+ "\ny-pos: "
							+ ((int) (100 + (500 - (sessionList.get(i)
									.getAvgHR() * 2.5)))) + "\nQ: "
							+ sessionList.get(i).getAvgQoutient());

			positionOfXes += x;

		}

		for (int y = 550; y > 100; y -= 50) {

			// y-axes
			g.drawLine(95, y, 105, y);
			g.drawString("0." + q, 65, (y + 5));
			q += 1;

		}
		g.drawString("Session", 900, 625);
		g.drawString("avg Quotient", 45, 85);

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
