package com.scarytom.pronet;

import static org.junit.Assert.assertEquals;
import junit.framework.Assert;

import org.junit.Test;

import com.google.common.collect.Sets;
import com.scarytom.Network;
import com.scarytom.NetworkBuilder;

public class KudosProviderTest {

	@Test
	public void testKudosForSingleProgrammer() {
		Programmer bill = new Programmer("Bill", Sets.<String> newHashSet());
		Network network = new NetworkBuilder().withProgrammer(bill).build();
		KudosProvider kudosProvider = new KudosProvider(network);
		Assert.assertEquals(1.0, kudosProvider.kudosFor(bill), 0.01);
	}

	@Test
	public void twoProgrammersPassAllKudosToEachOther() {
		Programmer programmer2 = new Programmer("programmer2",
				Sets.<String> newHashSet());
		Programmer programmer = new Programmer("programmer1",
				Sets.<String> newHashSet());
		programmer.addRecommendation(programmer2);
		// calculate the kudos..
		assertEquals(1, programmer.kudos(), 0.001);
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

		assertEquals(1.49, a.kudos(), 0.001);
		assertEquals(0.78, b.kudos(), 0.001);
		assertEquals(1.59, c.kudos(), 0.001);
		assertEquals(0.15, d.kudos(), 0.001);
	}

}
