/**
 * 
 */
package panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JPanel;

import model.Analyzer;
import model.TrainingSession;

/**
 * @author Christoph
 * 
 */
public class PanelGraphHR extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Analyzer sessions;
	LinkedList<TrainingSession> sessionList;
	int listSize;

	public PanelGraphHR(Analyzer sessions) {

		System.out.println("PanelGraphHR-Opened");
		initSessions(sessions);
		initFrame();

	}

	/**
	 * prepares parameters for the graph reads the log-file
	 * 
	 * @param sessions
	 */
	public void initSessions(Analyzer sessions) {
		System.out.println("PanelGraphHR-initSessions()");
		this.sessions = sessions;
		sessionList = sessions.deliverList();
		listSize = sessionList.size();
		System.out.println(listSize);
	}

	/**
	 * inits the frame of the view
	 */
	public void initFrame() {
		System.out.println("PanelGraphHR-initFrame()");
		this.setSize(600, 800);
		final Dimension d = this.getToolkit().getScreenSize();
		this.setLocation((int) ((d.getWidth() - this.getWidth()) / 2),
				(int) ((d.getHeight() - this.getHeight()) / 2));
		this.setVisible(true);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("PanelGraphHR-paint()");
		g.drawLine(100, 600, 100, 100); // y-axis
		g.drawLine(100, 600, 900, 600); // x-axis

		paintGraphHR(g, sessionList);

	}

	public void paintGraphHR(Graphics g, LinkedList<TrainingSession> sessionList)
			throws ArrayIndexOutOfBoundsException {
		System.out.println("PanelGraphHR-paintGraphHR()");
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

		try {
			for (int c = 0; c < listSize; c++) {

				g.drawLine(xCoordinates[c], yCoordinates[c],
						xCoordinates[c + 1], yCoordinates[c + 1]);

			}
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
}
