package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.Square;

/**
 * @author William Dye
 */
public class Rook extends AbstractPiece
{

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
    public final boolean isLegalSquare(Square dest, boolean ignorePins)
    {
        return (super.isLegalSquare(dest, ignorePins) && ((square.getRank() == dest.getRank()) || (square.getFile() == dest.getFile())));
    }

}
