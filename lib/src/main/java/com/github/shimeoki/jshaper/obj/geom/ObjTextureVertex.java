package com.github.shimeoki.jshaper.obj.geom;

import com.github.shimeoki.jshaper.geom.Pointf;

public interface ObjTextureVertex extends Pointf {

    public float u();

    public void setU(final float u);

    public float v();

    public void setV(final float v);

    public float w();

    public void setW(final float w);
}
