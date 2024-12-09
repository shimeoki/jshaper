package io.github.shimeoki.jshaper.obj.reader;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public final class ObjTokenizerTest {

    @Test
    public void parseNull() {
        try {
            ObjTokenizer.parse(null);
        } catch (final NullPointerException e) {
            return;
        }

        fail("no NullPointerException caught");
    }

    @Test
    public void parseComment() {
        final ObjToken t1 = ObjTokenizer.parse(ObjTokenizer.COMMENT);
        final ObjToken t2 = ObjTokenizer.parse("#");

        assertTrue(t1.is(t2));
        assertTrue(t1.is(ObjToken.COMMENT));
    }

    @Test
    public void parseVertex() {
        final ObjToken t1 = ObjTokenizer.parse(ObjTokenizer.VERTEX);
        final ObjToken t2 = ObjTokenizer.parse("v");

        assertTrue(t1.is(t2));
        assertTrue(t1.is(ObjToken.VERTEX));
    }

    @Test
    public void parseTextureVertex() {
        final ObjToken t1 = ObjTokenizer.parse(ObjTokenizer.TEXTURE_VERTEX);
        final ObjToken t2 = ObjTokenizer.parse("vt");

        assertTrue(t1.is(t2));
        assertTrue(t1.is(ObjToken.TEXTURE_VERTEX));
    }

    @Test
    public void parseVertexNormal() {
        final ObjToken t1 = ObjTokenizer.parse(ObjTokenizer.VERTEX_NORMAL);
        final ObjToken t2 = ObjTokenizer.parse("vn");

        assertTrue(t1.is(t2));
        assertTrue(t1.is(ObjToken.VERTEX_NORMAL));
    }

    @Test
    public void parseParameterSpaceVertex() {
        final ObjToken t1 = ObjTokenizer.parse(ObjTokenizer.PARAMETER_SPACE_VERTEX);
        final ObjToken t2 = ObjTokenizer.parse("vp");

        assertTrue(t1.is(t2));
        assertTrue(t1.is(ObjToken.PARAMETER_SPACE_VERTEX));
    }

    @Test
    public void parseFace() {
        final ObjToken t1 = ObjTokenizer.parse(ObjTokenizer.FACE);
        final ObjToken t2 = ObjTokenizer.parse("f");

        assertTrue(t1.is(t2));
        assertTrue(t1.is(ObjToken.FACE));
    }

    @Test
    public void parseGroupName() {
        final ObjToken t1 = ObjTokenizer.parse(ObjTokenizer.GROUP_NAME);
        final ObjToken t2 = ObjTokenizer.parse("g");

        assertTrue(t1.is(t2));
        assertTrue(t1.is(ObjToken.GROUP_NAME));
    }

    @Test
    public void parseNumber() {
        final ObjToken nil = ObjToken.NIL;

        final ObjToken t1 = ObjTokenizer.parse("0");
        final ObjToken t2 = ObjTokenizer.parse("1");
        final ObjToken t3 = ObjTokenizer.parse("0.0");
        final ObjToken t4 = ObjTokenizer.parse("1.0");
        final ObjToken t5 = ObjTokenizer.parse("0.1234");
        final ObjToken t6 = ObjTokenizer.parse("100.000");

        assertTrue(t1.is(nil));
        assertTrue(t2.is(nil));
        assertTrue(t3.is(nil));
        assertTrue(t4.is(nil));
        assertTrue(t5.is(nil));
        assertTrue(t6.is(nil));
    }

    @Test
    public void parseSpace() {
        final ObjToken nil = ObjToken.NIL;

        final ObjToken t1 = ObjTokenizer.parse(" ");
        final ObjToken t2 = ObjTokenizer.parse("   ");
        final ObjToken t3 = ObjTokenizer.parse("     ");

        assertTrue(t1.is(nil));
        assertTrue(t2.is(nil));
        assertTrue(t3.is(nil));
    }

    @Test
    public void parseBlank() {
        assertTrue(ObjTokenizer.parse("").is(ObjToken.NIL));
    }

    @Test
    public void parseWithSpaces() {
        final ObjToken nil = ObjToken.NIL;

        final ObjToken t1 = ObjTokenizer.parse(" v");
        final ObjToken t2 = ObjTokenizer.parse("v ");
        final ObjToken t3 = ObjTokenizer.parse(" v ");
        final ObjToken t4 = ObjTokenizer.parse("  v  ");

        assertTrue(t1.is(nil));
        assertTrue(t2.is(nil));
        assertTrue(t3.is(nil));
        assertTrue(t4.is(nil));
    }

    @Test
    public void parseInvalid() {
        final ObjToken nil = ObjToken.NIL;

        final ObjToken t1 = ObjTokenizer.parse("-");
        final ObjToken t2 = ObjTokenizer.parse("+");
        final ObjToken t3 = ObjTokenizer.parse("!");
        final ObjToken t4 = ObjTokenizer.parse(":");
        final ObjToken t5 = ObjTokenizer.parse("?");

        assertTrue(t1.is(nil));
        assertTrue(t2.is(nil));
        assertTrue(t3.is(nil));
        assertTrue(t4.is(nil));
        assertTrue(t5.is(nil));
    }
}
