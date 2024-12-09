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

        fail("no null pointer exception caught");
    }

    @Test
    public void parseComment() {
        final ObjToken t = ObjTokenizer.parse(ObjTokenizer.COMMENT);
        assertTrue(t.is(ObjToken.COMMENT));
    }

    @Test
    public void parseVertex() {
        final ObjToken t = ObjTokenizer.parse(ObjTokenizer.VERTEX);
        assertTrue(t.is(ObjToken.VERTEX));
    }

    @Test
    public void parseTextureVertex() {
        final ObjToken t = ObjTokenizer.parse(ObjTokenizer.TEXTURE_VERTEX);
        assertTrue(t.is(ObjToken.TEXTURE_VERTEX));
    }

    @Test
    public void parseVertexNormal() {
        final ObjToken t = ObjTokenizer.parse(ObjTokenizer.VERTEX_NORMAL);
        assertTrue(t.is(ObjToken.VERTEX_NORMAL));
    }

    @Test
    public void parseParameterSpaceVertex() {
        final ObjToken t = ObjTokenizer.parse(ObjTokenizer.PARAMETER_SPACE_VERTEX);
        assertTrue(t.is(ObjToken.PARAMETER_SPACE_VERTEX));
    }

    @Test
    public void parseFace() {
        final ObjToken t = ObjTokenizer.parse(ObjTokenizer.FACE);
        assertTrue(t.is(ObjToken.FACE));
    }

    @Test
    public void parseGroupName() {
        final ObjToken t = ObjTokenizer.parse(ObjTokenizer.GROUP_NAME);
        assertTrue(t.is(ObjToken.GROUP_NAME));
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
        final ObjToken t = ObjTokenizer.parse("");
        assertTrue(t.is(ObjToken.NIL));
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
