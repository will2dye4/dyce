package com.williamdye.dyce.pieces

import com.williamdye.dyce.board.Paths
import com.williamdye.dyce.board.Square

/**
 * Represents a bishop on a chessboard.
 *
 * @author William Dye
 */
class Bishop extends AbstractPiece
{

    /**
     * Construct a {@code Bishop} of the specified color.
     *
     * @param color The bishop's color
     */
    Bishop(final PieceColor color)
    {
        super(color)
    }

    @Override
    PieceType getPieceType()
    {
        PieceType.BISHOP
    }

    @Override
    final boolean isLegalSquare(final Square dest, final boolean ignorePins)
    {
        super.isLegalSquare(dest, ignorePins) && Paths.isSameDiagonal(square, dest)
    }

}
