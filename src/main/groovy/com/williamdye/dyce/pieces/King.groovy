package com.williamdye.dyce.pieces

import com.williamdye.dyce.board.File
import com.williamdye.dyce.board.Paths
import com.williamdye.dyce.board.Rank
import com.williamdye.dyce.board.Square
import com.williamdye.dyce.game.CastlingAvailability

import static com.williamdye.dyce.game.CastlingAvailability.*

/**
 * Represents a king on a chessboard.
 *
 * @author William Dye
 */
class King extends AbstractPiece
{

    /**
     * Construct a {@code King} of the specified color.
     *
     * @param color The king's color
     */
    King(final PieceColor color)
    {
        super(color)
    }

    @Override
    PieceType getPieceType()
    {
        PieceType.KING
    }

    @Override
    final boolean isLegalSquare(final Square dest, final boolean ignorePins)
    {
        if (!super.isLegalSquare(dest, true)) {   /* ignore pins when checking legality (king can't be pinned) */
            return false
        }
        if (square.board.getActivePieces(PieceColor.oppositeOf(color)).any {
            (it.pieceType == PieceType.KING && Paths.getFileDistance(it.square, dest) < 2 && Paths.getRankDistance(it.square, dest) < 2) ||
            (it.pieceType != PieceType.KING && it.isAttacking(dest, true))
        }) {
            return false
        }
        if (square.rank == dest.rank) {
            /* handle castling */
            if (square.rank == Rank.getStartingRank(color) && Paths.getFileDistance(square, dest) == 2) {
                final CastlingAvailability castling = square.board.game.state.castlingAvailability
                if (dest.file == File.G_FILE) {
                    return (color == PieceColor.WHITE ?
                            castling.isStatus(WHITE_CAN_CASTLE_KINGSIDE) : castling.isStatus(BLACK_CAN_CASTLE_KINGSIDE))
                } else {
                    return (color == PieceColor.WHITE ?
                            castling.isStatus(WHITE_CAN_CASTLE_QUEENSIDE) : castling.isStatus(BLACK_CAN_CASTLE_QUEENSIDE))
                }
            }
            return (Paths.getFileDistance(square, dest) == 1)
        }
        if (square.file == dest.file) {
            return (Paths.getRankDistance(square, dest) == 1)
        }
        (Paths.getRankDistance(square, dest) == 1) && (Paths.getFileDistance(square, dest) == 1)
    }

    boolean isInCheck()
    {
        square.board.getActivePieces(PieceColor.oppositeOf(color)).any { it.isAttacking(square, true) }
    }

}
