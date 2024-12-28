package io.github.shimeoki.jshaper.obj;

import io.github.shimeoki.jshaper.data.Clearable;
import io.github.shimeoki.jshaper.geom.Pointf;
import io.github.shimeoki.jshaper.geom.Pos;

public final class ParameterSpaceVertex implements Clearable, Pointf {

    public static final float DEFAULT_U = 0;
    public static final float DEFAULT_V = 0;
    public static final float DEFAULT_W = 1;

    private final float[] values = new float[3];

    public ParameterSpaceVertex(final float u, final float v, final float w) {
        setU(u);
        setV(v);
        setW(w);
    }

    @Override
    public float[] array() {
        return values;
    }

    @Override
    public float get(final Pos p) {
        switch (p) {
            case U:
                return u();
            case V:
                return v();
            case W:
                return w();
            default:
                throw new IllegalArgumentException("ObjParameterSpaceVertex: invalid Pos");
        }
    }

    @Override
    public void set(final Pos p, final float value) {
        switch (p) {
            case U:
                setU(value);
                break;
            case V:
                setV(value);
                break;
            case W:
                setW(value);
                break;
            default:
                throw new IllegalArgumentException("ObjParameterSpaceVertex: invalid Pos");
        }
    }

    public float u() {
        return values[0];
    }

    public void setU(final float u) {
        values[0] = u;
    }

    public float v() {
        return values[1];
    }

    public void setV(final float v) {
        values[1] = v;
    }

    public float w() {
        return values[2];
    }

    public void setW(final float w) {
        values[2] = w;
    }

    @Override
    public void clear() {
        setU(DEFAULT_U);
        setV(DEFAULT_V);
        setW(DEFAULT_W);
    }
}
