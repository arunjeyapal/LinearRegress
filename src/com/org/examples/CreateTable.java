package com.org.examples;

import com.org.lregression.DBoperations;

/**
 * @author arunjayapal
 *
 */
public class CreateTable {
	/**
	 * @param args
	 */
	public static void main(String args[]){
		String tableName = "HousingPriceData";
		DBoperations db = new DBoperations();
		db.createTable(tableName);
		db.closeDatabase();
	}
}
