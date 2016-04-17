package com.williamdye.dyce.pieces

import com.williamdye.dyce.board.Rank
import com.williamdye.dyce.board.Square

import static com.williamdye.dyce.board.Paths.getFileDistance
import static com.williamdye.dyce.board.Paths.getRankDistance

/**
 * Represents a pawn on a chessboard.
 *
 * @author William Dye
 */
class Pawn extends AbstractPiece
{

    private boolean promoted

    /**
     * Construct a {@code Pawn} of the specified color.
     *
     * @param color The pawn's color
     */
    Pawn(PieceColor color)
    {
        super(color)
        this.promoted = false
    }

    @Override
    PieceType getPieceType()
    {
        PieceType.PAWN
    }

    @Override
    final boolean isLegalSquare(final Square dest, final boolean ignorePins)
    {
        int rankDistance = getRankDistance(square, dest)
        int fileDistance = getFileDistance(square, dest)
        if (promoted || !super.isLegalSquare(dest, ignorePins) || !isAdvancingSquare(dest) || rankDistance > 2 || rankDistance < 1 || fileDistance > 1) {
            return false
        }
        if (fileDistance == 1 && rankDistance == 1) {
            return (dest == square.board.game.state.enPassantTargetSquare || (!dest.isEmpty() && (dest.piece.get().color == ~color)))
        }
        fileDistance == 0 && dest.isEmpty() && (rankDistance == 1 || square.rank == Rank.getStartingPawnRank(color))
    }

    @Override
    final boolean isAttacking(final Square dest)
    {
        isAttacking(dest, false)
    }

    @Override
    final boolean isAttacking(final Square dest, final boolean ignorePins)
    {
        /* do we need to return true only if dest is empty or occupied by an opposite color piece,
         * or should we also return true if dest is occupied by a same color piece? should this method be
         * renamed "isCovering" or similar, or should we introduce a new method "isDefending" to distinguish
         * the two situations? decisions, decisions ...
         */
        (!captured) && (ignorePins || !pinned) && isAdvancingSquare(dest) && (getFileDistance(square, dest) == 1) && (getRankDistance(square, dest) == 1)
    }

    void promote(Piece newPiece)
    {
        square.board.getActivePieces(color) << newPiece
        square.board.getActivePieces(color, newPiece.pieceType) << newPiece
        square.board.getActivePieces(color).remove(this)
        square.board.getActivePieces(color, pieceType).remove(this)

        square.setPiece(newPiece)
        square = null
        promoted = true
        captured = false
    }

    /** Helper to check if the specified square is advancing for this pawn. */
    private boolean isAdvancingSquare(final Square dest)
    {
        (color == PieceColor.WHITE) ? dest.rank > square.rank : dest.rank < square.rank
    }

}
