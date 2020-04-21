//IMPORT PACKAGES
import java.util.*;
import java.io.*;

//CLASS
public class Problem2_TheLuhnAlgorithm
{
	//***********
	//MAIN METHOD
	//***********
	public static void main(String [] args) throws Exception
	{
		//DECLARATION AND INITIALIZATION
		Scanner fromFile = new Scanner(new File("DATA-Problem 2-TheLuhnAlgorithm.txt")); //Scanner
		int testCases = 5; //Number of test cases
		int baseNumbersPerTestCase = 5; //Number of "base numbers" per test case
		String baseNumber; //Base number read from file
		String outputValue = ""; //Output for each test case
		
		//FOR LOOP FOR TEST CASES
		for(int testCaseCounter = 0; testCaseCounter < testCases; testCaseCounter++) //For each test cases...
		{
			//RESET VALUES
			outputValue = ""; //Reset output value
			
			//FOR LOOP FOR THE NUMBER OF BASE NUMBERS IN A TEST CASE
			for(int baseNumberCounter = 0; baseNumberCounter < baseNumbersPerTestCase; baseNumberCounter++) //For each base number...
			{
				baseNumber = fromFile.next(); //Read the base number from the file, store it
				outputValue += determineCheckDigit(baseNumber); //Concatenate the "check digits" for the whole test case
			}
			
			//PRINT STATEMENT
			System.out.println(outputValue); //Print the output value
		}
	}
	
	//*************************
	//DETERMINE THE CHECK DIGIT
	//*************************
	public static int determineCheckDigit(String baseNumber)
	{
		int baseNumberLength = baseNumber.length(); //Calculate and store the length of the base number
		int IDNumberLength = baseNumberLength + 1; //Calculate and store the length of the ID number (base number + check digit)
		int firstDigitSum = 0; //The sum of all the first digits starting from the right
		int secondDigitSum = 0; //The sum of all the second digits starting from the right
		int totalDigitSum = 0; //The sum of the first-digit-sum and the second-digit-sum 
		int checkDigit = 0; //The value of the check digit
		
		/* Every second digit RELATIVE TO THE RIGHT is subject to more math, therefore, we need their locations
		 * Ex. 1
		 * ID NUMBER: 3      8       9      7       9      6       4 
		 * POSITION:  First, Second, First, Second, First, Second, First
		 * LOCATION:  6      5       4      3       2      1       0
		 * LOCATION OF SECOND DIGITS: 1, 3, 5 
		 * 
		 * Ex. 2
		 * ID NUMBER: 3       3      4       3
		 * POSITION:  Second, First, Second, First
		 * LOCATION:  3       2      1       0
		 * LOCATION OF SECOND DIGITS: 1, 3
		 * 
		 * Notice that the location is counted from the right
		 * 
		 * I want the locations of the second digits with respect to the left
		 * 
		 * What I want:
		 * 
		 * Ex. 1
		 * ID NUMBER: 3      8       9      7       9      6       4 
		 * POSITION:  First, Second, First, Second, First, Second, First
		 * LOCATION:  0      1       2      3       4      5       6
		 * LOCATION OF SECOND DIGITS: 1, 3, 5 
		 * 
		 * Ex. 2
		 * ID NUMBER: 3       3      4       3
		 * POSITION:  Second, First, Second, First
		 * LOCATION:  0       1      2       3
		 * LOCATION OF SECOND DIGITS: 0, 2
		 * 
		 * NOTE: In Ex. 2, there is a difference
		 */
		
		/* By modulo division of the length of the ID number, we can determine whether a value is a "second digit"
		 * 
		 * If ID is even in length, then all the even location-values host a "second-digit", these are 0 and 2 
		 * 
		 * As shown in example 2:
		 * ID NUMBER: 3       3      4       3
		 * POSITION:  Second, First, Second, First
		 * LOCATION:  0       1      2       3
		 * 
		 * If ID is odd in length, then all the odd location-values host a "second-digit", as shown in example 1
		 */
		
		int startValue_For_EverySecondDigit_RealitiveToTheLeft = IDNumberLength % 2;
		
		//Loop through the base number
		for(int baseNumberCounter = 0; baseNumberCounter < baseNumberLength; baseNumberCounter++)
		{
			//If it is a second-digit...
			if(baseNumberCounter % 2 == startValue_For_EverySecondDigit_RealitiveToTheLeft)
			{
				//MATH!
				secondDigitSum += calcSecondDigit(baseNumber.substring(baseNumberCounter, baseNumberCounter + 1));
			}
			else //Otherwise
			{
				//No math
				firstDigitSum += calcFirstDigit(baseNumber.substring(baseNumberCounter, baseNumberCounter + 1));
			}
		}
		totalDigitSum = firstDigitSum + secondDigitSum;
		
		/* The "check digit" is the final value, that, when added to the sum, makes it divisible by 10
		 * Thus, we must first modulo the initial sum by 10, the gives us the remainder of the division
		 * Ex. 
		 * 7 % 10 = 7
		 * 18 % 10 = 8
		 * Subtracting the remainder from 10 gives us the value that we need to add to the sum to make it divisible by 10
		 * Ex.
		 * 10 - 7 = 3
		 * 10 - 8 = 2
		 * Finally, we must modulo the difference by 10, this is uniquely for the case where the sum is already divisible by 10:
		 * Ex.
		 * 100 % 10 = 0
		 * 10 - 0 = 10
		 * ...if we left it like that, our answer would be wrong
		 * Therefore, we modulo by 10 once more:
		 * 10 % 10 = 0
		 */
		
		checkDigit = (10 - (totalDigitSum % 10)) % 10;
		
		return checkDigit; //Return the calculated checkDigit
	}
	
	//**************************************
	//CALCULATE THE VALUE OF THE FIRST DIGIT
	//**************************************
	public static int calcFirstDigit(String valueAsString)
	{
		return Integer.parseInt(valueAsString); //Return the String as an Int
	}
	
	//***************************************
	//CALCULATE THE VALUE OF THE SECOND DIGIT
	//***************************************
	public static int calcSecondDigit(String valueAsString)
	{
		//INITIALIZATION AND DECLARATION
		int valueAsInt = Integer.parseInt(valueAsString); //Convert the String value into and Int value
		int calculatedValue = 0;
		
		calculatedValue = (2 * valueAsInt) - 9;
		
		if(calculatedValue < 0)
		{
			/* The original sequence of events was to double the number, then sum its digits,
			 * 
			 * Original Sequence:
			 * Value:               0, 1, 2, 3, 4, 5, 6, 7, 8, 9 
			 * Product of Sequence: 0, 2, 4, 6, 8, 1, 3, 5, 7, 9
			 * 
			 * However, this can be simplified further by using this mathematical sequence
			 * (that I somehow managed to get after staring at the sequence for a long time)
			 * 
			 * Notice in the original sequence that we go from skipping by two through the even values,
			 * then skipping by two in the odd values
			 * 
			 * New Sequence:
			 * A.) (2n - 9)
			 * B.) if the result is negative, modulus divide by 9
			 * C.) else, leave it alone
			 * 
			 * The First Step (2n - 9):
			 * Value of n: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9
			 * 2n - 9:    -9,-7,-5,-3,-1, 1, 3, 5, 7, 9
			 * Notice that this produces a pattern that skips by two
			 * Notice also that any value of "n" above 5 is correct
			 * 
			 * The Second Step (if negative, modulo):
			 * Value of n: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9
			 * 2n - 9:    -9,-7,-5,-3,-1, 1, 3, 5, 7, 9
			 * Modulo:     0, 2, 4, 6, 8
			 * 
			 * Result:     0, 2, 4, 6, 8, 1, 3, 5, 7, 9
			 * 
			 * NOTE: In the actual code, + 9) % 9 is added to the end, this is because
			 * Java does modulo as remainder, which can make negatives
			 * Ex.
			 * -1 % 9 = -1
			 * */
			
			calculatedValue = ((calculatedValue + 9) % 9);
		}
		return calculatedValue;
	}
}

//********
//APPENDIX
//********
/* For an integer "a" with "n" digits,
 * its value lies between (inclusive) 10^(n-1) and 10^n
 * Thus, log10(a) provides gives a number between n-1 and n
 * Flooring this number gives n-1
 * Adding 1 then gives n
 * This is the number of digits in the integer "a"
 * Of course, could have just casted as String and use .length() */

//return ((int)(Math.log10(baseNumber)) + 1);
