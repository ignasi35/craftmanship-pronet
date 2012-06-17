package com.scarytom;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.Sets;
import com.scarytom.pronet.Programmer;

public class KudosTest {

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
