package com.scarytom;

import java.util.Map;

import com.google.common.collect.Maps;
import com.scarytom.pronet.Programmer;

public class ProgrammerIndex {

	private Network _network;

	public ProgrammerIndex withNetwork(final Network network) {
		_network = network;
		return this;
	}

	public Map<Programmer, Integer> build() {
		Map<Programmer, Integer> programmerIndex = Maps.newTreeMap();
		int index = 0;
		for (Programmer programmer : _network.programmers()) {
			programmerIndex.put(programmer, index++);
		}
		return programmerIndex;
	}

}
