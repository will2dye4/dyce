package com.williamdye.dyce.game

import javax.annotation.Nonnull

import com.williamdye.dyce.board.Square
import com.williamdye.dyce.pieces.Piece

/**
 * Partial implementation of the {@link Move} interface used for creating moves from PGN strings.
 *
 * @author William Dye
 */
class PartialMove implements Move
{
    /** The piece that was moved. */
    protected final Piece movedPiece

    /** The square that the moved piece "landed" on as a result of the move. */
    protected final Square endSquare

    /** The type of move that was made. */
    protected final MoveType moveType

    /**
     * Construct a {@code PartialMove} with the specified values and defaulting to {@code MoveType.NORMAL}.
     *
     * @param moved The piece that was moved
     * @param end The piece's ending (target) square
     */
    PartialMove(final @Nonnull Piece moved, final @Nonnull Square end)
    {
        this(moved, end, MoveType.NORMAL)
    }

    /**
     * Construct a {@code PartialMove} with the specified values.
     *
     * @param moved The piece that was moved
     * @param end The piece's ending (target) square
     * @param type The type of move that was made
     */
    PartialMove(final @Nonnull Piece moved, final @Nonnull Square end, final @Nonnull MoveType type)
    {
        this.movedPiece = moved
        this.endSquare = end
        this.moveType = type
    }

    @Override
    Piece getMovedPiece()
    {
        movedPiece
    }

    @Override
    Piece getCapturedPiece()
    {
        null
    }

    @Override
    Square getStartSquare()
    {
        null
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
        null
    }

    @Override
    int getMoveNumber()
    {
        0
    }

}
