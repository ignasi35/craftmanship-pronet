package com.scarytom;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

public class MatrixCalculatorTest {

	@Test
	public void testMultiply() {
		int[][] original = new int[][] { { 0, 1 }, { 1, 2 } };
		MatrixCalculator matrixCalculator = new MatrixCalculator();
		Assert.assertTrue(Arrays.deepEquals(new int[][] { { 1, 2 }, { 2, 5 } },
				matrixCalculator.multiply(original, original)));
	}

}
