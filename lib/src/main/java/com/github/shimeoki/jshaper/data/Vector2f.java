package com.github.shimeoki.jshaper.data;

public final class Vector2f implements Array2f {

    private float v1;
    private float v2;

    public Vector2f(final float v1, final float v2) {
        setValue1(v1);
        setValue2(v2);
    }

    @Override
    public float value1() {
        return v1;
    }

    @Override
    public void setValue1(final float value) {
        v1 = value;
    }

    @Override
    public void addToValue1(final float delta) {
        setValue1(v1 + delta);
    }

    @Override
    public float value2() {
        return v2;
    }

    @Override
    public void setValue2(final float value) {
        v2 = value;
    }

    @Override
    public void addToValue2(final float delta) {
        setValue2(v2 + delta);
    }
}
