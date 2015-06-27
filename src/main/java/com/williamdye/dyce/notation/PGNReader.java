package com.williamdye.dyce.notation;

import java.io.*;
import java.text.ParseException;

import com.williamdye.dyce.exception.AmbiguousMoveException;
import com.williamdye.dyce.exception.IllegalMoveException;
import com.williamdye.dyce.game.*;

import static com.williamdye.dyce.notation.PGNTokenizer.TokenType;

/**
 * @author William Dye
 */
public class PGNReader
{

    private int depth;
    private Game game;
    private PGNTokenizer tokenizer;

    public PGNReader(final String filepath) {
        this.tokenizer = new PGNTokenizer(new File(filepath));
        this.game = new GameImpl();
        this.depth = 0;
    }

    public Game read() throws AmbiguousMoveException, IllegalMoveException, ParseException {
        pgn_game();
        game.getMoveHistory().rewind();
        // TODO - reset game state
        return game;
    }

    private void pgn_game() throws AmbiguousMoveException, IllegalMoveException, ParseException
    {
        tag_section();
        movetext_section();
    }

    private void tag_section() throws ParseException
    {
        if (tokenizer.peek().getTokenType() == TokenType.LEFT_BRACKET) {
            tag_pair();
            tag_section();
        }
    }

    private void tag_pair() throws ParseException
    {
        if (tokenizer.getNextToken().getTokenType() != TokenType.LEFT_BRACKET) {
            throw new ParseException("Expected '[' to begin tag pair", tokenizer.getLineNumber());
        }
        final String tagName = tag_name();
        tag_value(tagName);
        if (tokenizer.getNextToken().getTokenType() != TokenType.RIGHT_BRACKET) {
            throw new ParseException("Expected ']' to end tag pair", tokenizer.getLineNumber());
        }
    }

    private String tag_name() throws ParseException
    {
        final PGNTokenizer.Token token = tokenizer.getNextToken();
        if (token.getTokenType() != TokenType.SYMBOL) {
            throw new ParseException("Expected tag name to be a symbol", tokenizer.getLineNumber());
        }
        return token.getTokenString();
    }

    private void tag_value(final String tagName) throws ParseException
    {
        final PGNTokenizer.Token token = tokenizer.getNextToken();
        if (token.getTokenType() != TokenType.STRING) {
            throw new ParseException("Expected tag value to be a string", tokenizer.getLineNumber());
        }
        game.getPGN().setTagValue(tagName, token.getTokenString());
    }

    private void movetext_section() throws AmbiguousMoveException, IllegalMoveException, ParseException
    {
        element_sequence();
        if (tokenizer.getNextToken().getTokenType() != TokenType.GAME_TERMINATION) {
            throw new ParseException("Expected movetext section to end with a game termination marker", tokenizer.getLineNumber());
        }
    }

    private void element_sequence() throws AmbiguousMoveException, IllegalMoveException, ParseException
    {
        final TokenType nextTokenType = tokenizer.peek().getTokenType();
        if (nextTokenType == TokenType.INTEGER || nextTokenType == TokenType.SYMBOL || nextTokenType == TokenType.NUMERIC_ANNOTATION_GLYPH) {
            element();
            element_sequence();
        } else if (nextTokenType == PGNTokenizer.TokenType.LEFT_PAREN) {
            recursive_variation();
            element_sequence();
        }
    }

    private void element() throws AmbiguousMoveException, IllegalMoveException, ParseException
    {
        final TokenType nextTokenType = tokenizer.peek().getTokenType();
        if (nextTokenType == TokenType.INTEGER) {
            move_number_indication();
        } else if (nextTokenType == TokenType.SYMBOL) {
            final PGNTokenizer.Token token = tokenizer.getNextToken();
            if (depth == 0) {
                game.getChessboard().move(token.getTokenString());
            }
        } else if (nextTokenType == TokenType.NUMERIC_ANNOTATION_GLYPH) {
            tokenizer.consume();    /* we are currently ignoring NAGs */
        } else {
            throw new ParseException("Expected element to be an integer, symbol, or NAG", tokenizer.getLineNumber());
        }
    }

    private void move_number_indication() throws ParseException
    {
        if (tokenizer.getNextToken().getTokenType() != TokenType.INTEGER) {
            throw new ParseException("Expected move number indication to begin with an integer", tokenizer.getLineNumber());
        }
        periods();
    }

    private void periods() throws ParseException
    {
        if (tokenizer.peek().getTokenType() == TokenType.PERIOD) {
            tokenizer.consume();
            periods();
        }
    }

    private void recursive_variation() throws AmbiguousMoveException, IllegalMoveException, ParseException
    {
        if (tokenizer.getNextToken().getTokenType() != TokenType.LEFT_PAREN) {
            throw new ParseException("Expected '(' to begin recursive variation", tokenizer.getLineNumber());
        }
        depth++;
        element_sequence();
        depth--;
        if (tokenizer.getNextToken().getTokenType() != TokenType.RIGHT_PAREN) {
            throw new ParseException("Expected ')' to end recursive variation", tokenizer.getLineNumber());
        }
    }

}
