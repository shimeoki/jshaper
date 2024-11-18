package com.github.shimeoki.jshaper.data;

public final class Vector4f implements Array4f {

    private final Array3f array;
    private float v4;

    public Vector4f(
            final float v1,
            final float v2,
            final float v3,
            final float v4) {

        array = new Vector3f(v1, v2, v3);
        setValue4(v4);
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
        return array.value3();
    }

    @Override
    public void setValue3(final float value) {
        array.setValue3(value);
    }

    @Override
    public void addToValue3(final float delta) {
        array.addToValue3(delta);
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
