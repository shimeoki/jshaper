package io.github.shimeoki.jshaper.obj.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public final class ObjTokenTest {

    @Test
    public void isNull() {
        final ObjToken t = new ObjToken("#");
        assertFalse(t.typeIs(null));
    }

    @Test
    public void isTrue() {
        final ObjToken t = new ObjToken("v");
        assertTrue(t.typeIs(ObjToken.Type.VERTEX));
    }

    @Test
    public void isFalse() {
        final ObjToken t = new ObjToken("vt");
        assertFalse(t.typeIs(ObjToken.Type.VERTEX_NORMAL));
    }

    @Test
    public void nilString() {
        final ObjToken t = new ObjToken(ObjToken.NIL);
        assertEquals("", t.text());
    }

    @Test
    public void commentString() {
        assertEquals("#", ObjToken.COMMENT);
    }

    @Test
    public void vertexString() {
        assertEquals("v", ObjToken.VERTEX);
    }

    @Test
    public void textureVertexString() {
        assertEquals("vt", ObjToken.TEXTURE_VERTEX);
    }

    @Test
    public void vertexNormalString() {
        assertEquals("vn", ObjToken.VERTEX_NORMAL);
    }

    @Test
    public void parameterSpaceVertexString() {
        assertEquals("vp", ObjToken.PARAMETER_SPACE_VERTEX);
    }

    @Test
    public void faceString() {
        assertEquals("f", ObjToken.FACE);
    }

    @Test
    public void groupNameString() {
        assertEquals("g", ObjToken.GROUP_NAME);
    }

    @Test
    public void parseNull() {
        try {
            ObjToken.parse(null);
        } catch (final NullPointerException e) {
            return;
        }

        fail("no NullPointerException caught");
    }

    @Test
    public void parseComment() {
        final ObjToken.Type t1 = ObjToken.parse(ObjToken.COMMENT);
        final ObjToken.Type t2 = ObjToken.parse("#");

        assertTrue(t1.equals(t2));
        assertTrue(t1.equals(ObjToken.Type.COMMENT));
    }

    @Test
    public void parseVertex() {
        final ObjToken.Type t1 = ObjToken.parse(ObjToken.VERTEX);
        final ObjToken.Type t2 = ObjToken.parse("v");

        assertTrue(t1.equals(t2));
        assertTrue(t1.equals(ObjToken.Type.VERTEX));
    }

    @Test
    public void parseTextureVertex() {
        final ObjToken.Type t1 = ObjToken.parse(ObjToken.TEXTURE_VERTEX);
        final ObjToken.Type t2 = ObjToken.parse("vt");

        assertTrue(t1.equals(t2));
        assertTrue(t1.equals(ObjToken.Type.TEXTURE_VERTEX));
    }

    @Test
    public void parseVertexNormal() {
        final ObjToken.Type t1 = ObjToken.parse(ObjToken.VERTEX_NORMAL);
        final ObjToken.Type t2 = ObjToken.parse("vn");

        assertTrue(t1.equals(t2));
        assertTrue(t1.equals(ObjToken.Type.VERTEX_NORMAL));
    }

    @Test
    public void parseParameterSpaceVertex() {
        final ObjToken.Type t1 = ObjToken.parse(ObjToken.PARAMETER_SPACE_VERTEX);
        final ObjToken.Type t2 = ObjToken.parse("vp");

        assertTrue(t1.equals(t2));
        assertTrue(t1.equals(ObjToken.Type.PARAMETER_SPACE_VERTEX));
    }

    @Test
    public void parseFace() {
        final ObjToken.Type t1 = ObjToken.parse(ObjToken.FACE);
        final ObjToken.Type t2 = ObjToken.parse("f");

        assertTrue(t1.equals(t2));
        assertTrue(t1.equals(ObjToken.Type.FACE));
    }

    @Test
    public void parseGroupName() {
        final ObjToken.Type t1 = ObjToken.parse(ObjToken.GROUP_NAME);
        final ObjToken.Type t2 = ObjToken.parse("g");

        assertTrue(t1.equals(t2));
        assertTrue(t1.equals(ObjToken.Type.GROUP_NAME));
    }

    @Test
    public void parseNumber() {
        final ObjToken.Type nil = ObjToken.Type.NIL;

        final ObjToken.Type t1 = ObjToken.parse("0");
        final ObjToken.Type t2 = ObjToken.parse("1");
        final ObjToken.Type t3 = ObjToken.parse("0.0");
        final ObjToken.Type t4 = ObjToken.parse("1.0");
        final ObjToken.Type t5 = ObjToken.parse("0.1234");
        final ObjToken.Type t6 = ObjToken.parse("100.000");

        assertTrue(t1.equals(nil));
        assertTrue(t2.equals(nil));
        assertTrue(t3.equals(nil));
        assertTrue(t4.equals(nil));
        assertTrue(t5.equals(nil));
        assertTrue(t6.equals(nil));
    }

    @Test
    public void parseSpace() {
        final ObjToken.Type nil = ObjToken.Type.NIL;

        final ObjToken.Type t1 = ObjToken.parse(" ");
        final ObjToken.Type t2 = ObjToken.parse("   ");
        final ObjToken.Type t3 = ObjToken.parse("     ");

        assertTrue(t1.equals(nil));
        assertTrue(t2.equals(nil));
        assertTrue(t3.equals(nil));
    }

    @Test
    public void parseBlank() {
        assertTrue(ObjToken.parse("").equals(ObjToken.Type.NIL));
    }

    @Test
    public void parseWithSpaces() {
        final ObjToken.Type nil = ObjToken.Type.NIL;

        final ObjToken.Type t1 = ObjToken.parse(" v");
        final ObjToken.Type t2 = ObjToken.parse("v ");
        final ObjToken.Type t3 = ObjToken.parse(" v ");
        final ObjToken.Type t4 = ObjToken.parse("  v  ");

        assertTrue(t1.equals(nil));
        assertTrue(t2.equals(nil));
        assertTrue(t3.equals(nil));
        assertTrue(t4.equals(nil));
    }

    @Test
    public void parseInvalid() {
        final ObjToken.Type nil = ObjToken.Type.NIL;

        final ObjToken.Type t1 = ObjToken.parse("-");
        final ObjToken.Type t2 = ObjToken.parse("+");
        final ObjToken.Type t3 = ObjToken.parse("!");
        final ObjToken.Type t4 = ObjToken.parse(":");
        final ObjToken.Type t5 = ObjToken.parse("?");

        assertTrue(t1.equals(nil));
        assertTrue(t2.equals(nil));
        assertTrue(t3.equals(nil));
        assertTrue(t4.equals(nil));
        assertTrue(t5.equals(nil));
    }
}
