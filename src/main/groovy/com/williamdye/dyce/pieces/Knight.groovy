package com.williamdye.dyce.pieces

import com.williamdye.dyce.board.Paths
import com.williamdye.dyce.board.Square

/**
 * Represents a knight on a chessboard.
 *
 * @author William Dye
 */
class Knight extends AbstractPiece
{

    /**
     * Construct a {@code Knight} of the specified color.
     *
     * @param color The knight's color
     */
    Knight(final PieceColor color)
    {
        super(color)
    }

    @Override
    PieceType getPieceType()
    {
        PieceType.KNIGHT
    }

    @Override
    final boolean isLegalSquare(final Square dest, final boolean ignorePins)
    {
        super.isLegalSquare(dest, ignorePins) &&
                ((Paths.getRankDistance(square, dest) == 2 && Paths.getFileDistance(square, dest) == 1) ||
                        (Paths.getRankDistance(square, dest) == 1 && Paths.getFileDistance(square, dest) == 2))
    }

}
