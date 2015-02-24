package model;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

import control.FileParser;

/**
 * This class analyzes the latest training session by evaluating the average
 * herat rate and the average power. If this session is a "remarkable Session"
 * the analyzer will make a suggestion what to increase the heart rate. If there
 * is a "non-remarkable Session" the data is just saved.
 * 
 * the linked list only exists during the runtime of the program!!
 * 
 * @author Christoph
 */
public class Analyzer {

	LinkedList<TrainingSession> sessions;
	boolean rating = null != null;

	public boolean getRating() {
		System.out.println("Analyzer-getRating()");
		return rating;
	}

	/**
	 * returns the latest recorded session
	 */
	public TrainingSession getLatestSession() {
		System.out.println("Analyzer-getLatestSession()");
		return sessions.getLast();
	}

	public LinkedList<TrainingSession> deliverList() {
		System.out.println("Analyzer-deliverList()");
		return sessions;
	}

	/**
	 * brings all the single sessions to the list
	 * 
	 * @throws IOException
	 */
	public void readInSessions(String userName) throws IOException {
		System.out.println("Analyzer-readInSession()");
		FileParser fp;
		Path userDataPath = Paths.get(System.getProperty("user.home"))
				.resolve("Documents").resolve("MyAnalyzer").resolve("Sessions")
				.resolve(userName).resolve(userName + "_Log-Data.csv");
		String path = userDataPath + "";

		fp = new FileParser(path);

		sessions = fp.getListOfSessions();

	}

	/**
	 * prints the complete log-file with all sessions
	 */
	public String printLog() {
		System.out.println("Analyzer-printLog()");
		String string = "";

		for (int i = 0; i < sessions.size(); i++) {
			TrainingSession x = new TrainingSession();

			x = sessions.get(i);

			string = string + "Session #" + (i + 1) + "\n" + x.getSession()
					+ "\n\n";
		}
		return string;
	}

	/**
	 * rates a session compared to previous guidelines below :)
	 */
	public String compareSession() {
		System.out.println("Analyzer-compareSession()");
		/*
		 * ist die avgHR der letzten Session optimal zur maxHF +-10?? wenn ja:
		 * ist die avgWatt von allen Sessions <= als avgWatt der letzten
		 * Session? wenn ja: Training war optimal/watt bei gleicher HF erhöhen +
		 * Watt der letzten Sesioni anzeigen wenn nein: avgWatt erhöhen auf
		 * totalAvgWatt (=avgWatt+(totalAvgWatt-avgWatt)) wenn nein: - ist die
		 * avgHR der letzen Session größer? dann HR nächsten Mal auf maxHR
		 * dekrementieren - ist die avgHR der letzen Session kleiner? dann HR
		 * nachstes Mal auf maxHR inkrementieren zusätzlich ist die Zeit
		 * entscheidend - ein effektives Training kann erst ab ca. 30min
		 * stattfinden (also 30*60sec); die Trainingszeit kommt in sec rein
		 */
		String string = "no String";

		double avg1 = getAvgWattAllSessions();

		TrainingSession ts = sessions.getLast();

		// war die Zeit überhaupt in Ordnung? >30min
		if (ts.getTime() >= (60 * 20)) {
			System.out.println(1);
			// war die HF der TrainingsHF angepasst?
			if (ts.getAvgHR() <= ts.getTrainingHR() + 7
					&& ts.getAvgHR() >= ts.getTrainingHR() - 7) {
				System.out.println(2);
				// war die Wattzahl ungefähr so hoch wie die der vorherigen
				// Sessions im Schnitt und ist der Quotient aus HF/Watt höher?
				if (avg1 <= ts.getAvgWatt()) {
					System.out.println(3);
					string = "Your Training was perfect. Try to increase your Watt for the next time!\n\n";
					rating = true;
				}
				if (ts.getTime() > 60 * 60) {
					string = "Your training was perfect BUT do not train more than 60min per session. Keep your heartrate at "
							+ ts.getTrainingHR() + "bpm.";
					rating = true;
					// Die Wattzahl war zu niedrig
				} else if (avg1 > ts.getAvgWatt()) {
					System.out.println(4);
					string = "For an effectiv training RAISE your perfomance to "
							+ avg1
							+ "Watt. Your recommended heartrate for an effectiv training is "
							+ ts.getTrainingHR() + "bpm.";
					rating = false;
				}
				// die HF war zu niedrig
			} else if (ts.getAvgHR() < ts.getTrainingHR()) {
				System.out.println(6);
				string = "For an effective training "
						+ "RAISE your average heartrate to "
						+ ts.getTrainingHR() + "bpm.";
				rating = false;
				// die HF war zu hoch
			} else if (ts.getAvgHR() > ts.getTrainingHR()) {
				System.out.println(7);
				string = "For an effective training "
						+ "LOWER your average heartrate to "
						+ ts.getTrainingHR() + "bpm.";
				rating = false;
			}
			// die Zeit war zu gering
		} else if (ts.getTime() < 60 * 20) {
			System.out.println(7);
			string = "Increase your time to at least 30min!! Your recommended heartrate for an effectiv training is "
					+ ts.getTrainingHR() + "bpm.";
			rating = false;

		}

		return string;
	}

	/**
	 * calculates the avgWatt from all sessions except the latest
	 * 
	 * @return
	 */
	public double getAvgWattAllSessions() {
		System.out.println("Analyzer-getAvgWattAllSessions()");
		double x = 0;
		double value = 0;
		if (sessions.size() == 1) {
			return value;
		}

		for (int i = 0; i < sessions.size() - 1; i++) {
			x += sessions.get(i).getAvgWatt();
		}

		value = x / (sessions.size() - 1);

		return value;
	}

	/**
	 * calculates the avgWatt from all sessions except the latest
	 * 
	 * @return
	 */
	public double getAvgQuotientAllSessions() {
		System.out.println("Analyzer-getAvgWattAllSessions()");
		double x = 0;
		double value = 0;
		if (sessions.size() == 1) {
			return value;
		}

		for (int i = 0; i < sessions.size() - 1; i++) {
			x += sessions.get(i).getAvgQoutient();
		}

		value = x / (sessions.size() - 1);

		return value;
	}

	public String checkQuotient() {

		double avgQ = getAvgQuotientAllSessions();

		double q = sessions.getLast().getAvgQoutient();
		System.out.println(avgQ + " VS " + q);
		if (q < avgQ - 0.01) {
			return "EXCELLENT";
		} else if (q < avgQ + 0.01 || q > avgQ - 0.01) {
			return "GOOD";
		} else if (q > avgQ + 0.01) {
			return "NOT EFFICENT";
		}
		return "no string";
	}

}
