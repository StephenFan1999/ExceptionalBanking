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
import java.util.Scanner;

public class Account {
  //initialize variables
  private static final int MAX_GROUPS = 10000;
  private static int nextUniqueId = 1000; 
  
  private String name;
  private final int UNIQUE_ID; 
  private TransactionGroup[] transactionGroups;
  private int transactionGroupsCount; 
  
  /**
   * creates an Account object with 10000 spaces given a name
   * @param name which is the name of the account
   */
  public Account(String name) {
   //initialize variables
   this.name = name;
   this.UNIQUE_ID = Account.nextUniqueId;
   Account.nextUniqueId++;
   this.transactionGroups = new TransactionGroup[MAX_GROUPS];
   this.transactionGroupsCount = 0;
  }
  
  /**
   * creates an Account object with 10000 spaces given a file
   * @param file is the file that the user uses to create an Account object
   * @throws DataFormatException when data is in the wrong format
   * 		or is invalid
   * @throws FileNotFoundException when the file used to create an
   * 		Account object is not found
   */
  public Account(File file) throws DataFormatException, FileNotFoundException{
    // NOTE: THIS METHOD SHOULD NOT BE CALLED MORE THAN ONCE, BECAUSE THE
    // RESULTING BEHAVIOR IS NOT DEFINED WITHIN THE JAVA SPECIFICATION ...
	// ... THIS WILL BE UPDATED TO READ FROM A FILE INSTEAD OF SYSTEM.IN.
	
	//checks if file is null and throws exception
	if (file == null) {
		throw new FileNotFoundException("FileNotFoundException has "
				+ "been thrown");
	}
	
	//initialize variables
	Scanner in;
	
	try {
		in = new Scanner(file);
	}
	catch (FileNotFoundException e){
		throw e;
	}
	
    this.name = in.nextLine();
    this.UNIQUE_ID = Integer.parseInt( in.nextLine() );
    Account.nextUniqueId = this.UNIQUE_ID + 1;
    this.transactionGroups = new TransactionGroup[MAX_GROUPS];
    this.transactionGroupsCount = 0;    
    String nextLine = "";
    
    while(in.hasNextLine()) {
    	try {
    		this.addTransactionGroup(in.nextLine());
    	}
    	catch (DataFormatException exc) {
    		System.out.println("DataFormatException has been thrown");
    	}
    in.close();
    }
  }

  /**
   * accessor method to get the UNIQUE_ID variable
   * @return UNIQUE_ID to be used for another purpose
   */
  public int getId() { 
    return this.UNIQUE_ID;
  }

  /**
   * method to add a transaction group given a string input
   * @param command is a string of integers separated by spaces
   * @throws DataFormatException when data is in the wrong format/invalid
   */
  public void addTransactionGroup(String command) throws DataFormatException{
    //checks that command is separated by at least one space
	if (!command.contains(" ")) {
		throw new DataFormatException("addTransactionGroup requires "
				+ "string commands that contain only space separated"
				+ " integer values");
    }
	//checks that command contains only integers and no strings
    else {
    	String[] splitcommand = command.split(" ");
    	int[] intsplitcommand = new int[splitcommand.length];
    	for (int i = 0; i < splitcommand.length; i++) {
    		try {
    			intsplitcommand[i] = Integer.parseInt(splitcommand[i]);
    		}
    		catch (NumberFormatException numexc) {
    			throw new DataFormatException("addTransactionGroup requires "
    					+ "string commands that contain only space separated"
    					+ " integer values");
    		}
    	}
    }
	
	//checks that there is enough memory to add the transaction group
    if (transactionGroupsCount == transactionGroups.length) {
    	throw new OutOfMemoryError("The capacity of storage is " +
    			transactionGroups.length + "and it is full");
    }
	String[] parts = command.split(" ");
    int[] newTransactions = new int[parts.length];
    for (int i = 0; i < parts.length; i++)
      newTransactions[i] = Integer.parseInt(parts[i]);
    TransactionGroup t = new TransactionGroup(newTransactions);
    this.transactionGroups[this.transactionGroupsCount] = t;
    this.transactionGroupsCount++;
  }
  
  /**
   * gets the transaction count
   * @return the number of transaction counts
   */
  public int getTransactionCount() {
    int transactionCount = 0;
    for(int i=0;i<this.transactionGroupsCount;i++)
      transactionCount += this.transactionGroups[i].getTransactionCount();
    return transactionCount;
  }

  /**
   * gets the total transaction amount of a given index
   * @param index is the index that the user wants the transaction amount of
   * @return an int that is the transaction amount
   * @throws IndexOutOfBoundsException
   */
  public int getTransactionAmount(int index) throws IndexOutOfBoundsException{    
	if (index < 0 || index > this.transactionGroups.length) {
		throw new IndexOutOfBoundsException("The index " + index
				+ " is not within the range of valid indexes. There are "
				+ "only " + this.transactionGroups.length + " total "
						+ "transactions");
	}
	  
	
    int transactionCount = 0;
    for(int i=0;i<this.transactionGroupsCount;i++) {
      int prevTransactionCount = transactionCount;
      transactionCount += this.transactionGroups[i].getTransactionCount();
      if(transactionCount > index) {
        index -= prevTransactionCount;
        return this.transactionGroups[i].getTransactionAmount(index);
      }
    }
    return -1;
  }
  
  /**
   * gets the current balance in the account
   * @return balance is the current balance
   */
  public int getCurrentBalance() {
    int balance = 0;
    int size = this.getTransactionCount();
    for(int i=0;i<size; i++)
      balance += this.getTransactionAmount(i);
    return balance;
  }
  
  /**
   * gets the number of overdrafts of the account
   * @return overdraftCount is an int that is the number of overdrafts
   */
  public int getNumberOfOverdrafts() {
    int balance = 0;
    int overdraftCount = 0;
    int size = this.getTransactionCount();
    for(int i=0;i<size; i++) {
      int amount = this.getTransactionAmount(i); 
      balance += amount;
      if(balance < 0 && amount < 0)
        overdraftCount++;
    }
    return overdraftCount;
  }
    
}