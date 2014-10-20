package com.williamdye.dyce.game;

import com.google.common.base.Preconditions;

import com.williamdye.dyce.board.Square;
import com.williamdye.dyce.pieces.Piece;

/**
 * Implementation of the {@link Move} interface.
 *
 * @author William Dye
 */
public class MoveImpl implements Move
{

    /** The piece that was moved. */
    protected final Piece movedPiece;

    /** The piece that was captured, if any. */
    protected final Piece capturedPiece;

    /** The move's starting square. */
    protected final Square startSquare;

    /** The move's ending square. */
    protected final Square endSquare;

    /** The move's type. */
    protected final MoveType moveType;

    /** A string representing the move in PGN. */
    protected final String pgnString;

    /** The move's number in the game. */
    protected final int moveNumber;

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
    public MoveImpl(Piece moved, Piece captured, Square start, Square end, String pgn, int number)
    {
        this(moved, captured, start, end, MoveType.NORMAL, pgn, number);
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
    public MoveImpl(Piece moved, Piece captured, Square start, Square end, MoveType type, String pgn, int number)
    {
        Preconditions.checkNotNull(moved, "'moved' may not be null when creating a move");
        Preconditions.checkNotNull(start, "'start' may not be null when creating a move");
        Preconditions.checkNotNull(end, "'end' may not be null when creating a move");
        Preconditions.checkNotNull(type, "'type' may not be null when creating a move");
        Preconditions.checkArgument(number >= 1, "Invalid move number");
        Preconditions.checkArgument(start.getBoard().equals(end.getBoard()), "Incompatible squares");

        this.movedPiece = moved;
        this.capturedPiece = captured;
        this.startSquare = start;
        this.endSquare = end;
        this.pgnString = pgn;
        this.moveNumber = number;
        this.moveType = type;
    }

    @Override
    public Piece getMovedPiece()
    {
        return movedPiece;
    }

    @Override
    public Piece getCapturedPiece()
    {
        return capturedPiece;
    }

    @Override
    public Square getStartSquare()
    {
        return startSquare;
    }

    @Override
    public Square getEndSquare()
    {
        return endSquare;
    }

    @Override
    public MoveType getMoveType()
    {
        return moveType;
    }

    @Override
    public String getPGNString()
    {
        return pgnString;
    }

    @Override
    public int getMoveNumber()
    {
        return moveNumber;
    }

    @Override
    public String toString()
    {
        String captureInfo = ((capturedPiece == null) ? "" : String.format(" (captured %s)", capturedPiece.toString()));
        return String.format("(%d) %s: %s => %s%s", moveNumber, movedPiece.toString(), startSquare, endSquare, captureInfo);
    }
}
