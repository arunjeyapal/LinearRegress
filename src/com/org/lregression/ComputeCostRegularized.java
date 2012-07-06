package com.org.lregression;

import org.ejml.simple.SimpleMatrix;

/**
 * @author arunjayapal
 *
 */
public class ComputeCostRegularized implements CostFunctions {
	
	double lambda;
	/**
	 * Constructor nothing to initialise
	 * 
	 * @param lambda - Regularization parameter
	 */
	public ComputeCostRegularized(double lambda){
		this.lambda = lambda;
	}
	
	/**
	 * Returns regularized cost function
	 * 
	 * @return J - Regularized Cost function
	 * @param x - SimpleMatrix format that has the training data
	 * @param y - SimpleMatrix format that has the output corresponding to each training data
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
		SimpleMatrix thetaComp = theta.extractMatrix(1, theta.numRows(), 0, theta.numCols());
		double regularizationSum = thetaComp.elementMult(thetaComp).elementSum();
		J = (sum/(2*m) + ((lambda*regularizationSum)/(2*m)));
		//System.out.println(J);
		return J;
	}
}
