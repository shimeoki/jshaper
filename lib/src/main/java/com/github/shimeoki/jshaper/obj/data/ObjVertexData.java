package com.github.shimeoki.jshaper.obj.data;

import java.util.List;

import com.github.shimeoki.jshaper.obj.geom.ObjParameterSpaceVertex;
import com.github.shimeoki.jshaper.obj.geom.ObjTextureVertex;
import com.github.shimeoki.jshaper.obj.geom.ObjVertex;
import com.github.shimeoki.jshaper.obj.geom.ObjVertexNormal;

public interface ObjVertexData {

    public List<ObjVertex> vertices();

    public List<ObjTextureVertex> textureVertices();

    public List<ObjVertexNormal> vertexNormals();

    public List<ObjParameterSpaceVertex> parameterSpaceVertices();
}
