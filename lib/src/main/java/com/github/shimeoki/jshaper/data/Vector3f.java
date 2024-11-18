package com.github.shimeoki.jshaper.data;

public final class Vector3f implements Array3f {

    private final Array2f array;
    private float v3;

    public Vector3f(final float v1, final float v2, final float v3) {
        array = new Vector2f(v1, v2);
        setValue3(v3);
    }

    @Override
    public float value1() {
        return array.value1();
    }

    @Override
    public void setValue1(final float value) {
        array.setValue1(value);
    }

    @Override
    public void addToValue1(final float delta) {
        array.addToValue1(delta);
    }

    @Override
    public float value2() {
        return array.value2();
    }

    @Override
    public void setValue2(final float value) {
        array.setValue2(value);
    }

    @Override
    public void addToValue2(final float delta) {
        array.addToValue2(delta);
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
