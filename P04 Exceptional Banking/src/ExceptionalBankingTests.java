////////////////////ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           Exceptional Banking
// Files:           TransactionGroup.java, Account.java,
//					ExceptionalBankingTests.java
// Course:          CS300, Fall 2018
//
// Author:          Stephen Fan
// Email:           sfan54@wisc.edu
// Lecturer's Name: Alexi Brooks
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
//Students who get help from sources other than their partner must fully 
//acknowledge and credit those sources of help here.  Instructors and TAs do 
//not need to be credited here, but tutors, friends, relatives, room mates, 
//strangers, and others do.  If you received no outside help from either type
//of source, then please explicitly indicate NONE.
//
// Persons:         NONE
// Online Sources:  NONE
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.io.File;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

public class ExceptionalBankingTests {
	/**
	 * This method focuses on checking the correct functionality for 
	 * the getCurrentBalance method resulting from valid inputs or command 
	 * sequences.
	 * @return true when test verifies correct functionality, and false 
	 * otherwise.
	 */
	public static boolean testAccountBalance() { 
		//initialize variables
		boolean success = false;
		Account acc = new Account("Stephen");
		
		//no exception should occur here
		try {
			acc.addTransactionGroup("0 1 0 0 0 0 1 0");
			acc.addTransactionGroup("1 21 -5 -7 8");
			acc.addTransactionGroup("2 1 1 1 1");
		}
		catch (DataFormatException e) {
			System.out.println("DataFormatException has been thrown");
		}

		//checks that the balance is the correct amount
		int balance = acc.getCurrentBalance();
		if (balance == -226) {
			success = true;
		}
		return success;
	}
	
	/**
	 * This method focuses on checking the correct functionality for 
	 * the getNumberOfOverdrafts method resulting from valid inputs or 
	 * command sequences.
	 * @return true when test verifies correct functionality, and false 
	 * otherwise.
	 */
	public static boolean testOverdraftCount() {
		//initialize variables
		boolean success = false;
		Account acc = new Account("Stephen");
		
		//no exception should occur here
		try {
			acc.addTransactionGroup("0 0 1 0 0");
			acc.addTransactionGroup("1 20 5 -24");
			acc.addTransactionGroup("2 1 1 1 1");
		}
		catch (DataFormatException e) {
			System.out.println("DataFormatException has been thrown");
		}
		
		//checks that the overdraft count is the correct amount
		int overdraftCount = acc.getNumberOfOverdrafts();
		if (overdraftCount == 7) {
			success = true;
		}
		return success;
	}
	
	/**
	 * This method checks whether the TransactionGroup constructor throws an 
	 * exception with an appropriate message, when it is passed an empty int[]
	 * @return true when test verifies correct functionality, and false 
	 * otherwise.
	 */
	public static boolean testTransactionGroupEmpty() { 
		boolean success = false;
		int[] emptyarray = new int[0];
		
		//exception should be thrown here
		try {
			TransactionGroup trans = new TransactionGroup(emptyarray);
		}
		catch (DataFormatException e) {
			System.out.println(e.getMessage());
			success = true;
		}
		
		return success;
		}
	 
	/**
	 * This method checks whether the TransactionGroup constructor throws an 
	 * exception with an appropriate message, when then first int in the 
	 * provided array is not 0, 1, or 2.
	 * @return true when test verifies correct functionality, and false 
	 * 		otherwise.
	 */
	public static boolean testTransactionGroupInvalidEncoding() {
		boolean success = false;
		int[] invalidarray = new int[] {7,1,0};
		
		//exception should be thrown here
		try {
			TransactionGroup trans = new TransactionGroup(invalidarray);
		}
		catch (DataFormatException e) {
			System.out.println(e.getMessage());
			success = true;
		}
		
		return success;
	}
	 
	/**
	 * This method checks whether the Account.addTransactionGroup method
	 * throws an exception with an appropriate message, when it is passed
	 * a quick withdraw encoded group that contains negative
	 * numbers of withdraws.
	 * @return true when test verifies correct functionality, and false 
	 * 		otherwise.
	 */
	public static boolean testAccountAddNegativeQuickWithdraw() {
		boolean success = false;
		Account acc = new Account("Stephen");
		
		//exception should be thrown here
		try {
			acc.addTransactionGroup("2 -1 0 -5 2");
		}
		catch (DataFormatException e) {
			System.out.println(e.getMessage());
			success = true;
		}
		
		return success;
	}
	 
	/**
	 * This method checks whether the Account.addTransactionGroup method
	 * throws an exception with an appropriate message, when it is passed
	 * a string with space separated English words (non-int).
	 * @return true when test verifies correct functionality, and false 
	 * 		otherwise.
	 */
	public static boolean testAccountBadTransactionGroup() {
		boolean success = false;
		Account acc = new Account("Stephen");
		
		//exception should be thrown here
		try {
			acc.addTransactionGroup("bad code causes programs to fail");
		}
		catch (DataFormatException e) {
			System.out.println(e.getMessage());
			success = true;
		}
		
		return success;
	}
	 
	/**
	 * This method checks whether the Account.getTransactionAmount method 
	 * throws an exception with an appropriate message, when it is passed 
	 * an index that is out of bounds.
	 * @return true when test verifies correct functionality, and false 
	 * 		otherwise.
	 */
	public static boolean testAccountIndexOutOfBounds() {
		boolean success = false;
		Account acc = new Account("Stephen");
		
		//exception should be thrown here
		try {
			acc.getTransactionAmount(12345);
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
			success = true;
		}
		
		return success;
	}
	 
	/**
	 * This method checks whether the Account constructor that takes a 
	 * File parameter throws an exception with an appropriate message, 
	 * when it is passed a File object that does not correspond to an 
	 * actual file within the file system.
	 * @return true when test verifies correct functionality, and false 
	 * 		otherwise.
	 */
	public static boolean testAccountMissingFile() {
		boolean success = false;
		File file = null;
		
		//exception should be thrown here
		try {
			Account acc = new Account(file);
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			success = true;
		}
		catch (DataFormatException e) {
			System.out.println(e.getMessage());
		}
		
		return success;
	}
	
	/**
	 * This method checks whether the Account constructor that takes a 
	 * File parameter throws an exception with an appropriate message, 
	 * when it is passed a File object that does not correspond to an 
	 * actual file within the file system.
	 * @return true when test verifies correct functionality, and false 
	 * 		otherwise.
	 */
	public static boolean testAccountNotNullFile() {
		boolean success = false;
		File file = new File("NotNull");
		
		//exception should be thrown here
		try {
			Account acc = new Account(file);
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			success = true;
		}
		catch (DataFormatException e) {
			System.out.println(e.getMessage());
		}
		
		return success;
	}
	
	//main method to run test methods
	public static void main (String[] args) {
		boolean test1 = testAccountBalance();
		boolean test2 = testOverdraftCount();
		boolean test3 = testTransactionGroupEmpty();
		boolean test4 = testTransactionGroupInvalidEncoding();
		boolean test5 = testAccountAddNegativeQuickWithdraw();
		boolean test6 = testAccountBadTransactionGroup();
		boolean test7 = testAccountIndexOutOfBounds();
		boolean test8 = testAccountMissingFile();
		boolean test9 = testAccountNotNullFile();
		if (test1 == true && test2 == true && test3 == true && test4 == true
				&& test5 == true && test6 == true && test7 == true && 
				test8 == true && test9 == true) {
			System.out.println("All tests passed");
		}
	}
}
