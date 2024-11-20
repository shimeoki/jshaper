package com.github.shimeoki.jshaper.data;

public final class Vectorf implements Arrayf {

    private final float[] array;

    public Vectorf(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Vectorf: n < 0");
        }

        array = new float[n];
    }

    @Override
    public float[] array() {
        return array;
    }
}
