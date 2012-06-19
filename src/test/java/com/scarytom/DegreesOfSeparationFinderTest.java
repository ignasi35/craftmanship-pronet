package com.scarytom;

import static junit.framework.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;
import com.scarytom.pronet.Programmer;

public class DegreesOfSeparationFinderTest {

    private IDegreesOfSeparationFinder buildTarget(
            final Set<Programmer> programmers) {
        NetworkBuilder builder = new NetworkBuilder();
        for (Programmer programmer : programmers) {
            builder.withProgrammer(programmer);
        }
        return new MatrixDegreesOfSeparationFinder(builder.build());
    }

    @Test
    public void canFindDirectlyLinkedRecommendee() throws Exception {
        Programmer tom = new Programmer("tom", Sets.<String> newHashSet());
        Programmer davie =
            new Programmer("davie", Sets.<String> newHashSet());

        tom.addRecommendation(davie);

        Set<Programmer> programmers = Sets.newHashSet(tom, davie);

        IDegreesOfSeparationFinder degreesOfSeparationFinder =
            buildTarget(programmers);
        assertEquals(1,
            degreesOfSeparationFinder.degreesOfSeparationBetween(tom,
                davie));
    }

    @Test
    public void canFindIndirectlyLinkedRecommendee() throws Exception {
        Programmer tom = new Programmer("tom", Sets.<String> newHashSet());
        Programmer dick =
            new Programmer("dick", Sets.<String> newHashSet());
        Programmer harry =
            new Programmer("harry", Sets.<String> newHashSet());

        tom.addRecommendation(dick);
        dick.addRecommendation(harry);

        Set<Programmer> programmers = Sets.newHashSet(tom, dick, harry);

        IDegreesOfSeparationFinder degreesOfSeparationFinder =
            buildTarget(programmers);
        assertEquals(2,
            degreesOfSeparationFinder.degreesOfSeparationBetween(tom,
                harry));
    }

    @Test
    public void canFindDirectlyLinkedRecommender() throws Exception {
        Programmer tom = new Programmer("tom", Sets.<String> newHashSet());
        Programmer davie =
            new Programmer("davie", Sets.<String> newHashSet());

        tom.addRecommendation(davie);

        Set<Programmer> programmers = Sets.newHashSet(tom, davie);

        IDegreesOfSeparationFinder degreesOfSeparationFinder =
            buildTarget(programmers);
        assertEquals(1,
            degreesOfSeparationFinder.degreesOfSeparationBetween(davie,
                tom));
    }
}
