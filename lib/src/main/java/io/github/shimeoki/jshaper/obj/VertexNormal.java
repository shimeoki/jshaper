package io.github.shimeoki.jshaper.obj;

import io.github.shimeoki.jshaper.Clearable;
import io.github.shimeoki.jshaper.Point;
import io.github.shimeoki.jshaper.Pos;

public final class VertexNormal implements Clearable, Point {

    public static final float DEFAULT_I = 0;
    public static final float DEFAULT_J = 0;
    public static final float DEFAULT_K = 0;

    private final float[] values = new float[3];

    public VertexNormal(final float i, final float j, final float k) {
        setI(i);
        setJ(j);
        setK(k);
    }

    @Override
    public float get(final Pos p) {
        switch (p) {
            case I:
                return i();
            case J:
                return j();
            case K:
                return k();
            default:
                throw new IllegalArgumentException("invalid Pos");
        }
    }

    @Override
    public void set(final Pos p, final float value) {
        switch (p) {
            case I:
                setI(value);
                break;
            case J:
                setJ(value);
                break;
            case K:
                setK(value);
                break;
            default:
                throw new IllegalArgumentException("invalid Pos");
        }
    }

    public float i() {
        return values[0];
    }

    public void setI(final float i) {
        values[0] = i;
    }

    public float j() {
        return values[1];
    }

    public void setJ(final float j) {
        values[1] = j;
    }

    public float k() {
        return values[2];
    }

    public void setK(final float k) {
        values[2] = k;
    }

    @Override
    public void clear() {
        setI(DEFAULT_I);
        setJ(DEFAULT_J);
        setK(DEFAULT_K);
    }
}
