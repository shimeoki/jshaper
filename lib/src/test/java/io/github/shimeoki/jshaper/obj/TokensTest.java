package io.github.shimeoki.jshaper.obj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public final class TokensTest {

    @Test
    public void make() {
        final Tokens t = new Tokens();

        assertTrue(t.empty());
    }

    @Test
    public void addNull() {
        final Tokens t = new Tokens();

        try {
            t.add(null);
        } catch (final NullPointerException e) {
            return;
        }

        fail("no NullPointerException caught");
    }

    @Test
    public void empty() {
        final Tokens t = new Tokens();

        assertEquals(t.empty(), t.size() == 0);
    }

    @Test
    public void notEmpty() {
        final Tokens t = new Tokens();
        t.add(new Token("#"));

        assertEquals(!t.empty(), t.size() != 0);
    }

    @Test
    public void add() {
        final Tokens tokens = new Tokens();
        final Token token = new Token("v");
        tokens.add(token);

        assertEquals(1, tokens.size());
        assertEquals(token, tokens.get(0));
    }

    @Test
    public void clear() {
        final Tokens t = new Tokens();
        t.add(new Token("vt"));

        assertTrue(!t.empty());

        t.clear();

        assertTrue(t.empty());
    }

    @Test
    public void lineToken() {
        final Tokens tokens = new Tokens();
        final Token token = new Token("vn");

        tokens.add(token);

        assertEquals(token, tokens.get(0));
        assertEquals(token, tokens.lineToken());
    }

    @Test
    public void noLineToken() {
        final Tokens t = new Tokens();

        assertEquals(null, t.lineToken());
    }

    @Test
    public void noLineTokenIs() {
        final Tokens t = new Tokens();

        assertFalse(t.lineTokenTypeIs(Token.Type.PARAMETER_SPACE_VERTEX));
    }

    @Test
    public void lineTokenIs() {
        final Tokens tokens = new Tokens();
        final Token token = new Token("f");

        tokens.add(token);

        assertTrue(tokens.lineTokenTypeIs(token.type()));
        assertTrue(tokens.lineTokenTypeIs(Token.Type.FACE));
        assertFalse(tokens.lineTokenTypeIs(Token.Type.TEXTURE_VERTEX));
    }

    @Test
    public void size() {
        final Tokens t = new Tokens();
        assertEquals(0, t.size());

        t.add(new Token("vp"));
        assertEquals(1, t.size());

        t.add(new Token("g"));
        assertEquals(2, t.size());

        t.add(new Token("100"));
        assertEquals(3, t.size());

        t.clear();
        assertEquals(0, t.size());
    }

    @Test
    public void validIndex() {
        final Tokens t = new Tokens();
        assertFalse(t.validIndex(0));

        t.add(new Token("o"));
        assertTrue(t.validIndex(0));
        assertFalse(t.validIndex(1));

        t.add(new Token("gg"));
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
        final Tokens tokens = new Tokens();

        final Token t1 = new Token("vp");
        tokens.add(t1);

        assertEquals(t1, tokens.get(0));

        final Token t2 = new Token("vn");
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
