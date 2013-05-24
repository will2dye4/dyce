package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.Square;
import com.williamdye.dyce.board.SquareImpl;

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
    public boolean isLegalSquare(Square dest)
    {
        return (SquareImpl.getRankDistance(square, dest) == SquareImpl.getFileDistance(square, dest));
    }

}
