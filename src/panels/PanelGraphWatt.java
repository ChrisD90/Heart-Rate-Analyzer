/**
 * 
 */
package panels;

import java.awt.Color;
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
public class PanelGraphWatt extends JPanel{
	private static final long serialVersionUID = 1L;

	Analyzer sessions;
	LinkedList<TrainingSession> sessionList;
	int listSize;

	public PanelGraphWatt(Analyzer sessions) {
		System.out.println("PanelGraphWatt-Opened");
		initSessions(sessions);
		initFrame();

	}

	/**
	 * prepares parameters for the graph reads the log-file
	 * 
	 * @param sessions
	 */
	public void initSessions(Analyzer sessions) {
		System.out.println("PanelGraphWatt-initSessions()");
		this.sessions = sessions;
		sessionList = sessions.deliverList();
		listSize = sessionList.size();
		System.out.println(listSize);
	}

	/**
	 * inits the frame of the view
	 */
	public void initFrame() {
		System.out.println("PanelGraphWatt-initFrame()");
		this.setSize(600, 800);
		this.setBackground(Color.white);
		this.setVisible(true);
	}

	public void paint(Graphics g) {
		System.out.println("PanelGraphWatt-paint()");
				
		g.drawLine(100, 600, 100, 100); // y-axis
		g.drawLine(100, 600, 900, 600); // x-axis

		paintGraphWatt(g, sessionList);
		

	}

	public void paintGraphWatt(Graphics g, LinkedList<TrainingSession> sessionList) {
		System.out.println("PanelGraphWatt-paintGraphWatt()");
		
		int positionOfXes = (int) (800 / (listSize + 1));
		int x = positionOfXes;

		int watt = 40;

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

			int yPos = (int) (100 + (500 - (sessionList.get(i).getAvgWatt() * 1.25)));
			g.drawOval(positionOfXes + 100, yPos, 2, 2);

			yCoordinates[i] = yPos;

			System.out
					.println("Done for Session #"
							+ (i + 1)
							+ "\nx-pos: "
							+ (100 + positionOfXes)
							+ "\ny-pos: "
							+ yPos + "\nWatt: "
							+ sessionList.get(i).getAvgWatt());

			positionOfXes += x;

		}

		for (int y = 550; y > 100; y -= 50) {

			// y-axes
			g.drawLine(95, y, 105, y);
			g.drawString("" + watt, 65, (y + 5));
			watt += 40;

		}
		g.drawString("Session", 900, 625);
		g.drawString("avg Watt", 45, 85);

		//lines between points
		// lines between points
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
