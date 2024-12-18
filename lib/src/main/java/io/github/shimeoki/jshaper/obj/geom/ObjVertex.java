package io.github.shimeoki.jshaper.obj.geom;

import io.github.shimeoki.jshaper.geom.Pointf;
import io.github.shimeoki.jshaper.geom.Pos;

public final class ObjVertex implements Pointf {

    public static final float DEFAULT_W = 1;

    private final float[] values = new float[4];

    public ObjVertex(final float x, final float y, final float z, final Float w) {
        setX(x);
        setY(y);
        setZ(z);

        if (w != null) {
            setW(w);
        } else {
            setW(DEFAULT_W);
        }
    }

    @Override
    public float[] array() {
        return values;
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
}
