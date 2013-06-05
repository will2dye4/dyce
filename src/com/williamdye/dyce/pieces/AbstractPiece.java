package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.*;

import java.util.List;

import static com.williamdye.dyce.board.Paths.isPathClear;
import static com.williamdye.dyce.board.Paths.isSameDiagonal;

/**
 * @author William Dye
 */
public abstract class AbstractPiece implements Piece
{
    protected final PieceColor color;
    protected Square square;
    protected Square lastSquare;
    protected boolean captured;

    protected AbstractPiece(PieceColor colour)
    {
        this.color = colour;
        this.square = null;
        this.lastSquare = null;
        this.captured = false;
    }

    @Override
    public int compareTo(Piece other)
    {
        return (this.getPieceType().getMaterialValue() - other.getPieceType().getMaterialValue());
    }

    @Override
    public PieceColor getColor()
    {
        return color;
    }

    @Override
    public Square getSquare()
    {
        return square;
    }

    @Override
    public Square getLastSquare()
    {
        return lastSquare;
    }

    @Override
    public boolean isCaptured()
    {
        return captured;
    }

    @Override
    public boolean isPinned()
    {
        if (PieceType.KING == getPieceType())
            return false;   /* king can never be pinned */
        boolean pinned = false;
        Chessboard board = square.getBoard();
        King king = board.getKing(color);
        List<Piece> otherPieces = board.getActivePieces(PieceColor.oppositeOf(color));
        Square kingSquare = king.getSquare();
        Rank kingRank = kingSquare.getRank();
        File kingFile = kingSquare.getFile();
        if ((square.getRank() == kingRank) && (isPathClear(square, kingSquare))) {
            for (Piece piece : otherPieces) {
                PieceType pieceType = piece.getPieceType();
                if (((PieceType.QUEEN == pieceType) || (PieceType.ROOK == pieceType)) &&
                        (piece.getSquare().getRank() == kingRank) && (piece.isAttacking(square))) {
                    pinned = true;
                    break;
                }
            }
        } else if ((square.getFile() == kingFile) && (isPathClear(square, kingSquare))) {
            for (Piece piece : otherPieces) {
                PieceType pieceType = piece.getPieceType();
                if (((PieceType.QUEEN == pieceType) || (PieceType.ROOK == pieceType)) &&
                        (piece.getSquare().getFile() == kingFile) && (piece.isAttacking(square))) {
                    pinned = true;
                    break;
                }
            }
        } else if (isSameDiagonal(square, kingSquare) && isPathClear(square, kingSquare)) {
            for (Piece piece : otherPieces) {
                PieceType pieceType = piece.getPieceType();
                if (((PieceType.QUEEN == pieceType) || (PieceType.BISHOP == pieceType)) &&
                        (isSameDiagonal(piece.getSquare(), kingSquare)) && (piece.isAttacking(square))) {
                    pinned = true;
                    break;
                }
            }
        }
        /*
         * +---+---+---+---+---+
         * | r | p |   |   |   |
         * +---+---+---+---+---+
         * | P |   |   |   |   |  white Pawn is pinned by the Rook
         * +---+---+---+---+---+
         * | K |   | Q |   | r |  white Queen is pinned by the Rook
         * +---+---+---+---+---+
         * |   |   |   | n |   |  black Knight is pinned by the Queen
         * +---+---+---+---+---+
         * |   | B |   |   | k |
         * +---+---+---+---+---+
         */
        return pinned;
    }

    @Override
    public boolean isLegalSquare(Square dest)
    {
        return isLegalSquare(dest, false);
    }

    @Override
    public boolean isLegalSquare(Square dest, boolean ignorePins)
    {
        return ((!captured) && (ignorePins || !isPinned()) && (isPathClear(square, dest)) &&
                (dest.isEmpty() || (dest.getPiece().getColor() != color)));
    }

    @Override
    public boolean isAttacking(Square dest)
    {
        return isLegalSquare(dest);
    }

    @Override
    public void capture()
    {
        captured = true;
        lastSquare = square;
        square = null;
    }

    @Override
    public Piece move(Square dest)
    {
        Piece captured = null;
        /* handle capture (move has already been checked for legality) */
        if (!dest.isEmpty()) {
            captured = dest.getPiece();
            dest.getPiece().capture();
        }
        if (square != null) {
            lastSquare = square;
            lastSquare.setPiece(null);
            dest.setPiece(this);
        }
        square = dest;
        return captured;
    }

    @Override
    public String toString()
    {
        return "<" + ((color == PieceColor.WHITE) ? "white" : "black") + " " + getPieceType().toString() + ">";
    }

}
