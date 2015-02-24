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

import javax.imageio.ImageIO;
import javax.swing.*;

import model.TrainingSession;

/**
 * @author Christoph window that shows us the latest session from the list/log
 *         file sessions must be readed to list first
 */
public class FrameLatestSession extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	TrainingSession lastSession;

	public FrameLatestSession(TrainingSession session) {
		System.out.println("FrameLatestSession-Opened");
		lastSession = session;
		/*
		 * Fenster in Bildschrimmitte positionieren
		 */
		int windowX = 300;
		int windowY = 230;
		final Dimension d = this.getToolkit().getScreenSize();

		setLocation((int) ((d.getWidth() - this.getWidth()) / 2) - windowX / 2,
				(int) ((d.getHeight() - this.getHeight()) / 2) - windowY / 2);
		setSize(windowX, windowY);

		setTitle("Latest Session...");
		setIcon();
		setVisible(true);
	}

	public void paint(Graphics g) {
		System.out.println("FrameLatestSession-paint()");

		g.drawString("Date: ", 10,50);
		g.drawString("Age:", 10, 70);
		g.drawString("Sex:", 10, 90);
		g.drawString("Size:", 10, 110);
		g.drawString("Weight:", 10, 130);
		g.drawString("BMI:", 10, 150);
		g.drawString("avg. HeartRate:", 10, 170);
		g.drawString("avg. Watt:", 10, 190);
		g.drawString("Time:", 10, 210);

		String date = lastSession.getDate();
		String age = lastSession.getAge()+"";
		String sex = lastSession.getSex();
		String size = lastSession.getSize() + "";
		String weight = lastSession.getMass()+"";
		String bmi = lastSession.getBmi()+"";
		String avgHR = lastSession.getAvgHR()+"";
		String avgWatt = lastSession.getAvgWatt()+"";
		String time = lastSession.getTimeString();
		
		g.drawString(date, 100,50);
		g.drawString(age, 100, 70);
		g.drawString(sex, 100, 90);
		g.drawString(size, 100, 110);
		g.drawString(weight, 100, 130);
		g.drawString(bmi, 100, 150);
		g.drawString(avgHR, 100, 170);
		g.drawString(avgWatt, 100, 190);
		g.drawString(time, 100, 210);

		g.drawString("cm", 125, 110);
		g.drawString("kg", 130, 130);
		g.drawString("bpm", 135, 170);
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
