import rxtxrobot.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.awt.*;
import java.math.*;
import java.util.regex.*;
import static java.lang.System.*;
import static java.lang.Integer.*;
import static java.lang.Double.*;
import static java.lang.Character.*;
import static java.util.Collections.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;



public class Bender {

	private Scanner sc;
	private RXTXRobot ben;
	
	
	public void run() { //Called by runner every time
		
		//First, set up arduino
		ben = new ArduinoUno();// Create RXTXRobot object 
		ben.setPort("COM3"); // Set the port to COM3 
		ben.setVerbose(true); // Turn on debugging messages 
		ben.connect(); 
		ben.attachServo(RXTXRobot.SERVO1, 9); //Connect the servos to the Arduino 
		ben.attachServo(RXTXRobot.SERVO2, 10); 
		
		// Get the average thermistor reading and pring results
		int thermistorReading = getThermistorReading();
		System.out.println("The probe read the value: " + thermistorReading);
		System.out.println("In volts: " + (thermistorReading * (5.0/1023.0)));

		//Next, select starting location
		sc = new Scanner(System.in);
		out.println("Input starting location [1-4] :: "); //Request for location
		int pathNumber = sc.nextInt(); sc.nextLine(); //pathNumber now holds starting location
		switch (pathNumber) {
		case 1:
			runPath1();
			break;
		case 2:
			runPath2();
			break;
		case 3:
			runPath3();
			break;
		case 4:
			runPath4();
			break;
		default:
			out.println("ERROR: INVALID PATH NUMBER!");
			System.exit(0);
			break;
		}
			
	}
	private void runPath1() {
		
		ben.close(); 
	}
	
	private void runPath2() {
		
		ben.close(); 
	}

	private void runPath3() {
		
		ben.close(); 
	}

	private void runPath4() {
		
		ben.close(); 
	}
	
	private int getThermistorReading() {
		 int sum = 0;
		 int readingCount = 10;

		 //Read the analog pin values ten times, adding to sum each time
		 for (int i = 0; i < readingCount; i++) {

			 //Refresh the analog pins so we get new readings
			 ben.refreshAnalogPins();
			 int reading = ben.getAnalogPin(0).getValue();
			 sum += reading;
		 }

		 //Return the average reading
		 return sum / readingCount;
	}
	
	private int adjThermistorReading(int reading) {
		double adjReading = reading; //make reading double
		double intercept = 1.0; 
		double slope = 1.0;
		
		
		adjReading = (adjReading - intercept) / slope;
		
		return (int)adjReading;
	}
}
