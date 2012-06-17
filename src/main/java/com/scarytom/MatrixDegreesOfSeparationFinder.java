package com.scarytom;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Maps;
import com.scarytom.pronet.Programmer;

public class MatrixDegreesOfSeparationFinder implements
		IDegreesOfSeparationFinder {

	private final Map<Programmer, Integer> _programmerIndex;
	private final int[][] _degreesOfSeparation;

	public MatrixDegreesOfSeparationFinder(final Set<Programmer> programmers) {
		final int[][] relations = buildEmptySquareMatrix(programmers.size());
		_programmerIndex = buildProgrammerIndex(programmers, relations);

		_degreesOfSeparation = buildEmptySquareMatrix(programmers.size());
		MatrixCalculator matrixCalculator = new MatrixCalculator();
		int[][] current = matrixCalculator.copy(relations);
		int[][] previousDegrees;
		int degree = 1;
		updateDegrees(_degreesOfSeparation, current, degree);
		// MatrixTools.printout(_degreesOfSeparation);

		do {
			previousDegrees = matrixCalculator.copy(_degreesOfSeparation);
			current = matrixCalculator.multiply(relations, current);
			degree++;
			updateDegrees(_degreesOfSeparation, current, degree);
			// MatrixTools.printout(_degreesOfSeparation);
		} while (!MatrixTools.equals(previousDegrees, _degreesOfSeparation));
	}

	private void updateDegrees(final int[][] degreesOfSeparation,
			final int[][] current, final int degree) {
		for (int i = 0; i < current.length; i++) {
			for (int j = 0; j < current[i].length; j++) {
				if (degreesOfSeparation[i][j] == 0 && current[i][j] != 0) {
					degreesOfSeparation[i][j] = degree;
				}
			}
		}

	}

	private Map<Programmer, Integer> buildProgrammerIndex(
			final Set<Programmer> programmers, final int[][] relations) {
		final Map<Programmer, Integer> programmerIndex = Maps.newHashMap();
		int index = 0;
		for (Programmer programmer : programmers) {
			programmerIndex.put(programmer, index++);
		}
		for (Programmer programmer : programmers) {
			int i = programmerIndex.get(programmer);
			Set<Programmer> recommendations = programmer.recommendations();
			for (Programmer recommendation : recommendations) {
				Integer j = programmerIndex.get(recommendation);
				relations[i][j] = 1;
				relations[j][i] = 1;
			}
		}
		return programmerIndex;
	}

	private int[][] buildEmptySquareMatrix(final int width) {
		return new MatrixBuilder().withDefaults(0).withDimensions(width, width)
				.build();
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
