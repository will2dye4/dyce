package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.Rank;
import com.williamdye.dyce.board.Square;
import com.williamdye.dyce.board.SquareImpl;

/**
 * @author William Dye
 */
public class Pawn extends AbstractPiece
{

    public Pawn(PieceColor color)
    {
        super(color);
    }

    @Override
    public PieceType getPieceType()
    {
        return PieceType.PAWN;
    }

    @Override
    public boolean isLegalSquare(Square dest)
    {
        if (SquareImpl.getRankDistance(square, dest) > 2)
            return false;
        if (square.getFile() != dest.getFile()) {
            boolean legal = (SquareImpl.getFileDistance(square, dest) == 1);
            if (color == PieceColor.WHITE)
                return (legal && ((dest.getRank().getNumber() - square.getRank().getNumber()) == 1));
            else
                return (legal && ((square.getRank().getNumber() - dest.getRank().getNumber()) == 1));
        }
        if (SquareImpl.getRankDistance(square, dest) == 2)
            return (square.getRank() == ((color == PieceColor.WHITE) ? Rank.SECOND_RANK : Rank.SEVENTH_RANK));
        /* file distance == 0, rank distance == 1 */
        return ((dest.getRank().getNumber() - square.getRank().getNumber()) == ((color == PieceColor.WHITE) ? 1 : -1));
    }

}
