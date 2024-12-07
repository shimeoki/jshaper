package io.github.shimeoki.jshaper.obj.geom;

import io.github.shimeoki.jshaper.geom.Pointf;
import io.github.shimeoki.jshaper.geom.Pos;

public final class ObjParameterSpaceVertex implements Pointf {

    // the specification doesn't say anything about the default "v" value.
    // so i default it to float's zero value: 0.
    public static final float DEFAULT_V = 0;
    public static final float DEFAULT_W = 1;

    private final float[] values = new float[3];

    public ObjParameterSpaceVertex(final float u, final Float v, final Float w) {
        setU(u);

        if (v != null) {
            setV(v);
        } else {
            setV(DEFAULT_V);
        }

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
}
