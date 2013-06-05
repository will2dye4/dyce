package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.Square;
import com.williamdye.dyce.board.Paths;

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
    public final boolean isLegalSquare(Square dest)
    {
        return (super.isLegalSquare(dest) &&
                ((square.getRank() == dest.getRank()) || (square.getFile() == dest.getFile()) ||
                Paths.isSameDiagonal(square, dest)));
    }

}
