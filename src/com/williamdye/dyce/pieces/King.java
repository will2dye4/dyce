package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.Square;
import com.williamdye.dyce.board.Paths;

import java.util.List;

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
        List<Piece> otherPieces = ((color == PieceColor.WHITE) ?
                square.getBoard().getActiveBlackPieces() : square.getBoard().getActiveWhitePieces());
        for (Piece piece : otherPieces) {
            if (piece.isAttacking(dest))    /* TODO: isAttacking returns false if the piece is pinned, */
                return false;               /* but the king can't go to any square attacked by an enemy piece */
        }
        /* TODO handle castling */
        if (square.getFile() == dest.getFile())
            return (Paths.getRankDistance(square, dest) == 1);
        if (square.getRank() == dest.getRank())
            return (Paths.getFileDistance(square, dest) == 1);
        return ((Paths.getRankDistance(square, dest) == 1) && (Paths.getFileDistance(square, dest) == 1));
    }

}
