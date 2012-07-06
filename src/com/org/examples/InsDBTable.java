package com.org.examples;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import com.org.lregression.DBoperations;

/**
 * Class used to insert values to a given table
 * 
 * @author arunjayapal
 */
public class InsDBTable {

	static DBoperations db = new DBoperations(); //Database operations class instantiated
	/**
	 * @param args 0 - Enter the file name which contains the data to be inserted into the database
	 * 					The file will have data which is space separated, with each row containing the training data
	 * 					e.g., 12 40000
	 * 					      13 58990
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		String fileName = "MultiData.txt";
		String tableName = "HousingPriceData";
		DBoperations db = new DBoperations();
		InsDBTable pd = new InsDBTable();
		pd.insertData(tableName, fileName);
		db.closeDatabase();
	}
	
	/**
	 * insertData method is used to insert the data into the required database, 
	 * which can be used further for training using Linear Regression 
	 * 
	 * @param tableName 
	 * @param fileName containing the space separated training data is included  
	 * @throws FileNotFoundException
	 */
	public void insertData(String tableName, String fileName) throws FileNotFoundException{
		//String fileName = "/home/arunjayapal/Public/ProductDevelopment/LinearRegression/LinearRegressionPrototypeMatlab/Data1.txt"
		FileReader file = new FileReader(fileName);
		Scanner I = new Scanner(file);
		String word;
		float aInt;
		int count = 1;
		float val3 = 0;
		float val2 = 0;
		float val1 = 0;
		while(I.hasNext()){
    		word =  I.next();
    		aInt = Float.parseFloat(word.trim());
    		if (count > 3){
    			count = 1;
    		}
    		if (count == 1){
    			val1 = aInt;
    		}
    		else if (count == 2){
    			val2 = aInt;
    		}
    		else{
    			val3 = aInt;
    			db.insertTable(tableName, val1, val2, val3);
    		}
    		count = count + 1;
    		//System.out.println(aInt);
    	}
	}

}
