package com.scarytom;

public class MatrixCalculator {

	/**
	 * @param matrix
	 */
	public MatrixCalculator() {
	}

	public int[][] multiply(final int[][] original, final int[][] current) {

		int[][] result = new int[original.length][current.length];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[i].length; j++) {
				result[i][j] = 0;
				for (int l = 0; l < result.length; l++) {
					result[i][j] += original[i][l] * current[l][j];
				}
			}
		}

		return result;
	}

	public int[][] copy(final int[][] original) {
		int[][] result = new int[original.length][original.length];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[i].length; j++) {
				result[i][j] = original[i][j];
			}
		}
		return result;
	}

	public double[][] multiply(final double[][] relations,
			final double[][] kudos) {
		double[][] result = new double[relations.length][kudos[0].length];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[i].length; j++) {
				result[i][j] = 0;
				for (int l = 0; l < relations[0].length; l++) {
					result[i][j] += relations[i][l] * kudos[l][j];
				}
			}
		}

		return result;

	}
}
