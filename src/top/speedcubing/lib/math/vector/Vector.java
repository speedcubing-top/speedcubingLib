package top.speedcubing.lib.math.vector;

import top.speedcubing.lib.math.exception.InvalidDimensionException;

public class Vector {
    private final double[] vec;
    private final int dimension;

    public Vector(double... values) {
        vec = values;
        dimension = values.length;
    }

    public double dotProduct(Vector vector) {
        int sizeA = getDimension();
        if (sizeA == vector.getDimension()) {
            double d = 0;
            for (int i = 0; i < sizeA; i++)
                d += get(i + 1) * vector.get(i + 1);
            return d;
        } else throw new InvalidDimensionException();
    }

    public double angleOfVectors(Vector v2) {
        return Math.acos(dotProduct(v2) / (getLength() * v2.getLength()));
    }

    //正射影
    public Vector orthogonalProjection(Vector v2) {
        return v2.multiPly(dotProduct(v2) / (v2.getLength() * v2.getLength()));
    }

    //乘以整數
    public Vector multiPly(double d) {
        Vector vec = clone();
        for (int i = 0; i < dimension; i++)
            vec.vec[i] = vec.vec[i] * d;
        return vec;
    }

    public double get(int index) {
        return vec[index - 1];
    }

    public double getLength() {
        double result = 0;
        for (double k : vec)
            result += k * k;
        return Math.sqrt(result);
    }

    public int getDimension() {
        return vec.length;
    }

    public Vector3D toVec3D() {
        if (getDimension() != 3)
            throw new InvalidDimensionException();
        return new Vector3D(vec[0], vec[1], vec[2]);
    }

    public Vector clone() {
        return new Vector(vec);
    }

    public String toString() {
        StringBuilder result = new StringBuilder("Vector:(");
        for (double d : vec)
            result.append(d).append(", ");
        return result.substring(0, result.length() - 2) + ")";
    }
}
