package top.speedcubing.lib.math.matrix;

import top.speedcubing.lib.math.exception.InvalidMatrixException;

public class SquareMatrix extends Matrix {
    public SquareMatrix(double[][] v) {
        super(v);
        int first = v[0].length;
        if (v.length != first)
            throw new InvalidMatrixException();
        for (int i = 1; i < v.length; i++)
            if (v[i].length != first)
                throw new InvalidMatrixException();
    }

    public double[] linerTransform(double... values) {
        if (values.length == getRowCount()) {
            double[][] d = new double[values.length][1];
            for (int i = 0; i < values.length; i++)
                d[i][0] = values[i];
            Matrix m = multiply(new Matrix(d));
            double[] result = new double[values.length];
            for (int i = 0; i < values.length; i++)
                result[i] = m.get(i + 1, 1);
            return result;
        } else throw new InvalidMatrixException();
    }
}
