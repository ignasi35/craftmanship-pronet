package com.scarytom.pronet;

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
		Assert.assertEquals(1.0, bill.kudos(), 0.01);

	}
}
