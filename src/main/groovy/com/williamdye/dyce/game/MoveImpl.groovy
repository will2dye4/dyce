package com.williamdye.dyce.game

import javax.annotation.Nonnull

import com.google.common.base.Preconditions

import com.williamdye.dyce.board.Square
import com.williamdye.dyce.pieces.Piece

/**
 * Implementation of the {@link Move} interface.
 *
 * @author William Dye
 */
class MoveImpl implements Move
{

    /** The piece that was moved. */
    protected final Piece movedPiece

    /** The piece that was captured, if any. */
    protected final Piece capturedPiece

    /** The move's starting square. */
    protected final Square startSquare

    /** The move's ending square. */
    protected final Square endSquare

    /** The move's type. */
    protected final MoveType moveType

    /** A string representing the move in PGN. */
    protected final String pgnString

    /** Whether the move was a pawn promotion. */
    protected final boolean isPawnPromotion

    /** The move's number in the game. */
    protected final int moveNumber

    /** A snapshot of the game's state at the time of the move. */
    protected final GameState state

    /**
     * Construct a {@code MoveImpl} with the specified values and defaulting to {@code MoveType.NORMAL}.
     *
     * @param moved The piece that was moved
     * @param captured The piece that was captured, if any
     * @param start The square the moved piece was on prior to the move
     * @param end The square the moved piece ended on as a result of the move
     * @param pgn The move's representation in PGN
     * @param number The move's number
     */
    MoveImpl(Piece moved, Piece captured, Square start, Square end, String pgn, int number)
    {
        this(moved, captured, start, end, MoveType.NORMAL, pgn, number)
    }

    /**
     * Construct a {@code MoveImpl} with the specified values.
     *
     * @param moved The piece that was moved
     * @param captured The piece that was captured, if any
     * @param start The square the moved piece was on prior to the move
     * @param end The square the moved piece ended on as a result of the move
     * @param type The type of move that was made
     * @param pgn The move's representation in PGN
     * @param number The move's number
     */
    MoveImpl(final @Nonnull Piece moved, final Piece captured, final @Nonnull Square start, final @Nonnull Square end,
             final @Nonnull MoveType type, final boolean isPawnPromotion, String pgn, int number)
    {
        Preconditions.checkArgument(number >= 1, "Invalid move number")
        Preconditions.checkArgument(start.board == end.board, "Incompatible squares")

        this.movedPiece = moved
        this.capturedPiece = captured
        this.startSquare = start
        this.endSquare = end
        this.pgnString = pgn
        this.moveNumber = number
        this.moveType = type
        this.isPawnPromotion = isPawnPromotion
        this.state = start.board.game.state.clone()
    }

    @Override
    Piece getMovedPiece()
    {
        movedPiece
    }

    @Override
    Piece getCapturedPiece()
    {
        capturedPiece
    }

    @Override
    Square getStartSquare()
    {
        startSquare
    }

    @Override
    Square getEndSquare()
    {
        endSquare
    }

    @Override
    MoveType getMoveType()
    {
        moveType
    }

    @Override
    String getPGNString()
    {
        pgnString
    }

    @Override
    int getMoveNumber()
    {
        moveNumber
    }

    @Override
    boolean isPawnPromotion()
    {
        isPawnPromotion
    }

    @Override
    GameState getState()
    {
        state
    }

    @Override
    String toString()
    {
        "($moveNumber) ${movedPiece.toString()}: $startSquare => $endSquare${capturedPiece ? " (captured ${capturedPiece.toString()})" : ""}"
    }

}
