package top.speedcubing.lib.math.item;

public class Coord {
    private final double[] loc;

    public Coord(double... values) {
        loc = values;
    }

    public int getDimension() {
        return loc.length;
    }

    public double get(int index) {
        return loc[index - 1];
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        for (double d : loc)
            b.append(" ").append(d);
        return "Coord:(" + b.substring(1) + ")";
    }
}
