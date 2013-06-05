package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.File;
import com.williamdye.dyce.board.Rank;
import com.williamdye.dyce.board.Square;
import com.williamdye.dyce.board.Paths;
import com.williamdye.dyce.game.CastlingAvailability;

import java.util.List;

import static com.williamdye.dyce.game.CastlingAvailability.*;

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
    public final boolean isLegalSquare(Square dest)
    {
        if (!super.isLegalSquare(dest))
            return false;
        List<Piece> otherPieces = square.getBoard().getActivePieces(PieceColor.oppositeOf(color));
        for (Piece piece : otherPieces) {
            if (piece.isLegalSquare(dest, true))    /* ignore pins when checking if square is legal */
                return false;
        }
        if (square.getRank() == dest.getRank()) {
            /* handle castling */
            if ((square.getRank() == Rank.getStartingRank(color)) && (Paths.getFileDistance(square, dest) == 2)) {
                CastlingAvailability castling = square.getBoard().getGameState().getCastlingAvailability();
                if (dest.getFile() == File.G_FILE) {
                    return (color == PieceColor.WHITE ?
                            castling.isStatus(WHITE_CAN_CASTLE_KINGSIDE) : castling.isStatus(BLACK_CAN_CASTLE_KINGSIDE));
                } else {
                    return (color == PieceColor.WHITE ?
                            castling.isStatus(WHITE_CAN_CASTLE_QUEENSIDE) : castling.isStatus(BLACK_CAN_CASTLE_QUEENSIDE));
                }
            }
            return (Paths.getFileDistance(square, dest) == 1);
        }
        if (square.getFile() == dest.getFile())
            return (Paths.getRankDistance(square, dest) == 1);
        return ((Paths.getRankDistance(square, dest) == 1) && (Paths.getFileDistance(square, dest) == 1));
    }

}
