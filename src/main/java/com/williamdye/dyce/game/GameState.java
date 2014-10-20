package com.williamdye.dyce.game;

import com.williamdye.dyce.board.Square;
import com.williamdye.dyce.pieces.PieceColor;

/**
 * The game state contains metadata about a chess game in progress.
 *
 * @author William Dye
 */
public interface GameState
{

    /**
     * Accessor for the currently active color, i.e., which player is to move next.
     *
     * @return The {@code PieceColor} corresponding to the active player
     */
    public PieceColor getActiveColor();

    /**
     * Set the active color to the opposite of its current value.
     */
    public void toggleActiveColor();

    /**
     * Mutator for the game's active color.
     *
     * @param color The new active color
     */
    public void setActiveColor(PieceColor color);

    /**
     * Accessor for the castling availability.
     *
     * @return The game's {@code CastlingAvailability} object
     */
    public CastlingAvailability getCastlingAvailability();

    /**
     * Return a string representing the current castling availability of both players.
     *
     * @return The current castling status as a string (e.g., "KQkq")
     */
    public String getCastlingString();

    /**
     * Accessor for the game's current en passant target square.
     *
     * @return The en passant target, if there currently is one
     */
    public Square getEnPassantTargetSquare();

    /**
     * Mutator for the game's en passant target square.
     *
     * @param target The new en passant target
     */
    public void setEnPassantTargetSquare(Square target);

    /**
     * Accessor for the game's current move count.
     *
     * @return The move count (number of moves made by both sides)
     */
    public int getMoveCount();

    /**
     * Increment the game's move count by one.
     */
    public void incrementMoveCount();

    /**
     * Mutator for the game's move count.
     *
     * @param count The new move count
     */
    public void setMoveCount(int count);

    /**
     * Accessor for the game's current half move clock.
     *
     * @return The half move clock (used in checking for stalemate)
     */
    public int getHalfMoveClock();

    /**
     * Increment the game's half move clock by one.
     */
    public void incrementHalfMoveClock();

    /**
     * Mutator for the game's half move clock.
     *
     * @param clock The new half move clock
     */
    public void setHalfMoveClock(int clock);

    /**
     * Accessor for the game's half move total.
     *
     * @return The half move total (approximately double the move count)
     */
    public int getHalfMoveTotal();

    /**
     * Increment the game's half move total by one.
     */
    public void incrementHalfMoveTotal();

    /**
     * Mutator for the game's half move total.
     *
     * @param total The new half move total
     */
    public void setHalfMoveTotal(int total);

}
