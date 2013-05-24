package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.Square;
import com.williamdye.dyce.board.SquareImpl;

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
    public boolean isLegalSquare(Square dest)
    {
        return ((square.getRank() == dest.getRank()) || (square.getFile() == dest.getFile()) ||
                (SquareImpl.getRankDistance(square, dest) == SquareImpl.getFileDistance(square, dest)));
    }

}
