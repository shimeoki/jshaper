package com.github.shimeoki.jshaper.data;

public final class Vector4f implements Array4f {

    private float v1;
    private float v2;
    private float v3;
    private float v4;

    public Vector4f(
            final float v1,
            final float v2,
            final float v3,
            final float v4) {

        setValue1(v1);
        setValue2(v2);
        setValue3(v3);
        setValue4(v4);
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

    @Override
    public float value4() {
        return v4;
    }

    @Override
    public void setValue4(final float value) {
        v4 = value;
    }

    @Override
    public void addToValue4(final float delta) {
        setValue4(v4 + delta);
    }
}
