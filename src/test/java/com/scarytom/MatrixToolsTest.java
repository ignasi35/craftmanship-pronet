package com.scarytom;

import junit.framework.Assert;

import org.junit.Test;

public class MatrixToolsTest {

	@Test
	public void testPrintout() {
		Assert.assertEquals("1 2 \n3 4 \n",
				MatrixTools.printout(new int[][] { { 1, 2 }, { 3, 4 } }));
	}

	@Test
	public void testEqualsTrue() {
		Assert.assertTrue(MatrixTools.equals(
				new int[][] { { 1, 2 }, { 3, 4 } }, new int[][] { { 1, 2 },
						{ 3, 4 } }));
	}

	@Test
	public void testEqualsFalse() {
		Assert.assertFalse(MatrixTools.equals(
				new int[][] { { 2, 2 }, { 3, 4 } }, new int[][] { { 1, 2 },
						{ 3, 4 } }));
	}

}
