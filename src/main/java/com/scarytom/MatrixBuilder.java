package com.scarytom;

public class MatrixBuilder {

    private int _width = 0;

    private int _height = 0;

    private Number _defaultValue = 0;

    public MatrixBuilder withDefaults(final int defaultValue) {
        _defaultValue = defaultValue;
        return this;
    }

    public MatrixBuilder withDefaults(final double defaultValue) {
        _defaultValue = defaultValue;
        return this;
    }

    public MatrixBuilder withDimensions(final int width, final int height) {
        _width = width;
        _height = height;
        return this;
    }

    public int[][] build() {
        int[][] result = new int[_width][_height];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = Integer.valueOf(_defaultValue.toString());
            }
        }
        return result;
    }

    public double[][] buildForDouble() {
        double[][] result = new double[_width][_height];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = Double.valueOf(_defaultValue.toString());
            }
        }
        return result;
    }

}
