package com.github.shimeoki.jshaper.geom;

public interface Point4f extends Point3f, Pos4f {

    public void setW(final float w);

    public void addW(final float dw);
}
