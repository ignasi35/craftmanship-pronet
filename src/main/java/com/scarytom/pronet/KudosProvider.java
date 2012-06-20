package com.scarytom.pronet;

import java.util.Map;

import com.scarytom.MatrixBuilder;
import com.scarytom.MatrixCalculator;
import com.scarytom.Network;
import com.scarytom.NetworkRelations;
import com.scarytom.ProgrammerIndex;

public class KudosProvider {

	private final Map<Programmer, Integer> _index;
	private double[][] _kudos;

	public KudosProvider(final Network network) {
		_index = new ProgrammerIndex().withNetwork(network).build();
		double[][] relations = new NetworkRelations().withNetwork(network)
				.withProgrammerIndex(_index).withSymmetry(false)
				.buildWithDouble();
		_kudos = new MatrixBuilder()
				.withDimensions(network.programmers().size(), 1)
				.withDefaults(1).buildWithDouble();
		_kudos = new MatrixCalculator().multiply(relations, _kudos);
	}

	public double kudosFor(final Programmer programmer) {
		return _kudos[_index.get(programmer)][0];
	}

}
