package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.*;

/**
 * Represents a queen on a chessboard.
 *
 * @author William Dye
 */
public class Queen extends AbstractPiece
{

    /**
     * Construct a {@code Queen} of the specified color.
     *
     * @param color The queen's color
     */
    public Queen(PieceColor color)
    {
        super(color);
    }

    @Override
    public PieceType getPieceType()
    {
        return PieceType.QUEEN;
    }

    @Override
    public final boolean isLegalSquare(final Square dest, final boolean ignorePins)
    {
        return (super.isLegalSquare(dest, ignorePins) &&
                ((square.getRank() == dest.getRank()) || (square.getFile() == dest.getFile()) ||
                Paths.isSameDiagonal(square, dest)));
    }

}
