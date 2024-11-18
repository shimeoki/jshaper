package com.github.shimeoki.jshaper.data;

public final class Vector2f implements Array2f {

    private final float[] array = new float[2];

    public Vector2f(final float v1, final float v2) {
        setValue1(v1);
        setValue2(v2);
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
}
