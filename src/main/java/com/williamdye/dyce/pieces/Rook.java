package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.Square;

/**
 * Represents a rook on a chessboard.
 *
 * @author William Dye
 */
public class Rook extends AbstractPiece
{

    /**
     * Construct a {@code Rook} of the specified color.
     *
     * @param color The rook's color
     */
    public Rook(PieceColor color)
    {
        super(color);
    }

    @Override
    public PieceType getPieceType()
    {
        return PieceType.ROOK;
    }

    @Override
    public final boolean isLegalSquare(final Square dest, final boolean ignorePins)
    {
        return (super.isLegalSquare(dest, ignorePins) && ((square.getRank() == dest.getRank()) || (square.getFile() == dest.getFile())));
    }

}
