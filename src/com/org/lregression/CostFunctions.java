package com.org.lregression;

import org.ejml.simple.SimpleMatrix;

/**
 * @author arunjayapal
 *	Interface that can be used for implementing any cost function
 */
public interface CostFunctions {
	/**
	 * @param X - SimpleMatrix format that has the training data
	 * @param y - SimpleMatrix format that has the output 
	 * 						 corresponding to each training data
	 * @param theta - SimpleMatrix format that has the initial values
	 * @return J - Cost Function
	 */
	public double costCompute(SimpleMatrix X, SimpleMatrix y, SimpleMatrix theta);
}
