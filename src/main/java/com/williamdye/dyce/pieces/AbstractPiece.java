package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.*;

import java.util.List;
import java.util.Optional;

import static com.williamdye.dyce.board.Paths.*;

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
    public char getBoardRepresentation() {
        char representation = getPieceType().getSymbol();
        if (PieceColor.WHITE == color)
            representation -= 32;   /* uppercase letters are 32 below lowercase in the ASCII table */
        return representation;
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
        return pinned;
    }

    @Override
    public boolean isLegalSquare(final Square dest)
    {
        return isLegalSquare(dest, false);
    }

    @Override
    public boolean isLegalSquare(final Square dest, final boolean ignorePins)
    {
        return ((!captured) && (ignorePins || !isPinned()) && (isPathClear(square, dest)) &&
                (dest.isEmpty() || (dest.getPiece().get().getColor() != color)));
    }

    @Override
    public boolean isAttacking(final Square dest)
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
    public Optional<Piece> move(final Square dest)
    {
        Piece captured = null;
        /* handle capture (move has already been checked for legality) */
        if (!dest.isEmpty()) {
            captured = dest.getPiece().get();
            captured.capture();
        }
        if (square != null) {
            lastSquare = square;
            lastSquare.setPiece(null);
            dest.setPiece(this);
        }
        square = dest;
        return Optional.ofNullable(captured);
    }

    @Override
    public String toString()
    {
        return ((color == PieceColor.WHITE) ? "white" : "black") + " " + getPieceType().toString();
    }

}
