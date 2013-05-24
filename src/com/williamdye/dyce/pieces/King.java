package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.Square;
import com.williamdye.dyce.board.SquareImpl;

/**
 * @author William Dye
 */
public class King extends AbstractPiece
{

    public King(PieceColor color)
    {
        super(color);
    }

    @Override
    public PieceType getPieceType()
    {
        return PieceType.KING;
    }

    @Override
    public boolean isLegalSquare(Square dest)
    {
        if (square.getFile() == dest.getFile())
            return (SquareImpl.getRankDistance(square, dest) == 1);
        if (square.getRank() == dest.getRank())
            return (SquareImpl.getFileDistance(square, dest) == 1);
        return ((SquareImpl.getRankDistance(square, dest) == 1) && (SquareImpl.getFileDistance(square, dest) == 1));
    }

}
