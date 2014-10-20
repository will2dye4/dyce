package com.williamdye.dyce.game;

import com.google.common.base.Preconditions;

import com.williamdye.dyce.board.Square;
import com.williamdye.dyce.pieces.Piece;

/**
 * Partial implementation of the {@link Move} interface used for creating moves from PGN strings.
 *
 * @author William Dye
 */
public class PartialMove implements Move
{
    /** The piece that was moved. */
    protected final Piece movedPiece;

    /** The square that the moved piece "landed" on as a result of the move. */
    protected final Square endSquare;

    /** The type of move that was made. */
    protected final MoveType moveType;

    /**
     * Construct a {@code PartialMove} with the specified values and defaulting to {@code MoveType.NORMAL}.
     *
     * @param moved The piece that was moved
     * @param end The piece's ending (target) square
     */
    public PartialMove(Piece moved, Square end)
    {
        this(moved, end, MoveType.NORMAL);
    }

    /**
     * Construct a {@code PartialMove} with the specified values.
     *
     * @param moved The piece that was moved
     * @param end The piece's ending (target) square
     * @param type The type of move that was made
     */
    public PartialMove(Piece moved, Square end, MoveType type)
    {
        Preconditions.checkNotNull(moved, "'moved' may not be null when creating a partial move");
        Preconditions.checkNotNull(end, "'end' may not be null when creating a partial move");
        Preconditions.checkNotNull(type, "'type' may not be null when creating a partial move");

        this.movedPiece = moved;
        this.endSquare = end;
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
        return null;
    }

    @Override
    public Square getStartSquare()
    {
        return null;
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
        return null;
    }

    @Override
    public int getMoveNumber()
    {
        return 0;
    }

}
