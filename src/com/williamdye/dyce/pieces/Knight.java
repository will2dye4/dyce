package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.Square;
import com.williamdye.dyce.board.SquareImpl;

/**
 * @author William Dye
 */
public class Knight extends AbstractPiece
{

    public Knight(PieceColor color)
    {
        super(color);
    }

    @Override
    public PieceType getPieceType()
    {
        return PieceType.KNIGHT;
    }

    @Override
    public boolean isLegalSquare(Square dest)
    {
        return (((SquareImpl.getRankDistance(square, dest) == 2) && (SquareImpl.getFileDistance(square, dest) == 1)) ||
                ((SquareImpl.getRankDistance(square, dest) == 1) && (SquareImpl.getFileDistance(square, dest) == 2)));
    }

}
