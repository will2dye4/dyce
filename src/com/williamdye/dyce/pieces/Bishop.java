package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.Paths;
import com.williamdye.dyce.board.Square;

/**
 * @author William Dye
 */
public class Bishop extends AbstractPiece
{

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
    public final boolean isLegalSquare(Square dest, boolean ignorePins)
    {
        return (super.isLegalSquare(dest, ignorePins) && Paths.isSameDiagonal(square, dest));
    }

}
