package com.github.shimeoki.jshaper.data;

public final class Vector4f implements Array4f {

    private final float[] array = new float[4];

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
        return array[0];
    }

    @Override
    public void setValue1(final float value) {
        array[0] = value;
    }

    @Override
    public void addToValue1(final float delta) {
        setValue1(value1() + delta);
    }

    @Override
    public float value2() {
        return array[1];
    }

    @Override
    public void setValue2(final float value) {
        array[1] = value;
    }

    @Override
    public void addToValue2(final float delta) {
        setValue2(value2() + delta);
    }

    @Override
    public float value3() {
        return array[2];
    }

    @Override
    public void setValue3(final float value) {
        array[2] = value;
    }

    @Override
    public void addToValue3(final float delta) {
        setValue3(value3() + delta);
    }

    @Override
    public float value4() {
        return array[3];
    }

    @Override
    public void setValue4(final float value) {
        array[3] = value;
    }

    @Override
    public void addToValue4(final float delta) {
        setValue4(value4() + delta);
    }
}
