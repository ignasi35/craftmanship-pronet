package com.scarytom;

import java.util.Set;

import com.google.common.collect.Sets;
import com.scarytom.pronet.Programmer;

public class DegreesOfSeparationFinder implements IDegreesOfSeparationFinder {
	private final UndirectedGraphBuilder undirectedGraphBuilder;

	public DegreesOfSeparationFinder(final Set<Programmer> programmers) {
		undirectedGraphBuilder = new UndirectedGraphBuilder(programmers);
	}

	@Override
	public int degreesOfSeparationBetween(final Programmer p1,
			final Programmer p2) {
		return sep(links(p1), p2, 1);
	}

	private int sep(final Set<Programmer> links, final Programmer p2,
			final int i) {
		if (links.contains(p2)) {
			return i;
		}
		Set<Programmer> links2 = Sets.newHashSet();
		for (Programmer programmer : links) {
			links2.addAll(links(programmer));
		}
		return sep(links2, p2, i + 1);
	}

	private Set<Programmer> links(final Programmer p) {
		Set<Programmer> recommendations = p.recommendations();
		Set<Programmer> recommenders = undirectedGraphBuilder.whoRecommends(p);
		Set<Programmer> result = Sets.newHashSet();
		result.addAll(recommendations);
		result.addAll(recommenders);
		return result;
	}

	@Override
	public String[][] printout() {
		// TODO Auto-generated method stub
		return null;
	}
}
