package com.williamdye.dyce.notation

import java.text.ParseException

import com.williamdye.dyce.board.DefaultChessboard
import com.williamdye.dyce.exception.AmbiguousMoveException
import com.williamdye.dyce.exception.IllegalMoveException
import com.williamdye.dyce.game.Game

import static com.williamdye.dyce.notation.PGNTokenizer.TokenType

/**
 * @author William Dye
 */
class PGNReader
{

    private int depth
    private Game game
    private PGNTokenizer tokenizer

    PGNReader(final String filepath) {
        this.tokenizer = new PGNTokenizer(new File(filepath))
        this.game = DefaultChessboard.newInstance().game
        this.depth = 0
    }

    Game read() throws AmbiguousMoveException, IllegalMoveException, ParseException {
        pgn_game()
        game.moveHistory.rewind()
        game.state.reset()
        game
    }

    void pgn_game() throws AmbiguousMoveException, IllegalMoveException, ParseException
    {
        tag_section()
        movetext_section()
    }

    private void tag_section() throws ParseException
    {
        if (tokenizer.peek().tokenType == TokenType.LEFT_BRACKET) {
            tag_pair()
            tag_section()
        }
    }

    private void tag_pair() throws ParseException
    {
        if (tokenizer.nextToken.tokenType != TokenType.LEFT_BRACKET) {
            throw new ParseException("Expected '[' to begin tag pair", tokenizer.lineNumber)
        }
        final String tagName = tag_name()
        tag_value(tagName)
        if (tokenizer.nextToken.tokenType != TokenType.RIGHT_BRACKET) {
            throw new ParseException("Expected ']' to end tag pair", tokenizer.lineNumber)
        }
    }

    private String tag_name() throws ParseException
    {
        final PGNTokenizer.Token token = tokenizer.nextToken
        if (token.tokenType != TokenType.SYMBOL) {
            throw new ParseException("Expected tag name to be a symbol", tokenizer.lineNumber)
        }
        token.tokenString
    }

    private void tag_value(final String tagName) throws ParseException
    {
        final PGNTokenizer.Token token = tokenizer.nextToken
        if (token.tokenType != TokenType.STRING) {
            throw new ParseException("Expected tag value to be a string", tokenizer.lineNumber)
        }
        game.PGN.setTagValue(tagName, token.tokenString)
    }

    private void movetext_section() throws AmbiguousMoveException, IllegalMoveException, ParseException
    {
        element_sequence()
        if (tokenizer.nextToken.tokenType != TokenType.GAME_TERMINATION) {
            throw new ParseException("Expected movetext section to end with a game termination marker", tokenizer.lineNumber)
        }
    }

    private void element_sequence() throws AmbiguousMoveException, IllegalMoveException, ParseException
    {
        final TokenType nextTokenType = tokenizer.peek().tokenType
        if ([TokenType.INTEGER, TokenType.SYMBOL, TokenType.NUMERIC_ANNOTATION_GLYPH].contains(nextTokenType)) {
            element()
            element_sequence()
        } else if (nextTokenType == TokenType.LEFT_PAREN) {
            recursive_variation()
            element_sequence()
        }
    }

    private void element() throws AmbiguousMoveException, IllegalMoveException, ParseException
    {
        final TokenType nextTokenType = tokenizer.peek().tokenType
        if (nextTokenType == TokenType.INTEGER) {
            move_number_indication()
        } else if (nextTokenType == TokenType.SYMBOL) {
            final PGNTokenizer.Token token = tokenizer.nextToken
            if (depth == 0) {
                game.chessboard.move(token.tokenString)
            }
        } else if (nextTokenType == TokenType.NUMERIC_ANNOTATION_GLYPH) {
            tokenizer.consume()    /* we are currently ignoring NAGs */
        } else {
            throw new ParseException("Expected element to be an integer, symbol, or NAG", tokenizer.lineNumber)
        }
    }

    private void move_number_indication() throws ParseException
    {
        if (tokenizer.nextToken.tokenType != TokenType.INTEGER) {
            throw new ParseException("Expected move number indication to begin with an integer", tokenizer.lineNumber)
        }
        periods()
    }

    private void periods() throws ParseException
    {
        if (tokenizer.peek().tokenType == TokenType.PERIOD) {
            tokenizer.consume()
            periods()
        }
    }

    private void recursive_variation() throws AmbiguousMoveException, IllegalMoveException, ParseException
    {
        if (tokenizer.nextToken.tokenType != TokenType.LEFT_PAREN) {
            throw new ParseException("Expected '(' to begin recursive variation", tokenizer.lineNumber)
        }
        depth++
        element_sequence()
        depth--
        if (tokenizer.nextToken.tokenType != TokenType.RIGHT_PAREN) {
            throw new ParseException("Expected ')' to end recursive variation", tokenizer.lineNumber)
        }
    }

}
