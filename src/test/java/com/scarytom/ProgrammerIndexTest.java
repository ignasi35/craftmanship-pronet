package com.scarytom;

import java.util.Iterator;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;
import com.scarytom.pronet.Programmer;

public class ProgrammerIndexTest {
	private ProgrammerIndex _target;

	@Before
	public void before() {
		_target = new ProgrammerIndex();
	}

	@Test
	public void testANetOfOneHasOneItem() {
		Network net = new NetworkBuilder().withProgrammer(
				programmerFixture("adam")).build();

		Map<Programmer, Integer> index = _target.withNetwork(net).build();

		Assert.assertEquals(1, index.size());
		Assert.assertEquals("adam", index.keySet().iterator().next().name());
	}

	@Test
	public void testANetOfTwoHasTwoItemsSortedByProgrammerName() {
		Network net = new NetworkBuilder()
				.withProgrammer(programmerFixture("adam"))
				.withProgrammer(programmerFixture("eve"))
				.withProgrammer(programmerFixture("bob")).build();

		Map<Programmer, Integer> index = _target.withNetwork(net).build();

		Assert.assertEquals(3, index.size());
		Iterator<Programmer> iterator = index.keySet().iterator();
		Assert.assertEquals("adam", iterator.next().name());
		Assert.assertEquals("bob", iterator.next().name());
		Assert.assertEquals("eve", iterator.next().name());
	}

	private Programmer programmerFixture(final String name) {
		return new Programmer(name, Sets.<String> newTreeSet());
	}

}
