package io.github.shimeoki.jshaper.obj.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public final class ObjTokenizedTest {

    @Test
    public void nullValue() {
        try {
            new ObjTokenized(null);
        } catch (final NullPointerException e) {
            return;
        }

        fail("no NullPointerException caught");
    }

    @Test
    public void checkValue() {
        final String s = "value";
        final ObjTokenized t = new ObjTokenized(s);

        assertEquals(s, t.value());
    }

    @Test
    public void checkTrueToken() {
        final ObjTokenized t1 = new ObjTokenized(ObjTokenizer.VERTEX);
        final ObjTokenized t2 = new ObjTokenized("v");

        assertTrue(t1.token().is(t2.token()));
    }

    @Test
    public void checkFalseToken() {
        final ObjTokenized t1 = new ObjTokenized(ObjTokenizer.FACE);
        final ObjTokenized t2 = new ObjTokenized("g");

        assertFalse(t1.token().is(t2.token()));
    }
}
