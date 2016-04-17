package com.williamdye.dyce.pieces

import com.williamdye.dyce.board.Square

import static com.williamdye.dyce.board.Paths.isPathClear
import static com.williamdye.dyce.board.Paths.isSameDiagonal

/**
 * Abstract implementation of the {@link Piece} interface.
 *
 * @author William Dye
 */
abstract class AbstractPiece implements Piece
{

    /** The piece's color. */
    protected final PieceColor color

    /** The piece's current square (if not captured). */
    protected Square square

    /** The piece's previous square. */
    protected Square lastSquare

    /** Whether the piece has been captured. */
    protected boolean captured

    /**
     * Construct an {@code AbstractPiece} of the specified color.
     *
     * @param color The piece's color
     */
    protected AbstractPiece(final PieceColor color)
    {
        this.color = color
        this.square = null
        this.lastSquare = null
        this.captured = false
    }

    @Override
    int compareTo(final Piece other)
    {
        this.pieceType.materialValue <=> other.pieceType.materialValue
    }

    @Override
    PieceColor getColor()
    {
        color
    }

    @Override
    char getBoardRepresentation() {
        char representation = pieceType.symbol
        if (PieceColor.WHITE == color) {
            representation -= 32   /* uppercase letters are 32 below lowercase in the ASCII table */
        }
        representation
    }

    @Override
    Square getSquare()
    {
        square
    }

    @Override
    Square getLastSquare()
    {
        lastSquare
    }

    @Override
    boolean isCaptured()
    {
        captured
    }

    @Override
    boolean isPinned()
    {
        if (PieceType.KING == pieceType) {
            return false   /* king can never be pinned */
        }
        final Square king = square.board.getKing(color).square
        final List<Piece> otherPieces = square.board.getActivePieces(~color)

        (square.rank == king.rank && isPathClear(square, king) && otherPieces.any {
            [PieceType.QUEEN, PieceType.ROOK].contains(it.pieceType) && it.square.rank == king.rank && it.isAttacking(square)
        }) || (square.file == king.file && isPathClear(square, king) && otherPieces.any {
            [PieceType.QUEEN, PieceType.ROOK].contains(it.pieceType) && it.square.file == king.file && it.isAttacking(square)
        }) || (isSameDiagonal(square, king) && isPathClear(square, king) && otherPieces.any {
            [PieceType.QUEEN, PieceType.BISHOP].contains(it.pieceType) && isSameDiagonal(it.square, king) && it.isAttacking(square)
        })
    }

    Piece getPinningPiece()
    {
        if (PieceType.KING == pieceType) {
            return null   /* king can never be pinned */
        }
        Piece pinningPiece = null
        final Square king = square.board.getKing(color).square
        final List<Piece> otherPieces = square.board.getActivePieces(~color)
        if (square.rank == king.rank && isPathClear(square, king)) {
            pinningPiece = otherPieces.find {
                [PieceType.QUEEN, PieceType.ROOK].contains(it.pieceType) && it.square.rank == king.rank && it.isAttacking(square)
            }
        } else if (square.file == king.file && isPathClear(square, king)) {
            pinningPiece = otherPieces.find {
                [PieceType.QUEEN, PieceType.ROOK].contains(it.pieceType) && it.square.file == king.file && it.isAttacking(square)
            }
        } else if (isSameDiagonal(square, king) && isPathClear(square, king)) {
            pinningPiece = otherPieces.find {
                [PieceType.QUEEN, PieceType.BISHOP].contains(it.pieceType) && isSameDiagonal(it.square, king) && it.isAttacking(square)
            }
        }
        pinningPiece
    }

    @Override
    boolean isLegalSquare(final Square dest)
    {
        isLegalSquare(dest, false)
    }

    @Override
    boolean isLegalSquare(final Square dest, final boolean ignorePins)
    {
        (!captured) && (ignorePins || !pinned || pinningPiece.square == dest) && isPathClear(square, dest) &&
                (dest.isEmpty() || dest.piece.get().color != color)
    }

    @Override
    boolean isAttacking(final Square dest)
    {
        isLegalSquare(dest)
    }

    @Override
    boolean isAttacking(final Square dest, final boolean ignorePins)
    {
        isLegalSquare(dest, ignorePins)
    }

    @Override
    List<Square> getLegalSquares()
    {
        captured ? [] : square.board.board.findAll { isLegalSquare(it) }
    }

    @Override
    void capture()
    {
        captured = true
        remove()
    }

    @Override
    void remove()
    {
        lastSquare = square
        square = null
    }

    @Override
    void uncapture()
    {
        captured = false
        square = lastSquare
        square.piece = this
        square.board.getCapturedPieces(color).remove(this)
        square.board.getActivePieces(color) << this
        square.board.getActivePieces(color, pieceType) << this
    }

    @Override
    Optional<Piece> move(final Square dest)
    {
        Piece captured = null
        /* handle capture (move has already been checked for legality) */
        if (!dest.isEmpty()) {
            captured = dest.piece.get()
            captured.capture()
        }
        if (square) {
            lastSquare = square
            lastSquare.setPiece(null)
            dest.setPiece(this)
        }
        square = dest
        Optional.ofNullable(captured)
    }

    @Override
    String toString()
    {
        (color == PieceColor.WHITE ? "white" : "black") + " " + pieceType.toString()
    }

}
