package com.org.lregression;

import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;

/**
 * @author arunjayapal
 *
 */
public class FeatureNormalize {
	SimpleMatrix X;
	
	/**
	 * @param X -Training data for which the feature normalisation is applied 
	 */
	public FeatureNormalize(SimpleMatrix X){
		this.X = X;
	}
	
	/**
	 * @return mean for each feature of the training set
	 */
	public SimpleMatrix computeMean(){
		SimpleMatrix mean = null;
		double[][] meanArray = new double[1][X.numCols()] ;
		for (int i=0; i<X.numCols(); i++){
			SimpleMatrix newVect = X.extractVector(false, i);
			meanArray[0][i] = newVect.elementSum()/X.numRows();
		}
		DenseMatrix64F meanMatrix = new DenseMatrix64F(meanArray);
		mean = SimpleMatrix.wrap(meanMatrix);
		return mean;
	}
	
	/**
	 * @return Standard Deviation
	 */
	public SimpleMatrix computeStdDev(){
		SimpleMatrix xVal = computeMean();
		double StdDev[][] = new double[1][X.numCols()];
		
		for (int i=0; i<X.numCols(); i++){
			SimpleMatrix tempX = X.extractVector(false, i);
			double[][] tempVect = new double[X.numRows()][1];
			for(int j=0; j<X.numRows(); j++){
				tempVect[j][0] = xVal.get(i);
			}
			DenseMatrix64F tempMatrix = new DenseMatrix64F(tempVect);
			SimpleMatrix tempMat = SimpleMatrix.wrap(tempMatrix);
			StdDev[0][i] = Math.pow((tempX.minus(tempMat).elementMult(tempX.minus(tempMat)).elementSum()/X.numRows()),0.5);
		}
		
		DenseMatrix64F StdDevMatrix = new DenseMatrix64F(StdDev);
		SimpleMatrix StdDevMat = SimpleMatrix.wrap(StdDevMatrix);
		return StdDevMat;
	}
	
	/**
	 * @return X after normalisation
	 */
	public SimpleMatrix FNormalize(){
		SimpleMatrix mean = computeMean();
		SimpleMatrix sigma = computeStdDev();
		SimpleMatrix X_norm = null;
		double[][] newX = new double[X.numRows()][X.numCols()];
		for(int i=0; i<X.numRows(); i++){
			SimpleMatrix tempVect = X.extractVector(true, i);
			for(int j=0; j<tempVect.getNumElements(); j++){
				newX[i][j] = (tempVect.get(j)-mean.get(j))/sigma.get(j);
			}
		}
		DenseMatrix64F newXMat = new DenseMatrix64F(newX);
		X_norm = SimpleMatrix.wrap(newXMat);
		return X_norm;
	}
}
