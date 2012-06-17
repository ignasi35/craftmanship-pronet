package com.scarytom;

import com.scarytom.pronet.Programmer;

public interface IDegreesOfSeparationFinder {

	int degreesOfSeparationBetween(final Programmer p1, final Programmer p2);

	String[][] printout();

}
