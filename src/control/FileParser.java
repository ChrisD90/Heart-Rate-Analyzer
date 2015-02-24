package control;

import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

import model.TrainingSession;

/**
 * @author Christoph
 * 
 */
public class FileParser {

	LinkedList<TrainingSession> sessions = new LinkedList<TrainingSession>();

	public FileParser(String fileName) throws IOException {
		System.out.println("FileParser-Constructor");
		parseCsvFile(fileName);
	}

	/**
	 * parses the file line by line and creates a session per line that is added
	 * to the linked list
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public void parseCsvFile(String fileName) throws IOException {
		System.out.println("FileParser-parseCsvFiel()");

		TrainingSession session = new TrainingSession();
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String current;

		current = reader.readLine();

		while (current != null) {
			session = processCsvLine(current);
			sessions.add(session); // sessions are added to our list
			current = reader.readLine();
		}
		reader.close();

	}

	/**
	 * session is generated
	 * 
	 * @param data
	 * @return
	 */
	public TrainingSession processCsvLine(final String data) {
		System.out.println("FileParser-processCsvLine()");
		TrainingSession session = new TrainingSession();
		StringTokenizer st = new StringTokenizer(data, ";");
		String[] tokenArray = new String[8];

		// extract tokens from the coloumns and put them into an array
		while (st.hasMoreTokens()) {

			for (int i = 0; i < 8; i++) {
				tokenArray[i] = st.nextToken();
			}
		}

		/*
		 * actual training session is created and written to the linked list
		 */
		session.setDate(tokenArray[0]);
		session.setAge(Integer.parseInt(tokenArray[1]));
		session.setSex(tokenArray[2]);
		session.setSize(Integer.parseInt(tokenArray[3]));
		session.setMass(Double.parseDouble(tokenArray[4]));
		session.setAvgHR(Double.parseDouble(tokenArray[5]));
		session.setAvgWatt(Double.parseDouble(tokenArray[6]));
		session.setTime(Integer.parseInt(tokenArray[7]));

		session.setBmi(); // bases on mass and size
		session.setAvgQoutient(); // bases on the avgHR and avgWatt
		session.setPowerFactor();
		session.setTrainingHR(); // bases on age

		session.setSession();

		return session;
	}

	public LinkedList<TrainingSession> getListOfSessions() {
		System.out.println("FileParser-getListOfSessions()");
		return sessions;
	}
}
