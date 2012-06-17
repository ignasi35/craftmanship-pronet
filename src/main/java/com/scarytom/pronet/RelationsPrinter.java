package com.scarytom.pronet;

import java.io.IOException;

import com.scarytom.MatrixDegreesOfSeparationFinder;
import com.scarytom.Network;
import com.scarytom.NetworkBuilder;

import nu.xom.ParsingException;

public class RelationsPrinter {

	public static void main(final String[] args) throws ParsingException,
			IOException {
		Network net = NetworkBuilder.buildNetwork(
				"./src/main/resources/ProNet.xml").build();
		MatrixDegreesOfSeparationFinder separationFinder = new MatrixDegreesOfSeparationFinder(
				net.programmers());
		String[][] printout = separationFinder.printout();
		for (int i = 0; i < printout.length; i++) {
			for (int j = 0; j < printout.length; j++) {
				System.out.print(printout[i][j] + "\t");
			}
			System.out.println("");
		}
	}
}
