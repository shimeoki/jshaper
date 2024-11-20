package com.github.shimeoki.jshaper.obj.geom;

import com.github.shimeoki.jshaper.geom.Pointf;

public interface ObjVertex extends Pointf {

    public float x();

    public void setX(final float x);

    public float y();

    public void setY(final float y);

    public float z();

    public void setZ(final float z);

    public float w();

    public void setW(final float w);
}
