package com.github.shimeoki.jshaper.data;

public final class Vector3f implements Array3f {

    private final float[] array = new float[3];

    public Vector3f(final float v1, final float v2, final float v3) {
        setValue1(v1);
        setValue2(v2);
        setValue3(v3);
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
}
