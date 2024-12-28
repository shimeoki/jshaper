package io.github.shimeoki.jshaper.obj;

import io.github.shimeoki.jshaper.Clearable;
import io.github.shimeoki.jshaper.Point;
import io.github.shimeoki.jshaper.Pos;

public final class Vertex implements Clearable, Point {

    public static final float DEFAULT_X = 0;
    public static final float DEFAULT_Y = 0;
    public static final float DEFAULT_Z = 0;
    public static final float DEFAULT_W = 1;

    private final float[] values = new float[4];

    public Vertex(final float x, final float y, final float z, final float w) {
        setX(x);
        setY(y);
        setZ(z);
        setW(w);
    }

    @Override
    public float get(final Pos p) {
        switch (p) {
            case X:
                return x();
            case Y:
                return y();
            case Z:
                return z();
            case W:
                return w();
            default:
                throw new IllegalArgumentException("ObjVertex: invalid Pos");
        }
    }

    @Override
    public void set(final Pos p, final float value) {
        switch (p) {
            case X:
                setX(value);
                break;
            case Y:
                setY(value);
                break;
            case Z:
                setZ(value);
                break;
            case W:
                setW(value);
                break;
            default:
                throw new IllegalArgumentException("ObjVertex: invalid Pos");
        }
    }

    public float x() {
        return values[0];
    }

    public void setX(final float x) {
        values[0] = x;
    }

    public float y() {
        return values[1];
    }

    public void setY(final float y) {
        values[1] = y;
    }

    public float z() {
        return values[2];
    }

    public void setZ(final float z) {
        values[2] = z;
    }

    public float w() {
        return values[3];
    }

    public void setW(final float w) {
        values[3] = w;
    }

    @Override
    public void clear() {
        setX(DEFAULT_X);
        setY(DEFAULT_Y);
        setZ(DEFAULT_Z);
        setW(DEFAULT_W);
    }
}
