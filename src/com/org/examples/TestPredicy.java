package com.org.examples;
import java.io.IOException;

import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;

/**
 * @author arunjayapal
 * Sample to be used for using the saved model
 */
public class TestPredicy{
	/**
	 * @param args
	 * @throws IOException
	 * Sample
	 */
	public static void main(String args[]) throws IOException{
		double housing[][] = new double[1][3];
		double sqft = 1650;
		double nofrooms = 3;
		SimpleMatrix theta = SimpleMatrix.load("Theta.data");
		SimpleMatrix mean = SimpleMatrix.load("Mean.data");
		SimpleMatrix std = SimpleMatrix.load("StandardDeviation.data");
		housing[0][0] = 1;
		housing[0][1] = (sqft-mean.get(0))/std.get(0);
		housing[0][2] = (nofrooms-mean.get(1))/std.get(1);
		DenseMatrix64F housingMat = new DenseMatrix64F(housing);
//		housingMat.print();
//		System.out.println("Theta");
//		theta.print();
//		System.out.println("Mean");
//		mean.print();
//		System.out.println("Std");
//		std.print();
		SimpleMatrix housingWrap = SimpleMatrix.wrap(housingMat);
		System.out.println(housingWrap.mult(theta));
	}
}