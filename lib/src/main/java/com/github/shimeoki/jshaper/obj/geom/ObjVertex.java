package com.github.shimeoki.jshaper.obj.geom;

import com.github.shimeoki.jshaper.data.Arrayf;
import com.github.shimeoki.jshaper.geom.Posf;

public interface ObjVertex extends Posf, Arrayf {

    public float x();

    public void setX(final float x);

    public float y();

    public void setY(final float y);

    public float z();

    public void setZ(final float z);

    public float w();

    public void setW(final float w);
}
