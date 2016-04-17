package com.williamdye.dyce.game

import com.williamdye.dyce.board.Square
import com.williamdye.dyce.pieces.PieceColor

/**
 * Implementation of the {@link GameState} interface.
 *
 * @author William Dye
 */
class GameStateImpl implements GameState
{

    /** The currently active color. */
    protected PieceColor toMove

    /** The current en passant target square, if there is one. */
    protected Square enPassantTarget

    /** The current castling status. */
    protected CastlingAvailability castling

    /** The current move count. */
    protected int moveCount

    /** The current half move clock. */
    protected int halfMoveClock

    /** The current half move total. */
    protected int halfMoveTotal

    /**
     * Construct a {@code GameStateImpl} with the default initial values.
     */
    GameStateImpl()
    {
        reset()
    }

    /**
     * Construct a {@code GameStateImpl} with custom initial values.
     *
     * @param color The active color (i.e., player to move)
     * @param epTarget The current en passant target, or {@code null} if there isn't one
     * @param castle The game's castling state
     * @param moves The game's initial move count
     * @param halfMoves The game's current half move clock
     */
    GameStateImpl(PieceColor color, Square epTarget, CastlingAvailability castle, int moves, int halfMoves)
    {
        this.toMove = color
        this.enPassantTarget = epTarget
        this.castling = castle
        this.moveCount = moves
        this.halfMoveClock = halfMoves
        this.halfMoveTotal = 0
    }

    @Override
    PieceColor getActiveColor()
    {
        toMove
    }

    @Override
    void toggleActiveColor()
    {
        toMove = ~toMove
    }

    @Override
    void setActiveColor(final PieceColor color)
    {
        toMove = color
    }

    @Override
    Square getEnPassantTargetSquare()
    {
        enPassantTarget
    }

    @Override
    void setEnPassantTargetSquare(final Square target)
    {
        enPassantTarget = target
    }

    @Override
    int getMoveCount()
    {
        moveCount
    }

    @Override
    void incrementMoveCount()
    {
        moveCount += 1
    }

    @Override
    void setMoveCount(final int count)
    {
        moveCount = count
    }

    @Override
    int getHalfMoveClock()
    {
        halfMoveClock
    }

    @Override
    void incrementHalfMoveClock()
    {
        halfMoveClock += 1
    }

    @Override
    void resetHalfMoveClock()
    {
        halfMoveClock = 0
    }

    @Override
    void setHalfMoveClock(final int clock)
    {
        halfMoveClock = clock
    }

    @Override
    int getHalfMoveTotal()
    {
        halfMoveTotal
    }

    @Override
    void incrementHalfMoveTotal()
    {
        halfMoveTotal += 1
    }

    @Override
    void setHalfMoveTotal(final int total)
    {
        halfMoveTotal = total
    }

    @Override
    CastlingAvailability getCastlingAvailability()
    {
        castling
    }

    @Override
    String getCastlingString()
    {
        castling.toString()
    }

    @Override
    void reset()
    {
        toMove = PieceColor.WHITE
        enPassantTarget = null
        castling = new CastlingAvailability()
        moveCount = 1
        halfMoveClock = 0
        halfMoveTotal = 0
    }

    @Override
    String toString()
    {
        "${toMove.toString()} ${castling.toString()} ${enPassantTarget?.toString() ?: "-"} $halfMoveClock $moveCount"
    }

    @Override
    GameState clone()
    {
        final GameState clone = new GameStateImpl(activeColor, enPassantTarget, castling, moveCount, halfMoveClock)
        clone.halfMoveTotal = halfMoveTotal
        clone
    }

}
