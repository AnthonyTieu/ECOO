//Special Thanks to My Friend Anthony M. for Introducing Me to the Switch/Case Structure!
//The Revised Solution is Heavily Based on His Solution!
//IMPORT PACKAGES
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.*;

//CLASS
public class Problem1_TakeANumber 
{
	//DECLARATION AND INITIALIZATION
	private static String welcomeMessage = "Welcome to Ticket Calculator!"; //Welcome message
	private static String loadingMessage = "Please Wait, System is Loading"; //Loading Message
	private static String loadingFinishedMessage = "Thank You for Your Patience!"; //Finished loading message
	private static String columnOneDescription = "\nColumn One: Number of Late Students"; //Column one content description
	private static String columnTwoDescription = "Column Two: Number of Students Served After Closing"; //Column two content description
	private static String columnThreeDescription = "Column Three: Starting Ticket Number for Tomorrow\n"; //Column three content description
	private static String closingMessage = "Thank You for Using Ticket Calculator!"; //Closing message
	private static int loadingDotDuration = 400; //Duration (in milliseconds) for dots on loading screen
	private static int currentValue = 0; //Starting ticket value for the day 
	private static int numberOfTaken = 0; //Number of tickets taken during the day
	private static int numberOfUnserved = 0; //Number of students who were not served during the day
	private static int maxNumberOfTickets = 999; //Maximum number of tickets
	
	//***********
	//MAIN METHOD
	//***********
	public static void main(String [] args) throws Exception
	{
		welcomeScreen(); //Start welcome screen
		program(); //Start program
		closingScreen(); //Start closing Screen
	}
	
	//**************
	//WELCOME SCREEN
	//**************
	public static void welcomeScreen() throws Exception
	{ 	
		System.out.println(welcomeMessage); //Print welcome message
		pause(); //Pause 
		System.out.print(loadingMessage); //Print loading message (nothing actually happens)
		loadingAnimation(); //Play loading animation (nothing actually happens)
	}
	
	//*****************
	//LOADING ANIMATION
	//*****************
	public static void loadingAnimation() throws Exception
	{
		//Loop for number of dot animation cycles
		for (int numberOfIterations = 0; numberOfIterations < 3; numberOfIterations++) 
		{
			pause(); //Pause before printing dots
			for (int numberOfDots = 0; numberOfDots < 3; numberOfDots++) //Loop for number of dots (three)
			{
				System.out.print("."); //Print a dot
				pause(); //Pause a moment before printing the next dot
			}
			for (int numberOfDots = 0; numberOfDots < 3; numberOfDots++) //Loop for removing the dots (three)
			{
				/*Backspace the previous character, print a space
				 *(because we need a character before or after "\b" for it to work) 
				 *Backspace the space*/
				System.out.print("\b \b");
			}
		}
		
		//Once loading is "finished"
		System.out.print("\r" + loadingFinishedMessage); //Overwrite the "loading message" with the "finished loading message"
		/*NOTE: \r is carriage return, it returns to the beginning of the line,
		 *this enables us to write over what has already been written*/
		
		//Cover any other remaining characters from the loading message with a space
		for(int difference = 0; difference < loadingMessage.length() - loadingFinishedMessage.length(); difference++)
		{
			System.out.print(" ");
		}
		
		pause(); //Pause
		System.out.println("\n" + columnOneDescription); //Print column one description
		System.out.println(columnTwoDescription); //Print column two description
		System.out.println(columnThreeDescription); //Print column three description
		pause(); //Pause
	}
		
	//*******
	//PROGRAM
	//*******
	public static void program() throws Exception 
	{
		Scanner fromFile = new Scanner(new File("DATA11.txt")); //"DATA-Problem 1-Take A Number.txt")); // Scanner
		currentValue = fromFile.nextInt(); // Setting the start value

		attendance: while (fromFile.hasNext()) //While there is something to read... 
		{
			switch (fromFile.next()) //Take the next value
			{
			case "TAKE": //If the value is "TAKE"...
				numberOfTaken++; //Increment the number of taken tickets
				numberOfUnserved++; //Increment the number of unserved students
				currentValue++; //Increment the current ticket value
				//If the current ticket value exceeds 999, reset it to 1 (attendance office refills ticket machine)
				if(currentValue > maxNumberOfTickets)
				{
					currentValue = 1;
				}	
				break;
				
			case "SERVE": //If the value is "SERVE"...
				numberOfUnserved--; //Decrement the number of unserved students
				break;
				
			case "CLOSE": //If the value is "CLOSE"...
				//Print values
				System.out.println(numberOfTaken + " " + numberOfUnserved + " " + currentValue); // Print the required values																				// values
				// Reset counter
				numberOfTaken = 0; // Reset number of tickets taken
				numberOfUnserved = 0; // reset number of unserved students
				break;
				
			case "EOF": //If the value is "EOF"...
				fromFile.close(); //Close resource
				break attendance; //End the program
			}
		}

	}
	
	//**************
	//CLOSING SCREEN
	//**************
	public static void closingScreen() throws Exception
	{
		pause(); //Pause
		System.out.println("\n" + closingMessage); //Print closing message
	}
	
	//*****
	//PAUSE
	//*****
	public static void pause() throws Exception
	{
		TimeUnit.MILLISECONDS.sleep(loadingDotDuration); //Sleep for a set length of time
		//Note: sleep may not have perfect accuracy when it comes to time
	}
}


//********
//APPENDIX
//********

//***********************
//Original Program Method
//***********************

/* DO/WHILE LOOP (...because I wanted one)
do 
{
	temp = fromFile.next(); // Read the data from one line (either "TAKE," "SERVE" or "EOF")

	if (temp.contentEquals("TAKE")) // If the value is "TAKE"...
		numberOfTaken++; // Add to the take counter

	else if (temp.contentEquals("SERVE")) // If the value is "SERVE"...
		numberOfServed++; // Add to the serve counter

	else if (temp.contentEquals("CLOSE")) // If the value is "CLOSE"...
	{
		// Prepare to Print
		numberOfUnserved = numberOfTaken - numberOfServed; // Calculate the number of students left to serve
		currentValue += numberOfTaken; // Update the starting ticket value for the next day

		System.out.println(numberOfTaken + " " + numberOfUnserved + " " + currentValue); // Print the required
																							// values

		// Reset counter
		numberOfTaken = 0; // Reset number of tickets taken
		numberOfServed = 0; // Reset number of students served
		numberOfUnserved = 0; // reset number of unserved students
	} 
	else // If the value is "EOF"
	{
		fromFile.close(); //Close fromFile to prevent resource leaks
		break; // End the loop
	}
} 
while (!temp.contentEquals("EOF")); // While the variable "temp" does not equal "EOF," continue to process the file
}*/

//**************************************
//Original Delete Loading Message Method
//**************************************

/*Loop to delete the initial loading message
for(int numberOfCharacters = 0; numberOfCharacters < loadingMessage.length(); numberOfCharacters++) 
{
	System.out.print("\b \b"); //Backspace
}
		
pause(); //Pause
System.out.println(loadingFinishedMessage); //Print finished loading message
*/