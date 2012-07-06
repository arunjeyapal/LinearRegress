package com.org.lregression;

import org.ejml.data.DenseMatrix64F;
import org.ejml.data.MatrixIterator;
import org.ejml.simple.SimpleMatrix;

/**
 * @author arunjayapal
 *
 */
public class GDRegularized implements OptimizationAlg {
	int iterations;
	double alpha;
	SimpleMatrix X;
	SimpleMatrix y;
	SimpleMatrix theta;
	double[][] J_hist;
	ComputeCost cost = new ComputeCost();
	double lambda;
	
	/**
	 * @param x - SimpleMatrix format that has the training data
	 * @param y - SimpleMatrix format that has the output 
	 * 						 corresponding to each training data
	 * @param theta - SimpleMatrix format that has the initial values
	 * @param alpha - Learning rate for the gradient descent computation
	 * @param iterations - number of iterations gradient descent runs
	 * @param lambda - Regularization parameter
	 */
	public GDRegularized(SimpleMatrix x, SimpleMatrix y, SimpleMatrix theta, double alpha, int iterations, double lambda){
		this.X = x;
		this.y = y;
		this.theta = theta;
		this.alpha = alpha;
		this.iterations = iterations;
		this.J_hist = new double[iterations][1];
		this.lambda = lambda;
	}
	

	public SimpleMatrix computeTheta() {
		int m = y.numRows(); //number of training examples
		//y.print();
		double sumVal[][] = new double[X.numRows()][X.numCols()];
		SimpleMatrix newTheta = theta;
		for(int h = 0; h<iterations; h++){
			for (int i = 0; i<m; i++){
				double hypothesis = X.extractVector(true, i).elementMult(newTheta.transpose()).elementSum();
				MatrixIterator it = X.iterator(true, i, 0, i, theta.numRows()-1);
				double yVal = y.extractVector(true, i).get(0);
				
				for(int j = 0; j<theta.numRows(); j++){
					double xVal = it.next().doubleValue();
					sumVal[i][j] = (hypothesis - yVal) * xVal;
				}
			}
			DenseMatrix64F sumValMat = new DenseMatrix64F(sumVal);
			SimpleMatrix sumVal_Matrix = SimpleMatrix.wrap(sumValMat);
			
			double summation[][] = new double[theta.numRows()][1];
			
			for(int j = 0; j<theta.numRows(); j++){
				 double temp = sumVal_Matrix.extractVector(false, j).elementSum();
				 summation[j][0] = (temp * alpha)/m;
			}
			DenseMatrix64F summation_mat = new DenseMatrix64F(summation);
			SimpleMatrix summation_Matrix = SimpleMatrix.wrap(summation_mat);
			
			newTheta = (SimpleMatrix) newTheta.minus(summation_Matrix);
			SimpleMatrix J_history = computeJ_history(h, newTheta);
			//J_history.print();
			J_history.getClass();
		}
		//System.out.println(J_history);
		return newTheta;
	}

	/**
	 * @return J_history - returns a vector of cost values after each iteration
	 * 						in SimpleMatrix format
	 * 
	 * @since Computes the cost function every time the theta value gets updated
	 *  Also, J_history value (cost) reduces after each iteration  
	 */
	public SimpleMatrix computeJ_history(int curr_iter, SimpleMatrix the) {
		J_hist[curr_iter][0] = cost.costCompute(X, y, the);
		DenseMatrix64F J_histMat = new DenseMatrix64F(J_hist);
		SimpleMatrix J_history = SimpleMatrix.wrap(J_histMat);
		return J_history;
	}

}
