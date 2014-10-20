package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.*;

/**
 * Represents a bishop on a chessboard.
 *
 * @author William Dye
 */
public class Bishop extends AbstractPiece
{

    /**
     * Construct a {@code Bishop} of the specified color.
     *
     * @param color The bishop's color
     */
    public Bishop(PieceColor color)
    {
        super(color);
    }

    @Override
    public PieceType getPieceType()
    {
        return PieceType.BISHOP;
    }

    @Override
    public final boolean isLegalSquare(final Square dest, final boolean ignorePins)
    {
        return (super.isLegalSquare(dest, ignorePins) && Paths.isSameDiagonal(square, dest));
    }

}
