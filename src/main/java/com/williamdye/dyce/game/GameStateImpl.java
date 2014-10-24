package com.williamdye.dyce.game;

import com.williamdye.dyce.board.Square;
import com.williamdye.dyce.pieces.PieceColor;

/**
 * Implementation of the {@link GameState} interface.
 *
 * @author William Dye
 */
public class GameStateImpl implements GameState
{

    /** The currently active color. */
    protected PieceColor toMove;

    /** The current en passant target square, if there is one. */
    protected Square enPassantTarget;

    /** The current castling status. */
    protected CastlingAvailability castling;

    /** The current move count. */
    protected int moveCount;

    /** The current half move clock. */
    protected int halfMoveClock;

    /** The current half move total. */
    protected int halfMoveTotal;

    /**
     * Construct a {@code GameStateImpl} with the default initial values.
     */
    public GameStateImpl()
    {
        this(PieceColor.WHITE, null, new CastlingAvailability(), 1, 0);
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
    public GameStateImpl(PieceColor color, Square epTarget, CastlingAvailability castle, int moves, int halfMoves)
    {
        this.toMove = color;
        this.enPassantTarget = epTarget;
        this.castling = castle;
        this.moveCount = moves;
        this.halfMoveClock = halfMoves;
        this.halfMoveTotal = 0;
    }

    @Override
    public PieceColor getActiveColor()
    {
        return toMove;
    }

    @Override
    public void toggleActiveColor()
    {
        if (toMove == PieceColor.BLACK)
            moveCount += 1;
        toMove = PieceColor.oppositeOf(toMove);
    }

    @Override
    public void setActiveColor(final PieceColor color)
    {
        toMove = color;
    }

    @Override
    public Square getEnPassantTargetSquare()
    {
        return enPassantTarget;
    }

    @Override
    public void setEnPassantTargetSquare(final Square target)
    {
        enPassantTarget = target;
    }

    @Override
    public int getMoveCount()
    {
        return moveCount;
    }

    @Override
    public void incrementMoveCount()
    {
        moveCount += 1;
    }

    @Override
    public void setMoveCount(final int count)
    {
        moveCount = count;
    }

    @Override
    public int getHalfMoveClock()
    {
        return halfMoveClock;
    }

    @Override
    public void incrementHalfMoveClock()
    {
        halfMoveClock += 1;
    }

    @Override
    public void resetHalfMoveClock()
    {
        halfMoveClock = 0;
    }

    @Override
    public void setHalfMoveClock(final int clock)
    {
        halfMoveClock = clock;
    }

    @Override
    public int getHalfMoveTotal()
    {
        return halfMoveTotal;
    }

    @Override
    public void incrementHalfMoveTotal()
    {
        halfMoveTotal += 1;
    }

    @Override
    public void setHalfMoveTotal(final int total)
    {
        halfMoveTotal = total;
    }

    @Override
    public CastlingAvailability getCastlingAvailability()
    {
        return castling;
    }

    @Override
    public String getCastlingString()
    {
        return castling.toString();
    }

    @Override
    public String toString()
    {
        String enPassant = (enPassantTarget == null ? "-" : enPassantTarget.toString());
        return String.format("%s %s %s %d %d", toMove.toString(), castling.toString(), enPassant, halfMoveClock, moveCount);
    }

}
