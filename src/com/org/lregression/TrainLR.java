package com.org.lregression;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleBase;
import org.ejml.simple.SimpleMatrix;

/**
 * @author arunjayapal
 */

public class TrainLR {
	
	static DBoperations db = new DBoperations(); //Database operations class instantiated
	
	/**
	 * @param args
	 * @throws SQLException
	 * @throws IOException
	 * 
	 * Main method to train using Linear regression. 
	 * Get data from Database and initialise the data.
	 * Run Gradient descent algorithm and store Theta as model.
	 */
	public static void main(String args[]) throws SQLException, IOException{
		
		double[][] data = getData();
		db.closeDatabase();
		
		/*-----------------------------------------------*/
		/*Get Data from Database and Initialise the data */
		/*-----------------------------------------------*/
		DenseMatrix64F newData = new DenseMatrix64F(data);
		SimpleMatrix trainingData = SimpleMatrix.wrap(newData);
		/*retrieve column vector*/
		//SimpleMatrix X = (SimpleMatrix) trainingData.extractVector(false, 0);
		int numTrainingData = trainingData.numRows();
		int numCols = trainingData.numCols();
		SimpleMatrix X = (SimpleMatrix) trainingData.extractMatrix(0, numTrainingData, 0, numCols-1);
		SimpleMatrix y = (SimpleMatrix) trainingData.extractVector(false, numCols-1);
		
		/*Feature Normalisation*/
		FeatureNormalize fn = new FeatureNormalize(X);
		X = fn.FNormalize(); //Normalised features stored in X
		
		SimpleMatrix mean = fn.computeMean(); //Needs to be stored as model
		SimpleMatrix StdDev = fn.computeStdDev(); //Needs to be stored as model
		
		/*-----------------------------------------------*/
		/*---------------Gradient descent----------------*/
		/*-----------------------------------------------*/
		double[] oneArray = new double[trainingData.numRows()];
		for(int i = 0; i<trainingData.numRows(); i++)
			oneArray[i] = 1; 
		
		/*Create a vector of Ones*/
		DenseMatrix64F onesArray = new DenseMatrix64F(trainingData.numRows(),1,true,oneArray);
		SimpleMatrix oneMat = SimpleMatrix.wrap(onesArray);
		SimpleBase<SimpleMatrix> ones = oneMat.extractVector(false, 0);
		
		/*Add a column of ones to x*/
		X = (SimpleMatrix) ones.combine(0, 1, X);
		
		/*Initialise fitting parameters*/
		double[][] thetaArray = new double[X.numCols()][1];
		DenseMatrix64F thetaMat = new DenseMatrix64F(thetaArray);
		SimpleMatrix theta = SimpleMatrix.wrap(thetaMat);
		
		/* Some gradient descent settings*/
		int iterations = 100;
		double alpha = 0.01;
		
		/*compute initial cost*/
		ComputeCost uni = new ComputeCost();
		uni.costCompute(X, y, theta);
		
		/*run gradient descent*/
		System.out.println("Gradient Descent Running...");
		GradientDescent gd = new GradientDescent(X, y, theta, alpha, iterations);
		theta = gd.computeTheta();
		theta.saveToFile("Theta.data");
		mean.saveToFile("Mean.data");
		StdDev.saveToFile("StandardDeviation.data");
		System.out.println("\n\nPredictions performed and models saved in 3 files Theta.data, Mean.data, StandardDeviation.data.\n" +
				"\nTheta.data has to be used for prediction.\n" +
				"Mean.data and StandardDeviation.data has to be used for Feature Normalization for the data to be predicted.\n" +
				"Feature Normalization performed using '[(givenValue - meanValue)/standardDeviationValue]'");
	}
	
	/**
	 * @return Training data retrieved from the database
	 * @throws SQLException
	 */
	public static double[][] getData() throws SQLException{
		String tableName = "HousingPriceData";
		ResultSet res = null;
		res = db.execQuery(tableName);
		int rowVal = 1;
		int colVal = 0;
		int colCount = res.getMetaData().getColumnCount();
		res.last();
		int rowCount = res.getRow();
		double[][] data = new double[rowCount][colCount];
		res.first();
		for(int i = 1; i<=colCount ;i++){
			  String label = res.getMetaData().getColumnLabel(i);
			  data[0][colVal] = res.getFloat(label);
			  colVal = colVal+1;
		}
		while (res.next()) {
			  colVal = 0;
			  for(int i = 1; i<=colCount ;i++){
					  String label = res.getMetaData().getColumnLabel(i);
					  data[rowVal][colVal] = res.getFloat(label);
					  colVal = colVal+1;
				  }
			  rowVal = rowVal+1;
			  }
		
		return data;
	}
	
}
