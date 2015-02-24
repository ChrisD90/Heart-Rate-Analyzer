/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * @author Christoph
 *
 */
public class FrameFAQ extends JFrame{
	

	JButton ok;

	public FrameFAQ() {
		initFrame();
		writeAbout();
	}

	private void initFrame() {
		System.out.println("FrameAbout-initFrame()");
		this.setSize(650, 500);
		this.setTitle("FAQ");
		final Dimension d = this.getToolkit().getScreenSize();
		this.setLocation((int) ((d.getWidth() - this.getWidth()) / 2),
				(int) ((d.getHeight() - this.getHeight()) / 2));

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setIcon();
		getContentPane().setLayout(new BorderLayout());
		
		
		ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

			}
		});
		
		
		this.setVisible(true);
	}

	private void writeAbout() {
		
		BufferedImage bi = null;
		URL img = getClass().getResource("/images/FAQ.jpg");

		try {
			bi = ImageIO.read(img);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon image = new ImageIcon(bi);
		JPanel west = new JPanel();
		JLabel lblWest = new JLabel(image);
		west.add(lblWest);
		
		JPanel south = new JPanel();
		south.setBackground(Color.WHITE);
		south.add(ok);
		
		JScrollPane pane = new JScrollPane(west); //(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		this.add(pane, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);
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
