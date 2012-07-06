package com.org.lregression;

import org.ejml.simple.SimpleMatrix;

/**
 * @author arunjayapal
 * Interface to be used for any optimisation algorithms such as gradient descent
 */
public interface OptimizationAlg {
	/**
	 * @return theta
	 */
	public SimpleMatrix computeTheta();
	/**
	 * @return J_history - a vector of costs for each iteration
	 * @param curr_iter - provide the current iteration number
	 * @param the - provide the theta in SimpleMatrix format
	 */
	public SimpleMatrix computeJ_history(int curr_iter, SimpleMatrix the);
}
