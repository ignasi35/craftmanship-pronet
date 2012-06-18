package com.scarytom.pronet;

import static org.junit.Assert.assertEquals;
import junit.framework.Assert;

import org.junit.Test;

import com.google.common.collect.Sets;
import com.scarytom.Network;
import com.scarytom.NetworkBuilder;

public class KudosProviderTest {

	private KudosProvider getKudosProvider(final Programmer... programmers) {
		NetworkBuilder networkBuilder = new NetworkBuilder();
		for (Programmer programmer : programmers) {
			networkBuilder.withProgrammer(programmer);
		}
		Network network = networkBuilder.build();
		KudosProvider kudosProvider = new KudosProvider(network);
		return kudosProvider;
	}

	@Test
	public void testKudosForSingleProgrammer() {
		Programmer bill = new Programmer("Bill", Sets.<String> newHashSet());
		Assert.assertEquals(1.0, getKudosProvider(bill).kudosFor(bill), 0.01);
	}

	@Test
	public void twoProgrammersPassAllKudosToEachOther() {
		Programmer p2 = new Programmer("programmer2",
				Sets.<String> newHashSet());
		Programmer p1 = new Programmer("programmer1",
				Sets.<String> newHashSet());
		p1.addRecommendation(p2);

		assertEquals(1, getKudosProvider(p2, p1).kudosFor(p1), 0.001);
	}

	@Test
	public void severalProgrammersPassKudosToEachOther() {
		Programmer a = new Programmer("programmer2", Sets.<String> newHashSet());
		Programmer b = new Programmer("programmer2", Sets.<String> newHashSet());
		Programmer c = new Programmer("programmer2", Sets.<String> newHashSet());
		Programmer d = new Programmer("programmer2", Sets.<String> newHashSet());

		a.addRecommendation(b);
		a.addRecommendation(c);
		b.addRecommendation(c);
		c.addRecommendation(a);
		d.addRecommendation(c);

		// A=1.49, B=0.78, C=1.59, D=0.15

		KudosProvider kudosProvider = getKudosProvider(a, b, c, d);

		assertEquals(1.49, kudosProvider.kudosFor(a), 0.001);
		assertEquals(0.78, kudosProvider.kudosFor(b), 0.001);
		assertEquals(1.59, kudosProvider.kudosFor(c), 0.001);
		assertEquals(0.15, kudosProvider.kudosFor(d), 0.001);
	}
}
