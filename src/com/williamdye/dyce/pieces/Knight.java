package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.Square;
import com.williamdye.dyce.board.Paths;

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
    public final boolean isLegalSquare(Square dest, boolean ignorePins)
    {
        return (super.isLegalSquare(dest, ignorePins) &&
                (((Paths.getRankDistance(square, dest) == 2) && (Paths.getFileDistance(square, dest) == 1)) ||
                ((Paths.getRankDistance(square, dest) == 1) && (Paths.getFileDistance(square, dest) == 2))));
    }

}
