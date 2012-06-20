package com.scarytom;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.scarytom.pronet.Programmer;

public class MatrixDegreesOfSeparationFinder implements
		IDegreesOfSeparationFinder {

	private final Map<Programmer, Integer> _programmerIndex;

	private final int[][] _degreesOfSeparation;

	/**
	 * TODO replace parameter by a Network
	 * 
	 * @param programmers
	 */
	public MatrixDegreesOfSeparationFinder(final Network network) {
		_programmerIndex = new ProgrammerIndex().withNetwork(network).build();
		int[][] relations = new NetworkRelations().withNetwork(network)
				.withProgrammerIndex(_programmerIndex).withSymmetry(true)
				.buildWithInt();
		_degreesOfSeparation = computeDegreesOfSeparation(network, relations);
	}

	private int[][] computeDegreesOfSeparation(final Network network,
			final int[][] relations) {
		Set<Programmer> programmers = network.programmers();
		int width = programmers.size();
		int[][] degreesOfSeparation = new MatrixBuilder().withDefaults(0)
				.withDimensions(width, width).buildWithInt();
		MatrixCalculator matrixCalculator = new MatrixCalculator();
		// holds current result of multiplication
		int[][] current = matrixCalculator.copy(relations);
		// holds result or multiplication in previous iteration. Compred to
		// <code>current</code> we can determine if the alg has stabilized
		int[][] previousDegrees;
		// iteration counter (actually counts degrees of separation ;-))
		int degree = 1;
		updateDegrees(degreesOfSeparation, current, degree);
		do {
			previousDegrees = matrixCalculator.copy(degreesOfSeparation);
			current = matrixCalculator.multiply(relations, current);
			degree++;
			updateDegrees(degreesOfSeparation, current, degree);
		} while (!MatrixTools.equals(previousDegrees, degreesOfSeparation));
		// final adjustment: the diagonal must be reset to zero.
		resetDiagonalToZero(degreesOfSeparation);
		return degreesOfSeparation;
	}

	private void resetDiagonalToZero(final int[][] degreesOfSeparation) {
		for (int i = 0; i < degreesOfSeparation.length; i++) {
			degreesOfSeparation[i][i] = 0;
		}
	}

	private void updateDegrees(final int[][] degreesOfSeparation,
			final int[][] current, final int degree) {
		for (int i = 0; i < current.length; i++) {
			for (int j = 0; j < current[i].length; j++) {
				if ((degreesOfSeparation[i][j] == 0) && (current[i][j] != 0)) {
					degreesOfSeparation[i][j] = degree;
				}
			}
		}
	}

	@Override
	public int degreesOfSeparationBetween(final Programmer p1,
			final Programmer p2) {
		return _degreesOfSeparation[_programmerIndex.get(p1)][_programmerIndex
				.get(p2)];
	}

	@Override
	public String[][] printout() {
		String[][] result = new String[_degreesOfSeparation.length + 1][_degreesOfSeparation.length + 1];
		for (int i = 1; i < result.length; i++) {
			for (int j = 1; j < result[i].length; j++) {
				result[i][j] = "" + _degreesOfSeparation[i - 1][j - 1];
			}
		}
		for (Entry<Programmer, Integer> entry : _programmerIndex.entrySet()) {
			result[entry.getValue() + 1][0] = entry.getKey().name();
			result[0][entry.getValue() + 1] = entry.getKey().name();

		}
		result[0][0] = "";
		return result;
	}
}
