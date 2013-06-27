package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.*;

import static com.williamdye.dyce.board.Paths.*;

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
    public final boolean isLegalSquare(Square dest, boolean ignorePins)
    {
        int rankDistance = getRankDistance(square, dest);
        int fileDistance = getFileDistance(square, dest);
        if (!super.isLegalSquare(dest, ignorePins) || !isAdvancingSquare(dest) ||
                (rankDistance > 2) || (rankDistance < 1) || (fileDistance > 1))
            return false;
        if ((fileDistance == 1) && (rankDistance == 1)) {
            return (dest.equals(square.getBoard().getGameState().getEnPassantTargetSquare()) ||
                    (!dest.isEmpty() && (dest.getPiece().getColor() == PieceColor.oppositeOf(color))));
        }
        return ((fileDistance == 0) && ((rankDistance == 1) || (square.getRank() == Rank.getStartingPawnRank(color))));
    }

    @Override
    public final boolean isAttacking(Square dest)
    {
        /* do we need to return true only if dest is empty or occupied by an opposite color piece,
         * or should we also return true if dest is occupied by a same color piece? should this method be
         * renamed "isCovering" or similar, or should we introduce a new method "isDefending" to distinguish
         * the two situations? decisions, decisions ...
         */
        return (!captured && !isPinned() && isAdvancingSquare(dest) && (getFileDistance(square, dest) == 1));
    }

    private boolean isAdvancingSquare(Square dest)
    {
        if (color == PieceColor.WHITE)
            return (dest.getRank().getNumber() > square.getRank().getNumber());
        else
            return (dest.getRank().getNumber() < square.getRank().getNumber());
    }

}
