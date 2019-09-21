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

import java.util.zip.DataFormatException;

public class TransactionGroup {
  
  private enum EncodingType { BINARY_AMOUNT, INTEGER_AMOUNT, QUICK_WITHDRAW };
  private EncodingType type;
  private int[] values;

  /**
   * constructs a transaction group given an integer array
   * also checks to make sure the given array is valid
   * @param groupEncoding is an int array that will be used to
   * 		create a transaction group
   * @throws DataFormatException when the data input is not formatted
   * 		correctly
   */
  public TransactionGroup(int[] groupEncoding) throws DataFormatException {
	  
	//checks each case in which a dataformatexception can be thrown
	  
	if (groupEncoding == null || groupEncoding.length == 0) {
		throw new DataFormatException("transaction group encoding cannot be"
				+ " null or empty");
	}
	
	if (groupEncoding[0] != 0 && groupEncoding[0] != 1 && groupEncoding[0]
	  != 2)  {
		throw new DataFormatException("the first element within a trans"
				+ "action group must be 0, 1, or 2");
	}
	  
	if (groupEncoding[0] == 0)  {
		int numBinary = 0;
		for (int i = 0; i < groupEncoding.length; i++) {
			if (groupEncoding[i] == 0 || groupEncoding[i] == 1) {
				numBinary++;
			}
		}
		if (numBinary != groupEncoding.length) {
			throw new DataFormatException("binary amount transaction "
					+ "groups may only contain 0s and 1s");
		}
	}
	  
	  if (groupEncoding[0] == 1) {
		  for (int i = 0; i < groupEncoding.length; i++) {
			  if (groupEncoding[i] == 0) {
				  throw new DataFormatException("integer amount trans"
				  		+ "action groups may not contain 0s");
			  }
		  }
	  }
	  
	  if (groupEncoding[0] == 2 && groupEncoding.length != 5) {
		  throw new DataFormatException("quick withdraw transaction"
		  		+ " groups must contain 5 elements");
	  }
	  
	  if (groupEncoding[0] == 2) {
		  for (int i = 0; i < groupEncoding.length; i++) {
			  if (groupEncoding[i] < 0) {
				  throw new DataFormatException("quick withdraw trans"
				  		+ "action groups may not contain negative numbers");
			  }
		  }
	  }
	  
	this.type = EncodingType.values()[groupEncoding[0]];
    this.values = new int[groupEncoding.length-1];
    for(int i=0;i<values.length;i++)
      this.values[i] = groupEncoding[i+1];
  }
  
  /**
   * calculates the number of transactions based on the transaction type
   * note: BINARY_AMOUNT transactions calculations are different than in
   * the previous banking program
   * @return the number of transactions
   */
  public int getTransactionCount() {
    int transactionCount = 0;
    switch(this.type) {
      case BINARY_AMOUNT:
        int lastAmount = -1;
        for(int i=0;i<this.values.length;i++) {
          if(this.values[i] != lastAmount) {
            transactionCount++; 
            lastAmount = this.values[i];            
          }
        }
        break;
      case INTEGER_AMOUNT:
        transactionCount = values.length;
        break;
      case QUICK_WITHDRAW:
        for(int i=0;i<this.values.length;i++)
          transactionCount += this.values[i];        
    }
    return transactionCount;
  }
  
  /**
   * calculates the transaction amount of a given transaction index
   * note: BINARY_AMOUNT transactions calculations are different than in
   * the previous banking program
   * @param transactionIndex is the index of the transaction that the user
   * 		would like to get
   * @throws IndexOutOfBoundsException when the requested index is out of
   * 		bounds
   * @return the transaction amount
   */
  public int getTransactionAmount(int transactionIndex) throws 
  IndexOutOfBoundsException{
	if (transactionIndex < 0 || transactionIndex > this.values.length) {
		throw new IndexOutOfBoundsException("The index " + transactionIndex
				+ " is not within the range of valid indexes. There are "
				+ "only " + this.values.length + " total transactions");
	}
	  
	int transactionCount = 0;
    switch(this.type) {
      case BINARY_AMOUNT:
        int lastAmount = -1;
        int amountCount = 0;
        for(int i=0;i<=this.values.length;i++) {
          if(i == this.values.length || this.values[i] != lastAmount)  { 
            if(transactionCount-1 == transactionIndex) {
              if(lastAmount == 0)
                return -1 * amountCount;
              else
                return +1 * amountCount;
            }
            transactionCount++;       
            lastAmount = this.values[i];
            amountCount = 1;
          } else
            amountCount++;
          lastAmount = this.values[i];
        } 
        break;
      case INTEGER_AMOUNT:
        return this.values[transactionIndex];
      case QUICK_WITHDRAW:
        final int[] QW_AMOUNTS = new int[] {-20, -40, -80, -100};
        for(int i=0;i<this.values.length;i++) 
          for(int j=0;j<this.values[i];j++)
            if(transactionCount == transactionIndex) 
              return QW_AMOUNTS[i]; 
            else 
              transactionCount++;
    }
    return -1;
  }  
}