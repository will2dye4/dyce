package com.williamdye.dyce.game

import com.williamdye.dyce.board.Square
import com.williamdye.dyce.pieces.Piece

/**
 * This is dyce's representation of one player's move in a chess game.
 *
 * @author William Dye
 */
interface Move
{

    /**
     * Accessor for the piece that was moved.
     *
     * @return The piece that moved
     */
    Piece getMovedPiece()

    /**
     * Accessor for the captured piece, if there was one.
     *
     * @return The piece that was captured, if any
     */
    Piece getCapturedPiece()

    /**
     * Accessor for the move's "starting square."
     *
     * @return The square that the piece was on before the move was made
     */
    Square getStartSquare()

    /**
     * Accessor for the move's "ending square."
     *
     * @return The square that the piece "landed" on as a result of the move
     */
    Square getEndSquare()

    /**
     * Accessor for the move's type (normal, castling, en passant, or checkmate).
     *
     * @return The type of move that was made
     */
    MoveType getMoveType()

    /**
     * Accessor for the move's PGN string.
     *
     * @return A string representing the move in Portable Game Notation (PGN)
     */
    String getPGNString()

    /**
     * Accessor for the move's number.
     *
     * @return The number of the move
     */
    int getMoveNumber()

    /**
     * Whether or not the move is a pawn promotion.
     *
     * @return {@code true} if the move is a pawn promotion, {@code false} otherwise
     */
    boolean isPawnPromotion()

    /**
     * Accessor for the state of the game after the move has been made.
     *
     * @return A {@code GameState} instance representing the state associated with the move
     */
    GameState getState()

}
