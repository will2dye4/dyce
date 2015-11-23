package com.williamdye.dyce.pieces

import com.williamdye.dyce.board.Square

/**
 * Represents a rook on a chessboard.
 *
 * @author William Dye
 */
class Rook extends AbstractPiece
{

    /**
     * Construct a {@code Rook} of the specified color.
     *
     * @param color The rook's color
     */
    Rook(final PieceColor color)
    {
        super(color)
    }

    @Override
    PieceType getPieceType()
    {
        PieceType.ROOK
    }

    @Override
    final boolean isLegalSquare(final Square dest, final boolean ignorePins)
    {
        super.isLegalSquare(dest, ignorePins) && (square.rank == dest.rank || square.file == dest.file)
    }

}
