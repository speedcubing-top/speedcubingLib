package top.speedcubing.lib.math;

public class scMath {

    public static int randomInt(int min, int max) {
        return (int) ((Math.random() * (max + 1 - min)) + min);
    }

    public static double square(double d) {
        return d * d;
    }

    public static boolean isPrime(long l) {
        if (l < 2 || ((l & 1) == 0 && l != 2))
            return false;
        for (long i = 3; i * i <= l; i += 2)
            if (l % i == 0)
                return false;
        return true;
    }

    //this is cool
    public static double fastInvSqrt(double x, int accuracy) {
        double xH = 0.5D * x;
        x = Double.longBitsToDouble(0x5FE6Ec85E7DE30DAL - (Double.doubleToLongBits(x) >> 1));
        for (int i = 0; i < accuracy; i++)
            x *= (1.5D - xH * x * x);
        return x;
    }

    public static float fastInvSqrt(float x, int accuracy) {
        float xH = 0.5F * x;
        x = Float.intBitsToFloat(0x5F3759DF - (Float.floatToIntBits(x) >> 1));
        for (int i = 0; i < accuracy; i++)
            x *= (1.5F - xH * x * x);
        return x;
    }
}
