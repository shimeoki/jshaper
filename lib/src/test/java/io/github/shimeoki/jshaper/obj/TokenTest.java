package io.github.shimeoki.jshaper.obj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public final class TokenTest {

    @Test
    public void isNull() {
        final Token t = new Token("#");
        assertFalse(t.typeIs(null));
    }

    @Test
    public void isTrue() {
        final Token t = new Token("v");
        assertTrue(t.typeIs(Token.Type.VERTEX));
    }

    @Test
    public void isFalse() {
        final Token t = new Token("vt");
        assertFalse(t.typeIs(Token.Type.VERTEX_NORMAL));
    }

    @Test
    public void nilString() {
        final Token t = new Token(Token.NIL);
        assertEquals("", t.text());
    }

    @Test
    public void commentString() {
        assertEquals("#", Token.COMMENT);
    }

    @Test
    public void vertexString() {
        assertEquals("v", Token.VERTEX);
    }

    @Test
    public void textureVertexString() {
        assertEquals("vt", Token.TEXTURE_VERTEX);
    }

    @Test
    public void vertexNormalString() {
        assertEquals("vn", Token.VERTEX_NORMAL);
    }

    @Test
    public void parameterSpaceVertexString() {
        assertEquals("vp", Token.PARAMETER_VERTEX);
    }

    @Test
    public void faceString() {
        assertEquals("f", Token.FACE);
    }

    @Test
    public void groupNameString() {
        assertEquals("g", Token.GROUP);
    }

    @Test
    public void parseNull() {
        try {
            Token.parse(null);
        } catch (final NullPointerException e) {
            return;
        }

        fail("no NullPointerException caught");
    }

    @Test
    public void parseComment() {
        final Token.Type t1 = Token.parse(Token.COMMENT);
        final Token.Type t2 = Token.parse("#");

        assertTrue(t1.equals(t2));
        assertTrue(t1.equals(Token.Type.COMMENT));
    }

    @Test
    public void parseVertex() {
        final Token.Type t1 = Token.parse(Token.VERTEX);
        final Token.Type t2 = Token.parse("v");

        assertTrue(t1.equals(t2));
        assertTrue(t1.equals(Token.Type.VERTEX));
    }

    @Test
    public void parseTextureVertex() {
        final Token.Type t1 = Token.parse(Token.TEXTURE_VERTEX);
        final Token.Type t2 = Token.parse("vt");

        assertTrue(t1.equals(t2));
        assertTrue(t1.equals(Token.Type.TEXTURE_VERTEX));
    }

    @Test
    public void parseVertexNormal() {
        final Token.Type t1 = Token.parse(Token.VERTEX_NORMAL);
        final Token.Type t2 = Token.parse("vn");

        assertTrue(t1.equals(t2));
        assertTrue(t1.equals(Token.Type.VERTEX_NORMAL));
    }

    @Test
    public void parseParameterSpaceVertex() {
        final Token.Type t1 = Token.parse(Token.PARAMETER_VERTEX);
        final Token.Type t2 = Token.parse("vp");

        assertTrue(t1.equals(t2));
        assertTrue(t1.equals(Token.Type.PARAMETER_VERTEX));
    }

    @Test
    public void parseFace() {
        final Token.Type t1 = Token.parse(Token.FACE);
        final Token.Type t2 = Token.parse("f");

        assertTrue(t1.equals(t2));
        assertTrue(t1.equals(Token.Type.FACE));
    }

    @Test
    public void parseGroupName() {
        final Token.Type t1 = Token.parse(Token.GROUP);
        final Token.Type t2 = Token.parse("g");

        assertTrue(t1.equals(t2));
        assertTrue(t1.equals(Token.Type.GROUP));
    }

    @Test
    public void parseNumber() {
        final Token.Type nil = Token.Type.NIL;

        final Token.Type t1 = Token.parse("0");
        final Token.Type t2 = Token.parse("1");
        final Token.Type t3 = Token.parse("0.0");
        final Token.Type t4 = Token.parse("1.0");
        final Token.Type t5 = Token.parse("0.1234");
        final Token.Type t6 = Token.parse("100.000");

        assertTrue(t1.equals(nil));
        assertTrue(t2.equals(nil));
        assertTrue(t3.equals(nil));
        assertTrue(t4.equals(nil));
        assertTrue(t5.equals(nil));
        assertTrue(t6.equals(nil));
    }

    @Test
    public void parseSpace() {
        final Token.Type nil = Token.Type.NIL;

        final Token.Type t1 = Token.parse(" ");
        final Token.Type t2 = Token.parse("   ");
        final Token.Type t3 = Token.parse("     ");

        assertTrue(t1.equals(nil));
        assertTrue(t2.equals(nil));
        assertTrue(t3.equals(nil));
    }

    @Test
    public void parseBlank() {
        assertTrue(Token.parse("").equals(Token.Type.NIL));
    }

    @Test
    public void parseWithSpaces() {
        final Token.Type nil = Token.Type.NIL;

        final Token.Type t1 = Token.parse(" v");
        final Token.Type t2 = Token.parse("v ");
        final Token.Type t3 = Token.parse(" v ");
        final Token.Type t4 = Token.parse("  v  ");

        assertTrue(t1.equals(nil));
        assertTrue(t2.equals(nil));
        assertTrue(t3.equals(nil));
        assertTrue(t4.equals(nil));
    }

    @Test
    public void parseInvalid() {
        final Token.Type nil = Token.Type.NIL;

        final Token.Type t1 = Token.parse("-");
        final Token.Type t2 = Token.parse("+");
        final Token.Type t3 = Token.parse("!");
        final Token.Type t4 = Token.parse(":");
        final Token.Type t5 = Token.parse("?");

        assertTrue(t1.equals(nil));
        assertTrue(t2.equals(nil));
        assertTrue(t3.equals(nil));
        assertTrue(t4.equals(nil));
        assertTrue(t5.equals(nil));
    }
}
