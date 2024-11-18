package com.github.shimeoki.jshaper.data;

public final class Vector3f implements Array3f {

    private float v1;
    private float v2;
    private float v3;

    public Vector3f(final float v1, final float v2, final float v3) {
        setValue1(v1);
        setValue2(v2);
        setValue3(v3);
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

    @Override
    public float value3() {
        return v3;
    }

    @Override
    public void setValue3(final float value) {
        v3 = value;
    }

    @Override
    public void addToValue3(final float delta) {
        setValue3(v3 + delta);
    }
}
