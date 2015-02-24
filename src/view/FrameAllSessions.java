/**
 * 
 */
package view;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author Christoph
 *
 */
public class FrameAllSessions extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FrameAllSessions(String string) {
		System.out.println("FrameAllSessions-Opened");
		/*
		 * Fenster in Bildschrimmitte positionieren
		 */
		int windowX = 300;
		int windowY = 500;
		final Dimension d = this.getToolkit().getScreenSize();
		
		setLocation((int) ((d.getWidth() - this.getWidth()) / 2)-windowX/2,
				(int) ((d.getHeight() - this.getHeight()) / 2)-windowY/2);
		setSize(windowX, windowY);
		
		setTitle("All Sessions from Log...");
		setIcon();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JPanel contentPane = new JPanel();
		//add(contentPane, "North");
		
		JTextArea ta = new JTextArea(string);
		ta.setEditable(false);
		contentPane.add(ta);
		
		JScrollPane pane = new JScrollPane(contentPane); //(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		setContentPane(pane);
		
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

}
