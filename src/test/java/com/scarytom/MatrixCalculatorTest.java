package com.scarytom;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

public class MatrixCalculatorTest {

	@Test
	public void testMultiplyIntegerMatrixes() {
		int[][] original = new int[][] { { 0, 1 }, { 1, 2 } };
		MatrixCalculator matrixCalculator = new MatrixCalculator();
		Assert.assertTrue(Arrays.deepEquals(new int[][] { { 1, 2 }, { 2, 5 } },
				matrixCalculator.multiply(original, original)));
	}

	@Test
	public void testMultiplyDoubleMatrixes() {
		double[][] original = new double[][] { { 0, 1 }, { 1, 2 } };
		MatrixCalculator matrixCalculator = new MatrixCalculator();
		Assert.assertTrue(Arrays.deepEquals(
				new double[][] { { 1, 2 }, { 2, 5 } },
				matrixCalculator.multiply(original, original)));
	}

	@Test
	public void testMultiplyDoubleMatrixAndVector() {
		double[][] matrix23 = new double[][] { { 0, 1 }, { 1, 2 }, { 0, 0 } };
		double[][] vector12 = new double[][] { { 1 }, { 2 } };

		MatrixCalculator matrixCalculator = new MatrixCalculator();
		double[][] actual = matrixCalculator.multiply(matrix23, vector12);
		Assert.assertEquals(2.0, actual[0][0]);
		Assert.assertEquals(5.0, actual[1][0]);
		Assert.assertEquals(0.0, actual[2][0]);

	}

}
