package com.scarytom;

import static com.google.common.collect.Sets.newHashSet;
import static junit.framework.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.scarytom.pronet.Programmer;

public class UndirectedGraphBuilderTest {
	@Test
	public void createsGraphWithTwoNodes() throws Exception {
		Set<Programmer> recommendations = newHashSet();
		Programmer recommendee = new Programmer("davie", new HashSet<Programmer>());
		recommendations.add(recommendee);
		
		Programmer tom = new Programmer("tom", recommendations);
		
		UndirectedGraphBuilder graphBuilder = new UndirectedGraphBuilder(newHashSet(recommendee, tom));
		Set<Programmer> recommenders = graphBuilder.whoRecommends(recommendee);
		assertEquals(newHashSet(tom), recommenders);
	}
	
	@Test
	public void canRecommendMikeInstead() throws Exception {
		Set<Programmer> recommendations = newHashSet();
		Programmer recommendee = new Programmer("mike", new HashSet<Programmer>());
		recommendations.add(recommendee);
		
		Programmer tom = new Programmer("tom", recommendations);
		
		UndirectedGraphBuilder graphBuilder = new UndirectedGraphBuilder(newHashSet(recommendee, tom));
		Set<Programmer> recommenders = graphBuilder.whoRecommends(recommendee);
		assertEquals(newHashSet(tom), recommenders);
	}
}
