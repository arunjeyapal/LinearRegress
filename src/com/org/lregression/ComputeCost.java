package com.org.lregression;

import org.ejml.simple.SimpleMatrix;

/**
 * @author arunjayapal
 * @serial	COMPUTECOST Compute cost for linear regression
 *		   J = COMPUTECOST(X, y, theta) computes the cost of using theta as the
 *		   parameter for linear regression to fit the data points in X and y
 */
public class ComputeCost implements CostFunctions{
	/**
	 * Constructor - nothing to initialise 
	 */
	public ComputeCost(){}
	
	/**
	 * Return cost function
	 * 
	 * @return J - Cost function
	 * @param x - SimpleMatrix format that has the training data
	 * @param y - SimpleMatrix format that has the output 
	 * 						 corresponding to each training data
	 * @param theta - SimpleMatrix format that has the initial values
	 */
	public double costCompute(SimpleMatrix x, SimpleMatrix y, SimpleMatrix theta) {
		double subOp;
		double sum = 0.0;		
		double J;
		int m = y.numRows(); //number of training examples
		double hypothesis;
		for(int i=0; i<m; i++){
			hypothesis = x.extractVector(true, i).elementMult(theta.transpose()).elementSum();
			double yVal = y.extractVector(true, i).get(0);
			subOp = hypothesis - yVal;
			sum = sum + (subOp * subOp);
		}
		J = sum/(2*m);
		//System.out.println(J);
		return J;
	}
}
