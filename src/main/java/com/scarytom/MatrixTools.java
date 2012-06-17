package com.scarytom;

import java.util.Arrays;

public class MatrixTools {

	public static String printout(final int[][] matrix) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				sb.append(matrix[i][j]).append(' ');
			}
			sb.append('\n');
		}
		return sb.toString();
	}

	public static boolean equals(final int[][] previous, final int[][] current) {
		return Arrays.deepEquals(previous, current);
	}
}
