package io.github.shimeoki.jshaper.obj.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public final class ObjTokenTest {

    @Test
    public void isNull() {
        assertFalse(ObjToken.NIL.is(null));
    }

    @Test
    public void isTrue() {
        assertTrue(ObjToken.VERTEX.is(ObjToken.VERTEX));
    }

    @Test
    public void isFalse() {
        assertFalse(ObjToken.TEXTURE_VERTEX.is(ObjToken.VERTEX_NORMAL));
    }

    @Test
    public void nilString() {
        final String s = ObjToken.NIL.toString();

        assertEquals("", s);
    }

    @Test
    public void commentString() {
        final String s = ObjToken.COMMENT.toString();

        assertEquals("#", s);
        assertEquals(ObjTokenizer.COMMENT, s);
    }

    @Test
    public void vertexString() {
        final String s = ObjToken.VERTEX.toString();

        assertEquals("v", s);
        assertEquals(ObjTokenizer.VERTEX, s);
    }

    @Test
    public void textureVertexString() {
        final String s = ObjToken.TEXTURE_VERTEX.toString();

        assertEquals("vt", s);
        assertEquals(ObjTokenizer.TEXTURE_VERTEX, s);
    }

    @Test
    public void vertexNormalString() {
        final String s = ObjToken.VERTEX_NORMAL.toString();

        assertEquals("vn", s);
        assertEquals(ObjTokenizer.VERTEX_NORMAL, s);
    }

    @Test
    public void parameterSpaceVertexString() {
        final String s = ObjToken.PARAMETER_SPACE_VERTEX.toString();

        assertEquals("vp", s);
        assertEquals(ObjTokenizer.PARAMETER_SPACE_VERTEX, s);
    }

    @Test
    public void faceString() {
        final String s = ObjToken.FACE.toString();

        assertEquals("f", s);
        assertEquals(ObjTokenizer.FACE, s);
    }

    @Test
    public void groupNameString() {
        final String s = ObjToken.GROUP_NAME.toString();

        assertEquals("g", s);
        assertEquals(ObjTokenizer.GROUP_NAME, s);
    }
}
