package top.speedcubing.lib.math.matrix;

import top.speedcubing.lib.math.exception.InvalidMatrixException;

public class MatrixUtils {

    public static SquareMatrix getIdentityMatrix(int dimension) {
        if (dimension < 1)
            throw new InvalidMatrixException();
        double[][] d = new double[dimension][dimension];
        for (int i = 0; i < dimension; i++)
            d[i][i] = 1;
        return new SquareMatrix(d);
    }

    public static SquareMatrix get2DRotate(double rad) {
        double sin = Math.sin(rad);
        double cos = Math.cos(rad);
        return new SquareMatrix(new double[][]{{cos, -sin}, {sin, cos}});
    }

    public static SquareMatrix get2DReflect(double rad) {
        double sin = Math.sin(2 * rad);
        double cos = Math.cos(2 * rad);
        return new SquareMatrix(new double[][]{{cos, sin}, {sin, -cos}});
    }

    public static SquareMatrix get2DScaling(double x, double y) {
        return new SquareMatrix(new double[][]{{x, 0}, {0, y}});
    }

    public static SquareMatrix get2DShearX(double h) {
        return new SquareMatrix(new double[][]{{1, 0}, {h, 1}});
    }

    public static SquareMatrix get2DShearY(double h) {
        return new SquareMatrix(new double[][]{{1, h}, {0, 1}});
    }

    public static SquareMatrix get3DRotateX(double rad) {
        double sin = Math.sin(rad);
        double cos = Math.cos(rad);
        return new SquareMatrix(new double[][]{
                {1, 0, 0},
                {0, cos, -sin},
                {0, sin, cos}
        });
    }

    public static SquareMatrix get3DRotateY(double rad) {
        double sin = Math.sin(rad);
        double cos = Math.cos(rad);
        return new SquareMatrix(new double[][]{
                {cos, 0, sin},
                {0, 1, 0},
                {-sin, 0, cos}
        });
    }

    public static SquareMatrix get3DRotateZ(double rad) {
        double sin = Math.sin(rad);
        double cos = Math.cos(rad);
        return new SquareMatrix(new double[][]{
                {cos, -sin, 0},
                {sin, cos, 0},
                {0, 0, 1}
        });
    }
}
