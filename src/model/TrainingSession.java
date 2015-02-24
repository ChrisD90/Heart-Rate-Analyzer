/**
 * 
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Christoph
 * 
 */
public class TrainingSession {

	/*
	 * parameters that describe a training session input by externs
	 */
	public String date;
	public int age;
	public String sex;
	public int size;
	public double mass;
	public double avgHR;
	public double avgWatt;
	public int time;

	/*
	 * parameters resutling from delivered parameters
	 */
	public int trainingHR;
	public double powerFactor;
	public double bmi;
	public double avgQoutient;

	/*
	 * a string for display a sessions to the user
	 */
	public String session;
	public String timeString;

	/**
	 * writes the latest session to log file
	 * 
	 * @param path
	 */
	@SuppressWarnings("resource")
	public void writeToLog(String path, String userName) {
		System.out.println("TrainingSession-writeToLog()");

		try {
			Path userDataPath = Paths.get(System.getProperty("user.home"))
					.resolve("Documents").resolve("MyAnalyzer")
					.resolve("Sessions").resolve(userName)
					.resolve(userName + "_Log-Data.csv");
			String dataPath = userDataPath + "";

			BufferedReader br = new BufferedReader(new FileReader(path));

			String current;
			current = br.readLine();

			File file = new File(dataPath);
			FileWriter fw = null;

			if (file.exists())
				System.out
						.println("Data already exists. \nNew information will be appended!");
			else
				System.out.println("Data generated");

			fw = new FileWriter(file.getPath(), true);

			PrintWriter pw = new PrintWriter(fw);
			pw.println(current);
			System.out.println("Written to Log: \n" + current + "\n");

			fw.flush();
			fw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * prints the session on console: -> Date of
	 * Session;Age;Sex;Size;Weight;avgHR;avgWatt
	 */
	public String printSession() {
		System.out.println("TrainingSession-printSession()");
		System.out.println("Date:   " + getDate());
		System.out.println("Age:    " + getAge());
		System.out.println("Sex:    " + getSex());
		System.out.println("Size:   " + getSize() + "cm");
		System.out.println("Mass:   " + getMass() + "kg");
		System.out.println("avgHR:  " + getAvgHR() + "bpm");
		System.out.println("avgPw:  " + getAvgWatt() + "watts");
		System.out.println("Time:	" + timeString);

		String s1 =		"Analyzed session: ";
		String s2 = 	"\nDate:.............." + getDate();
		String s3 = 	"\nAge:..............." + getAge();
		String s4 = 	"\nSex:................" + getSex();
		String s5 = 	"\nSize:..............." + getSize() + "cm";
		String s6 = 	"\nMass:.............." + getMass() + "kg";
		String s65 = 	"\nBMI:................." + getBmi();
		String s655 = 	"\nperfect HR:.."
				+ "." + getTrainingHR() + "bpm";
		String s7 = 	"\navgHR:..........." + getAvgHR() + "bpm";
		String s8 = 	"\navgPw:..........." + getAvgWatt() + "watts";
		String s9 = 	"\nTime:.............." + timeString;

		return s1 +s2 + s3 + s4 + s5 + s6 + s65 + s655 + s7 + s8 + s9;

	}

	/*
	 * GETTERS
	 * ------------------------------------------------------------------
	 * --------------------
	 * ------------------------------------------------------
	 * --------------------------------
	 */
	public String getDate() {
		return date;
	}

	public int getAge() {
		return age;
	}

	public String getSex() {
		return sex;
	}

	public int getSize() {
		return size;
	}

	public double getMass() {
		return mass;
	}

	public double getAvgHR() {
		return avgHR;
	}

	public double getAvgWatt() {
		return avgWatt;
	}

	public int getTrainingHR() {
		return trainingHR;
	}

	public double getPowerFactor() {
		return powerFactor;
	}

	public double getBmi() {
		return bmi;
	}

	public double getAvgQoutient() {
		return avgQoutient;
	}

	public String getSession() {
		return session;
	}

	public int getTime() {
		return time;
	}

	public String getTimeString() {
		return timeString;
	}

	/*
	 * SETTERS
	 * ------------------------------------------------------------------
	 * --------------------
	 * ------------------------------------------------------
	 * --------------------------------
	 */
	public void setDate(String date) {
		this.date = date;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public void setAvgHR(double avgHR) {

		this.avgHR = avgHR;
	}

	public void setAvgWatt(double avgWatt) {
		this.avgWatt = avgWatt;
	}

	/**
	 * maxHR nach Hottenrott Trainings-Herzfrequenz = Maximale Herzfrequenz
	 * (HFmax) x 0,7 x Fitnesslevel (LFi) x Ziel (Tzi) x Geschlecht (GFi) x
	 * Sportartfaktor (SPi) Wir gehen von folgendem aus: - maxHF eines
	 * Erwachsenen - Fitnesslevel abhängig von bmi - Ziel: HKS-Training -
	 * Geschlecht: m/f - Sportfaktor: 1 (für Laufen) - (...)-10 da es sich um
	 * ein Ergometertraining handelt 
	 */
	public void setTrainingHR() {
		double sexValue;

		if (sex.equals("m")) {
			sexValue = 1;

		} else {
			sexValue = 1.1;
		}

		trainingHR = (int) ((208 - (0.7 * age)) * 0.7 * powerFactor * 1.1 * sexValue * 1) -10;

	}

	public void setPowerFactor() {

		if (bmi < 19 || bmi > 24) {
			powerFactor = 1;
		} else {
			powerFactor = 1.03;
		}
	}

	/**
	 * calculates the BMI based - mass/size in m²
	 */
	public void setBmi() {

		double height = size;
		bmi = mass / ((height * height) / 10000);
	}

	/**
	 * this qoutient makes it possible to compare the latest session with the
	 * avergae of older sessoins with an pretty good value
	 */
	public void setAvgQoutient() {

		double hr = avgHR;
		double watt = avgWatt;

		avgQoutient = hr / watt;
	}

	/**
	 * prepares a String to display
	 */
	public void setSession() {
		System.out.println("TrainingSession-setSession()");
		bmi = Math.round(bmi*100)/100;
		trainingHR = Math.round(trainingHR*100)/100;
		
		session = "Date:	 " + getDate() + "\nAge:	 " + getAge() + "\nSex:	 "
				+ getSex() + "\nSize:	 " + getSize() + "\nMass:	 " + getMass()
				+ "\nBMI:	 " + getBmi() + "\ntraining HR:	 " + getTrainingHR()
				+ "\navgHR:	 " + getAvgHR() + "\navgPw:	 " + getAvgWatt()
				+ "\nHR/Watt: 	 " + getAvgQoutient() + "\nTime:	 "
				+ timeString + "\n-------------------------------------------------------------";
	}

	/**
	 * comes in seconds - changed to hour:min:sec for view
	 * 
	 * @param time
	 */
	public void setTime(int time) {
		this.time = time;

		int sek1 = 1;
		int min = (60 * sek1);
		int std = (60 * min);
		int tag = (24 * std);

		int stderg = (time % tag) / (std);
		int minerg = (time % std) / (min);
		int sekerg = (time % min * sek1);

		String hour = stderg + "";
		String mins = minerg + "";
		String sec = sekerg + "";

		timeString = hour + "h:" + mins + "min:" + sec + "sec";

	}
	/*
	 * --------------------------------------------------------------------------
	 * ------------
	 * --------------------------------------------------------------
	 * ------------------------
	 */
}
