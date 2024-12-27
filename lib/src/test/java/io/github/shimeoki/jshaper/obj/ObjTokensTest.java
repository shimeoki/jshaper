package io.github.shimeoki.jshaper.obj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public final class ObjTokensTest {

    @Test
    public void make() {
        final ObjTokens t = new ObjTokens();

        assertTrue(t.empty());
    }

    @Test
    public void addNull() {
        final ObjTokens t = new ObjTokens();

        try {
            t.add(null);
        } catch (final NullPointerException e) {
            return;
        }

        fail("no NullPointerException caught");
    }

    @Test
    public void empty() {
        final ObjTokens t = new ObjTokens();

        assertEquals(t.empty(), t.size() == 0);
    }

    @Test
    public void notEmpty() {
        final ObjTokens t = new ObjTokens();
        t.add(new ObjToken("#"));

        assertEquals(!t.empty(), t.size() != 0);
    }

    @Test
    public void add() {
        final ObjTokens tokens = new ObjTokens();
        final ObjToken token = new ObjToken("v");
        tokens.add(token);

        assertEquals(1, tokens.size());
        assertEquals(token, tokens.get(0));
    }

    @Test
    public void clear() {
        final ObjTokens t = new ObjTokens();
        t.add(new ObjToken("vt"));

        assertTrue(!t.empty());

        t.clear();

        assertTrue(t.empty());
    }

    @Test
    public void lineToken() {
        final ObjTokens tokens = new ObjTokens();
        final ObjToken token = new ObjToken("vn");

        tokens.add(token);

        assertEquals(token, tokens.get(0));
        assertEquals(token, tokens.lineToken());
    }

    @Test
    public void noLineToken() {
        final ObjTokens t = new ObjTokens();

        assertEquals(null, t.lineToken());
    }

    @Test
    public void noLineTokenIs() {
        final ObjTokens t = new ObjTokens();

        assertFalse(t.lineTokenTypeIs(ObjToken.Type.PARAMETER_SPACE_VERTEX));
    }

    @Test
    public void lineTokenIs() {
        final ObjTokens tokens = new ObjTokens();
        final ObjToken token = new ObjToken("f");

        tokens.add(token);

        assertTrue(tokens.lineTokenTypeIs(token.type()));
        assertTrue(tokens.lineTokenTypeIs(ObjToken.Type.FACE));
        assertFalse(tokens.lineTokenTypeIs(ObjToken.Type.TEXTURE_VERTEX));
    }

    @Test
    public void size() {
        final ObjTokens t = new ObjTokens();
        assertEquals(0, t.size());

        t.add(new ObjToken("vp"));
        assertEquals(1, t.size());

        t.add(new ObjToken("g"));
        assertEquals(2, t.size());

        t.add(new ObjToken("100"));
        assertEquals(3, t.size());

        t.clear();
        assertEquals(0, t.size());
    }

    @Test
    public void validIndex() {
        final ObjTokens t = new ObjTokens();
        assertFalse(t.validIndex(0));

        t.add(new ObjToken("o"));
        assertTrue(t.validIndex(0));
        assertFalse(t.validIndex(1));

        t.add(new ObjToken("gg"));
        assertTrue(t.validIndex(1));
        assertFalse(t.validIndex(2));

        t.clear();
        assertFalse(t.validIndex(0));
        assertFalse(t.validIndex(1));
        assertFalse(t.validIndex(2));

        assertFalse(t.validIndex(-1));
        assertFalse(t.validIndex(-26));
        assertFalse(t.validIndex(3));
        assertFalse(t.validIndex(40));
    }

    @Test
    public void get() {
        final ObjTokens tokens = new ObjTokens();

        final ObjToken t1 = new ObjToken("vp");
        tokens.add(t1);

        assertEquals(t1, tokens.get(0));

        final ObjToken t2 = new ObjToken("vn");
        tokens.add(t2);

        assertEquals(t2, tokens.get(1));

        try {
            tokens.get(-1);
        } catch (final IllegalArgumentException e) {
            return;
        }

        fail("no IllegalArgumentException caught");
    }
}