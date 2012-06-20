package com.scarytom;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;
import com.scarytom.pronet.Programmer;

public class NetworkRelationsTest {

	private NetworkRelations _relations;

	@Before
	public void before() {
		_relations = new NetworkRelations();
	}

	@Test
	public void testANetworkOfTwoReturns0010() {
		Network network = buildNetworkOfTwo();

		int[][] relationsMatrix = _relations.withNetwork(network).buildWithInt();
		Assert.assertTrue(Arrays.deepEquals(new int[][] { { 0, 0 }, { 1, 0 } },
				relationsMatrix));
	}

	@Test
	public void testANetworkOfTwoAndSymmetricReturns0110() {
		Network network = buildNetworkOfTwo();

		int[][] relationsMatrix = _relations.withNetwork(network)
				.withSymmetry(true).buildWithInt();
		Assert.assertTrue(Arrays.deepEquals(new int[][] { { 0, 1 }, { 1, 0 } },
				relationsMatrix));
	}

	private Network buildNetworkOfTwo() {
		Programmer alice = programmerFixture("alice");
		Programmer bob = programmerFixture("bob");
		bob.addRecommendation(alice);

		Network network = new NetworkBuilder().withProgrammer(alice)
				.withProgrammer(bob).build();
		return network;
	}

	private Programmer programmerFixture(final String name) {
		return new Programmer(name, Sets.<String> newTreeSet());
	}
}
