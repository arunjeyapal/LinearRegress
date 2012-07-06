package com.org.examples;
import java.io.IOException;

import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;

/**
 * @author arunjayapal
 * Sample to be used for using the saved model
 */
public class PredictUniRegression {
	/**
	 * @param args
	 * @throws IOException
	 * Sample
	 */
	public static void main(String args[]) throws IOException{
		double pop[][] = new double[1][2];
		double val = 8.5781;
		System.out.println(val);
		pop[0][0] = 1;
		pop[0][1] = val;
		DenseMatrix64F population = new DenseMatrix64F(pop);
		SimpleMatrix popMat = SimpleMatrix.wrap(population);
		SimpleMatrix theta = SimpleMatrix.load("LinearModel.data");
		System.out.println(popMat.elementMult(theta.transpose()).elementSum());
	}
}
