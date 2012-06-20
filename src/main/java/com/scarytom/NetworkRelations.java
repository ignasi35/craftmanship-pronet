package com.scarytom;

import java.util.Map;
import java.util.Set;

import com.scarytom.pronet.Programmer;

public class NetworkRelations {

	private Network _network;
	private Map<Programmer, Integer> _programmerIndex = null;
	private boolean _symmetric = false;

	public NetworkRelations withNetwork(final Network network) {
		_network = network;
		return this;
	}

	/**
	 * Makes the relational matrix represent bidirectional relations (the matrix
	 * will be symmetric).
	 * 
	 * @param symmetric
	 * @return
	 */
	public NetworkRelations withSymmetry(final boolean symmetric) {
		_symmetric = symmetric;
		return this;
	}

	public NetworkRelations withProgrammerIndex(
			final Map<Programmer, Integer> programmerIndex) {
		_programmerIndex = programmerIndex;
		return this;
	}

	public int[][] buildWithInt() {
		assertIndexing();

		Set<Programmer> programmers = _network.programmers();
		final int[][] relations = buildEmptySquareIntMatrix(programmers.size());
		for (Programmer programmer : programmers) {
			int i = _programmerIndex.get(programmer);
			Set<Programmer> recommendations = programmer.recommendations();
			for (Programmer recommendation : recommendations) {
				Integer j = _programmerIndex.get(recommendation);
				relations[i][j] = 1;
				if (_symmetric) {
					relations[j][i] = 1;
				}
			}
		}
		return relations;
	}

	public double[][] buildWithDouble() {
		assertIndexing();

		Set<Programmer> programmers = _network.programmers();
		final double[][] relations = buildEmptySquareDoubleMatrix(programmers
				.size());
		for (Programmer programmer : programmers) {
			int i = _programmerIndex.get(programmer);
			Set<Programmer> recommendations = programmer.recommendations();
			for (Programmer recommendation : recommendations) {
				Integer j = _programmerIndex.get(recommendation);
				relations[i][j] = 1;
				if (_symmetric) {
					relations[j][i] = 1;
				}
			}
		}
		return relations;
	}

	private void assertIndexing() {
		if (_programmerIndex == null) {
			_programmerIndex = new ProgrammerIndex().withNetwork(_network)
					.build();
		}
	}

	private int[][] buildEmptySquareIntMatrix(final int size) {
		return new MatrixBuilder().withDefaults(0).withDimensions(size, size)
				.buildWithInt();
	}

	private double[][] buildEmptySquareDoubleMatrix(final int size) {
		return new MatrixBuilder().withDefaults(0).withDimensions(size, size)
				.buildWithDouble();
	}

}
