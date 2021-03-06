package com.williamdye.dyce.game

import com.williamdye.dyce.board.Square
import com.williamdye.dyce.pieces.PieceColor

/**
 * The game state contains metadata about a chess game in progress.
 *
 * @author William Dye
 */
interface GameState
{

    /**
     * Accessor for the currently active color, i.e., which player is to move next.
     *
     * @return The {@code PieceColor} corresponding to the active player
     */
    PieceColor getActiveColor()

    /**
     * Set the active color to the opposite of its current value.
     */
    void toggleActiveColor()

    /**
     * Mutator for the game's active color.
     *
     * @param color The new active color
     */
    void setActiveColor(PieceColor color)

    /**
     * Accessor for the castling availability.
     *
     * @return The game's {@code CastlingAvailability} object
     */
    CastlingAvailability getCastlingAvailability()

    /**
     * Return a string representing the current castling availability of both players.
     *
     * @return The current castling status as a string (e.g., "KQkq")
     */
    String getCastlingString()

    /**
     * Accessor for the game's current en passant target square.
     *
     * @return The en passant target, if there currently is one
     */
    Square getEnPassantTargetSquare()

    /**
     * Mutator for the game's en passant target square.
     *
     * @param target The new en passant target
     */
    void setEnPassantTargetSquare(Square target)

    /**
     * Accessor for the game's current move count.
     *
     * @return The move count (number of moves made by both sides)
     */
    int getMoveCount()

    /**
     * Increment the game's move count by one.
     */
    void incrementMoveCount()

    /**
     * Mutator for the game's move count.
     *
     * @param count The new move count
     */
    void setMoveCount(int count)

    /**
     * Accessor for the game's current half move clock.
     *
     * @return The half move clock (used in checking for stalemate)
     */
    int getHalfMoveClock()

    /**
     * Increment the game's half move clock by one.
     */
    void incrementHalfMoveClock()

    /**
     * Reset the game's half move clock back to zero (0).
     */
    void resetHalfMoveClock()

    /**
     * Mutator for the game's half move clock.
     *
     * @param clock The new half move clock
     */
    void setHalfMoveClock(int clock)

    /**
     * Accessor for the game's half move total.
     *
     * @return The half move total (approximately double the move count)
     */
    int getHalfMoveTotal()

    /**
     * Increment the game's half move total by one.
     */
    void incrementHalfMoveTotal()

    /**
     * Mutator for the game's half move total.
     *
     * @param total The new half move total
     */
    void setHalfMoveTotal(int total)

    /**
     * Reset the game state to initial values.
     */
    void reset()

    /**
     * Overrides {@link Object#clone} to return {@code GameState}.
     *
     * @return A cloned {@code GameState} instance
     */
    GameState clone()

}
