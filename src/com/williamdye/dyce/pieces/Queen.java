package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.*;

/**
 * @author William Dye
 */
public class Queen extends AbstractPiece
{

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
    public final boolean isLegalSquare(Square dest, boolean ignorePins)
    {
        return (super.isLegalSquare(dest, ignorePins) &&
                ((square.getRank() == dest.getRank()) || (square.getFile() == dest.getFile()) ||
                Paths.isSameDiagonal(square, dest)));
    }

}
