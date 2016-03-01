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
        private final int PING_PIN = 13;
	
	public void run() { //Called by runner every time
		
		//First, set up arduino
		ben = new ArduinoUno();// Create RXTXRobot object 
		ben.setPort("COM3"); // Set the port to COM3 
		ben.setVerbose(true); // Turn on debugging messages 
		ben.connect(); 
                
		// Get the average thermistor reading and pring results
		int thermistorReading = getThermistorReading();
		out.println("The probe read the value: " + thermistorReading);
		out.println("In volts: " + (thermistorReading * (5.0/1023.0)));

		//Next, select starting location
		sc = new Scanner(System.in);
		out.println("Input starting location [1-4] :: "); //Request for location
		int pathNumber = sc.nextInt(); sc.nextLine(); //pathNumber now holds starting location
                
                //sensorTestingZone();
                
		switch (pathNumber) {
		case 1:
			runPath1(); //Run motors until it hits a bump sensor
			break;
		case 2:
			runPath2(); //Drive forward 3 meters
			break;
		case 3:
			runPath3();//Ping sensor test
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
	private void runPath1() { //Run motors until it hits a bump sensor
                
                ben.runMotor(RXTXRobot.MOTOR1, 500, RXTXRobot.MOTOR2, 500, 0); // Run both motors forward indefinitely 
                AnalogPin bumpSense = ben.getAnalogPin(2); //Bump sensor
		do {
                    
                } while (bumpSense.getValue() != 0); //while no bump detected, keep going
                ben.runMotor(RXTXRobot.MOTOR1,0,RXTXRobot.MOTOR2,0,0); // Stop both motors         
		ben.close(); 
	}
	
	private void runPath2() { //Drive forward 3 meters
		
		ben.close(); 
	}

	private void runPath3() { //Ping sensor test
                
		for (int x=0; x < 3; ++x) 
		{ 
			//Read the ping sensor value, which is connected to pin 12
                        ben.refreshDigitalPins();
			out.println("Response: " + ben.getPing(PING_PIN) + " cm");
			ben.sleep(300); 
		} 

		ben.close(); 
	}

	private void runPath4() {
		
		ben.close(); 
	}
	
	private int getThermistorReading(){
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
		 //return adjThermistorReading(sum / readingCount);
                 return(sum / readingCount);
	}
	
	private int adjThermistorReading(double reading) {
		double adjReading = reading; //make reading double
		double intercept = 1.0; 
		double slope = 1.0;
		
		
		adjReading = (adjReading - intercept) / slope;
		
		return (int)adjReading;
	}
}
