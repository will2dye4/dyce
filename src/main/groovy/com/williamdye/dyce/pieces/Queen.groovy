package com.williamdye.dyce.pieces

import com.williamdye.dyce.board.Paths
import com.williamdye.dyce.board.Square

/**
 * Represents a queen on a chessboard.
 *
 * @author William Dye
 */
class Queen extends AbstractPiece
{

    /**
     * Construct a {@code Queen} of the specified color.
     *
     * @param color The queen's color
     */
    Queen(final PieceColor color)
    {
        super(color)
    }

    @Override
    PieceType getPieceType()
    {
        PieceType.QUEEN
    }

    @Override
    final boolean isLegalSquare(final Square dest, final boolean ignorePins)
    {
        super.isLegalSquare(dest, ignorePins) &&
                (square.rank == dest.rank || square.file == dest.file || Paths.isSameDiagonal(square, dest))
    }

}
