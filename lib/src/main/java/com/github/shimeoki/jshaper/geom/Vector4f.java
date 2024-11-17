package com.github.shimeoki.jshaper.geom;

public final class Vector4f implements Point4f {

    private float x;
    private float y;
    private float z;
    private float w;

    public Vector4f(final float x, final float y, final float z, final float w) {
        setX(x);
        setY(y);
        setZ(z);
        setW(w);
    }

    @Override
    public float x() {
        return x;
    }

    @Override
    public void setX(final float x) {
        this.x = x;
    }

    @Override
    public void addX(final float dx) {
        setX(x + dx);
    }

    @Override
    public float y() {
        return y;
    }

    @Override
    public void setY(final float y) {
        this.y = y;
    }

    @Override
    public void addY(final float dy) {
        setY(y + dy);
    }

    @Override
    public float z() {
        return z;
    }

    @Override
    public void setZ(final float z) {
        this.z = z;
    }

    @Override
    public void addZ(final float dz) {
        setZ(z + dz);
    }

    @Override
    public float w() {
        return w;
    }

    @Override
    public void setW(final float w) {
        this.w = w;
    }

    @Override
    public void addW(final float dw) {
        setW(w + dw);
    }
}
