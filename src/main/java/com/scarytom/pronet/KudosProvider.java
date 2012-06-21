package com.scarytom.pronet;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.scarytom.MatrixBuilder;
import com.scarytom.MatrixCalculator;
import com.scarytom.MatrixTools;
import com.scarytom.Network;
import com.scarytom.NetworkRelations;
import com.scarytom.ProgrammerIndex;

public class KudosProvider {

    private static final double DUMPING_FACTOR = 0.85;

    private final Map<Programmer, Integer> _index;

    /**
     * Accumulated kudos after each iteration of the calculation. The actual
     * formula is:<br/>
     * <blockquote>[kn]=[1-d]+d·[R]·[km]</blockquote> where:
     * <ul>
     * <li><b>d</b> is a constant dumping factor (usually 0.85)</li>
     * <li><b>[kn]</b> is a vector with the resulting kudos for each programmer</li>
     * <li><b>[km]</b> is a vector with the kudos in the previous iteration</li>
     * <li><b>[1-d]</b> is a vector of constant values 1-d</li>
     * <li><b>[R]</b> is a matrix with the ratio of kudos each programmer is
     * passing to another. Where each position (<i>i</i>,<i>j</i>) it's the
     * ratio of kudos programmer <i>i</i> is receiving from programmer <i>j</i>.
     * The actual amount of kudos passed will depend on the actual kudos of
     * <i>i</i> and the ratio denoted in (<i>i</i>, <i>j</i>). <b>NOTE</b> that
     * to compute this matrix it is necessary to transpose the relations matrix
     * since it reflects the incoming recommendations not the outgoing (which is
     * what the relations matrix represents).
     * </ul>
     */
    private double[][] _kudos;

    public KudosProvider(final Network network) {
        _index = new ProgrammerIndex().withNetwork(network).build();
        Map<Integer, Programmer> reverseIndex = buildReverseIndex(_index);
        double[][] relations =
            new NetworkRelations().withNetwork(network)
                .withProgrammerIndex(_index).withSymmetry(false)
                .buildWithDouble();
        double[][] ratios = buildRatios(relations, reverseIndex);

        double[][] dumpings =
            new MatrixBuilder()
                .withDimensions(network.programmers().size(), 1)
                .withDefaults(1 - DUMPING_FACTOR).buildWithDouble();
        double[][] previousKudos =
            add(dumpings,
                new MatrixBuilder()
                    .withDimensions(network.programmers().size(), 1)
                    .withDefaults(DUMPING_FACTOR).buildWithDouble());

        boolean completed = false;
        do {
            _kudos =
                add(dumpings,
                    multiply(DUMPING_FACTOR, new MatrixCalculator()
                        .multiply(ratios, previousKudos)));
            double error = error(_kudos, previousKudos);
            completed = error < 0.000001;
            if (!completed) {
                previousKudos = _kudos;
                System.out.println(MatrixTools.printout(_kudos));
            }
        } while (!completed);
    }

    private double[][] multiply(final double d, final double[][] multiply) {
        for (int i = 0; i < multiply.length; i++) {
            for (int j = 0; j < multiply[0].length; j++) {
                multiply[i][j] *= d;
            }
        }
        return multiply;
    }

    private double error(final double[][] kudos,
            final double[][] previousKudos) {
        int width = kudos.length;
        int height = kudos[0].length;
        return ((double) 1 / (width * height))
            * sum(squaredDifferences(kudos, previousKudos));
    }

    private double sum(final double[][] input) {
        double result = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                result += input[i][j];
            }
        }
        return result;
    }

    private double[][] squaredDifferences(final double[][] kudos,
            final double[][] previousKudos) {
        double[][] result =
            new MatrixBuilder().withDefaults(0)
                .withDimensions(kudos.length, kudos[0].length)
                .buildWithDouble();
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] =
                    Math.pow(kudos[i][j] - previousKudos[i][j], 2);
            }
        }
        return result;
    }

    private double[][] add(final double[][] dumpings,
            final double[][] multiply) {
        for (int i = 0; i < multiply.length; i++) {
            for (int j = 0; j < multiply[0].length; j++) {
                multiply[i][j] += dumpings[i][j];
            }
        }
        return multiply;
    }

    private Map<Integer, Programmer> buildReverseIndex(
            final Map<Programmer, Integer> index) {
        Map<Integer, Programmer> result =
            Maps.newHashMapWithExpectedSize(index.size());
        for (Entry<Programmer, Integer> entry : index.entrySet()) {
            result.put(entry.getValue(), entry.getKey());
        }
        return result;
    }

    /**
     * TODO remove when NetworkRelations returns ratios already.
     * 
     * @param relations
     * @param reverseIndex
     * @return
     */
    private double[][] buildRatios(final double[][] relations,
            final Map<Integer, Programmer> reverseIndex) {
        double[][] result =
            new MatrixBuilder().withDefaults(0)
                .withDimensions(relations.length, relations[0].length)
                .buildWithDouble();
        Programmer p;
        for (int i = 0; i < result.length; i++) {
            p = reverseIndex.get(i);
            int size = p.recommendations().size();
            for (int j = 0; j < result[i].length; j++) {
                if ((size == 0) && (i == j)) {
                    // someone who doesn't recommend anyone is actually
                    // recommending herself.
                    result[i][j] = 1;
                } else if (relations[i][j] != 0) {
                    result[j][i] = ((double) 1) / size;
                }
            }
        }
        return result;
    }

    public double kudosFor(final Programmer programmer) {
        return _kudos[_index.get(programmer)][0];
    }

}
