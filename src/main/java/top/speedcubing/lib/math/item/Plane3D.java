package top.speedcubing.lib.math.item;

import top.speedcubing.lib.utils.Preconditions;

public class Plane3D {
    // ax + by + cz = d
    public final double a;
    public final double b;
    public final double c;
    public final double d;
    public final Vector n;

    public Plane3D(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.n = new Vector(a, b, c);
    }

    public Plane3D(Coord l1, Coord l2, Coord l3) {
        Preconditions.checkArgument(l1.getDimension() != 3 || l2.getDimension() != 3 || l3.getDimension() != 3);

        double a = l2.get(1) - l1.get(1);
        double b = l2.get(2) - l1.get(2);
        double c = l2.get(3) - l1.get(3);
        double d = l3.get(1) - l1.get(1);
        double e = l3.get(2) - l1.get(2);
        double f = l3.get(3) - l1.get(3);

        Preconditions.checkArgument(d * b != a * e || e * c != b * f);

        Vector res = new Vector(a, b, c).crossProduct(new Vector(d, e, f));
        this.a = res.get(1);
        this.b = res.get(2);
        this.c = res.get(3);
        this.d = res.get(1) * l1.get(1) + res.get(2) * l1.get(2) + res.get(3) * l1.get(3);
        this.n = new Vector(a, b, c);

    }

    //投影點
    public Coord projectedCoord(Coord coord3d) {
        double t = (d - a * coord3d.get(1) - b * coord3d.get(2) - c * coord3d.get(3)) / (a * a + b * b + c * c);
        return new Coord(coord3d.get(1) + a * t, coord3d.get(2) + b * t, coord3d.get(3) + c * t);
    }
}
