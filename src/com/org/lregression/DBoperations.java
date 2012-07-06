package com.org.lregression;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*;;

/**
 * Different database operations such as create table and insert table are performed using DBoperations class
 * 
 * @author arunjayapal
 */
public class DBoperations {
	Connection conn = null;
	Statement s = null;
	
	/**
	 * constructor to initialise the MySQL database connection 
	 */
	public DBoperations(){
	    try
	    {
	        String userName = "root";
	        String password = "Jayanthi27";
	        String url = "jdbc:mysql://localhost/housingPriceData";
	        Class.forName ("com.mysql.jdbc.Driver").newInstance ();
	        conn = DriverManager.getConnection (url, userName, password);
	        System.out.println ("Database connection established");
	        s = conn.createStatement();
	    }
	    catch (Exception e)
	    {
	        System.err.println ("Cannot connect to database server");
	    }
	}
	
	/**
	 * Method to create table
	 * 
	 * @param tableName is passed as parameter for this method
	 */
	public void createTable(String tableName){
		
		try {
			s.executeUpdate("CREATE TABLE "+tableName+"(SIZE_OF_HOUSE FLOAT NOT NULL, NO_OF_BEDROOMS FLOAT NOT NULL, PRICE FLOAT NOT NULL)");
			System.out.println("Table created Successfully");
		} catch (SQLException e) {
			System.out.println("Error creating table");
			e.printStackTrace();
		}
	}
	
	/**
	 * insertTable method is used to insert values to a given table. 
	 * Here three parameters or 3 values are inserted into the given table.
	 * 
	 * @param tableName - Name of the table into which the values will be inserted 
	 * @param val1 - that needs to be inserted into the table
	 * @param val2 - that needs to be inserted into the table
	 * @param val3 - that needs to be inserted into the table
	 */
	public void insertTable(String tableName, double val1, double val2, double val3){
		try {
			int val = s.executeUpdate("INSERT "+ tableName +" VALUES("+val1+","+val2+","+val3+")");
			System.out.println(val + " row data inserted");
		} catch (SQLException e) {
			System.out.println("Error inserting values into the table");
			e.printStackTrace();
		}
	}
	
	/**
	 * insertTable method overloaded here, which accepts an array of values. 
	 * This is provided since the user might have any number of features 
	 * 
	 * @param tableName - Name of the table into which the values will be inserted
	 * @param values - accepts an array of values to be stored in a database
	 */
	public void insertTable(String tableName, double... values){
		
		String value = "INSERT "+tableName+" VALUES(";
		int numofValues = values.length, count = 1;
		
		for(@SuppressWarnings("unused") double v:values){
			if (count < numofValues){
				value = value + ",";
			}
			if(count == numofValues){
				value = value + ")";
			}
			count++;
		}
		
		try {
			int val = s.executeUpdate(value);
			System.out.println(val + " row data inserted");
		} catch (SQLException e) {
			System.out.println("Error inserting values into the table");
			e.printStackTrace();
		}
	}
	
	/**
	 * @param tableName - Enter the table name from where the data needs to be fetched
	 * @return result of the given query
	 * Method used to select data from database.
	 */
	public ResultSet execQuery(String tableName){
		ResultSet res = null;
		try {
			res = s.executeQuery("SELECT * FROM "+ tableName);
		} catch (SQLException e) {
			System.out.println("Error in Select Query");
			e.printStackTrace();
		}
		return res;
		
	}
	
	/**
	 * After all the database operations are over,
	 * the database connection is terminated.
	 */
	public void closeDatabase(){
		
        if (conn != null)
        {
            try
            {
                conn.close ();
                System.out.println ("Database connection terminated");
            }
            catch (Exception e) { /* ignore close errors */ }
        }
	}
}
