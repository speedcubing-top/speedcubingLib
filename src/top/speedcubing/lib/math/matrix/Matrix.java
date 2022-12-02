package top.speedcubing.lib.math.matrix;

import top.speedcubing.lib.math.exception.InvalidMatrixException;

public class Matrix {
    private final double[][] v;

    public Matrix(double[][] v) {
        for (int i = 1; i < v.length; i++)
            if (v[i].length != v[0].length)
                throw new InvalidMatrixException();
        this.v = v;
    }

    public SquareMatrix toSquareMatrix() {
        return new SquareMatrix(v);
    }

    public int getRowCount() {
        return v.length;
    }

    public int getColumnCount() {
        return v[0].length;
    }

    public Matrix multiply(Matrix matrix2D) {
        int r1 = getRowCount();
        int r2 = getColumnCount();
        int c1 = matrix2D.getRowCount();
        int c2 = matrix2D.getColumnCount();
        if (r2 != c1)
            throw new InvalidMatrixException();
        double[][] sum = new double[r1][c2];
        for (int i = 0; i < r1; i++)
            for (int j = 0; j < c2; j++)
                for (int k = 0; k < c1; k++)
                    sum[i][j] += v[i][k] * matrix2D.v[k][j];
        return new Matrix(sum);
    }

    public double get(int r, int c) {
        return v[r - 1][c - 1];
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        int largest = 0;
        for (int i = 0; i < getColumnCount(); i++)
            for (double[] doubles : v) {
                int size = Double.toString(doubles[i]).length();
                if (largest < size)
                    largest = size;
            }
        for (int i = 0; i < getRowCount(); i++) {
            b.append("\n[ ");
            for (double d : v[i]) {
                StringBuilder sBuilder = new StringBuilder(Double.toString(d));
                while (sBuilder.length() < largest + 1)
                    sBuilder.append(" ");
                b.append(sBuilder);
            }
            b.append("]");
        }
        return b.substring(1);
    }
}
