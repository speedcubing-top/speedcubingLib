package top.speedcubing.lib.math.vector;

public class Vector3D extends Vector{
    public Vector3D(double x, double y, double z) {
        super(x, y, z);
    }

    //外積
    public Vector3D crossProduct(Vector3D v2) {
        return new Vector3D(get(2) * v2.get(3) - v2.get(2) * get(3), get(3) * v2.get(1) - v2.get(3) * get(1), get(1) * v2.get(2) - v2.get(1) * get(2));
    }
}
