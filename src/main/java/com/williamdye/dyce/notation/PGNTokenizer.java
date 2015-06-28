package com.williamdye.dyce.notation;

import java.io.File;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import com.williamdye.dyce.util.CharBuffer;

/**
 * Tokenizer which converts a PGN file into a stream of PGN tokens.
 * @author William Dye
 */
public class PGNTokenizer
{

    /**
     * Represents the valid types of tokens that may appear in a PGN file.
     * @author William Dye
     */
    public static enum TokenType
    {
        /** A token denoting the end of the file */
        EOF,
        /** An integer token in the input file */
        INTEGER,
        /** A token representing the termination of a game; one of "1-0", "0-1", "1/2-1/2", or "*" */
        GAME_TERMINATION,
        /** A Numeric Annotation Glyph (NAG) token */
        NUMERIC_ANNOTATION_GLYPH,
        /** A string token in the input file (text enclosed in <code>&quot;</code> characters) */
        STRING,
        /** A symbol token (e.g., the name of a tag pair) */
        SYMBOL,
        /** The left angle bracket character "<code>&lt;</code>", reserved for future use by the PGN spec */
        LEFT_ANGLE_BRACKET("<"),
        /** The left bracket character "<code>[</code>", used to begin a tag pair */
        LEFT_BRACKET("["),
        /** The left parenthesis character "<code>(</code>", used to begin a Recursive Annotation Variation */
        LEFT_PAREN("("),
        /** The right angle bracket character "<code>&gt;</code>", reserved for future use by the PGN spec */
        RIGHT_ANGLE_BRACKET(">"),
        /** The right bracket character "<code>]</code>", used to end a tag pair */
        RIGHT_BRACKET("]"),
        /** The right parenthesis character "<code>)</code>", used to end a Recursive Annotation Variation */
        RIGHT_PAREN(")"),
        /** The period character "<code>.</code>", used in move number representations */
        PERIOD(".");

        private String string;

        /**
         * Default constructor. Creates a <code>TokenType</code> with <code>null</code> string.
         */
        TokenType()
        {
            this(null);
        }

        /**
         * Constructs a <code>TokenType</code> with the specified <code>value</code>.
         * @param value the value of the token type's string
         */
        TokenType(String value)
        {
            string = value;
        }

        /**
         * Accessor for a token type's string.
         * @return the string value of the token type
         */
        public String getString()
        {
            return string;
        }
    }

    /**
     * Represents a token found in a PGN file.
     * @author William Dye
     */
    public static class Token
    {
        private TokenType type;
        private String string;

        /**
         * Creates a <code>Token</code> with the specified <code>type</code> and the value of the token type as its string.
         * @param type the type of the token
         */
        public Token(TokenType type)
        {
            this.type = type;
            this.string = type.getString();
        }

        /**
         * Creates a <code>Token</code> with the specified <code>type</code> and <code>string</code>.
         * @param type the type of the token
         * @param string the string value of the token
         */
        public Token(TokenType type, String string)
        {
            this.type = type;
            this.string = string;
        }

        public TokenType getTokenType()
        {
            return type;
        }

        public String getTokenString()
        {
            return string;
        }
    }

    private static final char ASCII_MIN = 0x20;    /* the minimum value for ASCII printable characters (32) */
    private static final List<Character> GAME_TERMINATION_DRAW_VALID_CHARS = Arrays.asList('/', '2', '-', '1', '/', '2');
    private static final List<Character> VALID_SYMBOL_PUNCTUATION_CHARS = Arrays.asList('+', '-', '_', ':', '#', '=');

    private CharBuffer buffer;
    private Token current;
    private boolean paused;

    /**
     * Constructs a <code>PGNTokenizer</code> capable of reading from the specified <code>file</code>.
     * @param file the file to be tokenized
     */
    public PGNTokenizer(File file)
    {
        buffer = new CharBuffer(file);
        clearState();
    }

    /**
     * Constructs a <code>PGNTokenizer</code> capable of reading from the specified <code>String</code>.
     * @param string the String to be tokenized
     */
    public PGNTokenizer(String string)
    {
        buffer = new CharBuffer(string);
        clearState();
    }

    public Token getNextToken() throws ParseException
    {
        return next(false);
    }

    public Token peek() throws ParseException
    {
        return next(true);
    }

    public void consume() throws ParseException
    {
        getNextToken();
    }

    public boolean advance()
    {
        clearState();
        return buffer.advance();
    }

    public int getLineNumber()
    {
        return buffer.getLineNumber();
    }

    /* Resets the current token and paused flag. */
    protected void clearState()
    {
        current = null;
        paused = true;
    }

    /* Helper method for getNextToken() and peekNextToken(). */
    private Token next(boolean peek) throws ParseException
    {
        if (!paused || current == null)
            current = readToken();
        paused = peek;
        return current;
    }

    /* Helper method for next(). Handles reading from the buffer and generation of new tokens. */
    private Token readToken() throws ParseException
    {
        Token token;
        StringBuilder builder;
        char c = buffer.getNextChar(), prev, tmp;
        switch (c) {
            case ' ':
            case '\t':
            case '\r':
            case '\f':
                token = readToken();    /* ignore whitespace */
                break;
            case '\n':
                token = (advance() ? readToken() : new Token(TokenType.EOF));
                break;
            case '"':
                builder = new StringBuilder();
                prev = c;
                tmp = buffer.peekNextChar();
                while (isString(tmp, prev)) {
                    if ('\\' != tmp || '\\' == prev) {
                        builder.append(buffer.getNextChar());
                    }
                    prev = tmp;
                    tmp = buffer.peekNextChar();
                }
                if ('"' == tmp) {
                    buffer.getNextChar();   /* consume quote */
                    token = new Token(TokenType.STRING, builder.toString());
                    break;
                } else {
                    throw new ParseException(String.format("Unterminated string token: %s", builder.toString()), buffer.getLineNumber());
                }
            case '$':
                builder = new StringBuilder();
                tmp = buffer.peekNextChar();
                while (Character.isDigit(tmp)) {
                    builder.append(buffer.getNextChar());
                    tmp = buffer.peekNextChar();
                }
                if (builder.length() > 0) {
                    token = new Token(TokenType.NUMERIC_ANNOTATION_GLYPH, builder.toString());
                    break;
                } else {
                    throw new ParseException("Illegal NAG token", buffer.getLineNumber());
                }
            case '(':
                token = new Token(TokenType.LEFT_PAREN);
                break;
            case ')':
                token = new Token(TokenType.RIGHT_PAREN);
                break;
            case '[':
                token = new Token(TokenType.LEFT_BRACKET);
                break;
            case ']':
                token = new Token(TokenType.RIGHT_BRACKET);
                break;
            case '<':
                token = new Token(TokenType.LEFT_ANGLE_BRACKET);
                break;
            case '>':
                token = new Token(TokenType.RIGHT_ANGLE_BRACKET);
                break;
            case '.':
                token = new Token(TokenType.PERIOD);
                break;
            case '*':
                token = new Token(TokenType.GAME_TERMINATION, String.valueOf(c));
                break;
            default:
                if ('1' == c && '/' == buffer.peekNextChar()) {
                    for (char expected : GAME_TERMINATION_DRAW_VALID_CHARS) {
                        if (buffer.getNextChar() != expected) {
                            throw new ParseException("Unrecognized token (possibly malformed game terminator?)", buffer.getLineNumber());
                        }
                    }
                    token = new Token(TokenType.GAME_TERMINATION, PGN.GAME_TERMINATION_DRAW);
                } else if (Character.isLetterOrDigit(c)) {
                    boolean isStrictlyNumeric = Character.isDigit(c);
                    builder = new StringBuilder(String.valueOf(c));
                    tmp = buffer.peekNextChar();
                    while (isSymbol(tmp)) {
                        builder.append(buffer.getNextChar());
                        isStrictlyNumeric &= Character.isDigit(tmp);
                        tmp = buffer.peekNextChar();
                    }
                    String tokenString = builder.toString();
                    if (PGN.GAME_TERMINATION_BLACK_WINS.equals(tokenString) || PGN.GAME_TERMINATION_WHITE_WINS.equals(tokenString)) {
                        token = new Token(TokenType.GAME_TERMINATION, tokenString);
                    } else {
                        token = new Token((isStrictlyNumeric ? TokenType.INTEGER : TokenType.SYMBOL), tokenString);
                    }
                } else {
                    throw new ParseException(String.format("Unrecognized token: %s", c), buffer.getLineNumber());
                }
        }
        return token;
    }

    private static boolean isString(char c, char prev)
    {
        return (('"' == c && '\\' == prev) || ('"' != c && c >= ASCII_MIN));
    }

    private static boolean isSymbol(char c)
    {
        return Character.isLetterOrDigit(c) || VALID_SYMBOL_PUNCTUATION_CHARS.contains(c);
    }

}

