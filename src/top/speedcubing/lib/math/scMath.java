package top.speedcubing.lib.math;

public class scMath {
    public static int randomInt(int min, int max) {
        return (int) ((Math.random() * (max + 1 - min)) + min);
    }
}
