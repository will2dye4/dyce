package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.Rank;
import com.williamdye.dyce.board.Square;

import static com.williamdye.dyce.board.Paths.getFileDistance;
import static com.williamdye.dyce.board.Paths.getRankDistance;

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
    public final boolean isLegalSquare(Square dest)
    {
        if (!super.isLegalSquare(dest) || (getRankDistance(square, dest) > 2) || (getFileDistance(square, dest) > 1))
            return false;
        if (getFileDistance(square, dest) == 1) {
            boolean legal = (dest.equals(square.getBoard().getGameState().getEnPassantTargetSquare()) ||
                    (!dest.isEmpty() && (dest.getPiece().getColor() == PieceColor.oppositeOf(color))));
            if (color == PieceColor.WHITE)
                return (legal && ((dest.getRank().getNumber() - square.getRank().getNumber()) == 1));
            else
                return (legal && ((square.getRank().getNumber() - dest.getRank().getNumber()) == 1));
        }
        if (getRankDistance(square, dest) == 2)
            return (square.getRank() == ((color == PieceColor.WHITE) ? Rank.SECOND_RANK : Rank.SEVENTH_RANK));
        /* file distance == 0, rank distance == 1 */
        return ((dest.getRank().getNumber() - square.getRank().getNumber()) == ((color == PieceColor.WHITE) ? 1 : -1));
    }

}
