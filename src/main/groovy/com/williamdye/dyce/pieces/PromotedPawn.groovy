package com.williamdye.dyce.pieces

import com.williamdye.dyce.board.Square

/**
 * Special {@code Piece} type representing a pawn which has just been promoted.
 */
class PromotedPawn extends AbstractPiece implements Piece
{

    private Pawn pawn
    private Piece delegate
    private Piece promotedPiece

    /**
     * Construct a {@code PromotedPawn} of the specified color and promoted piece type.
     *
     * @param color The piece's color
     * @param type The type of piece the pawn was promoted to
     */
    PromotedPawn(Pawn pawn, PieceType type)
    {
        super(pawn.color)
        this.pawn = pawn
        this.delegate = pawn
        this.promotedPiece = createPromotedPiece(type, pawn.color)
    }

    private static Piece createPromotedPiece(final PieceType type, final PieceColor color)
    {
        switch (type) {
            case PieceType.BISHOP:
                new Bishop(color)
                break
            case PieceType.KNIGHT:
                new Knight(color)
                break
            case PieceType.QUEEN:
                new Queen(color)
                break
            case PieceType.ROOK:
                new Rook(color)
                break
            default:
                throw new IllegalArgumentException("'type' may not be King or Pawn for promoted piece")
        }
    }

    @Override
    PieceType getPieceType()
    {
        delegate.pieceType
    }

    @Override
    Square getSquare()
    {
        delegate.square
    }

    @Override
    Square getLastSquare()
    {
        delegate.lastSquare
    }

    @Override
    boolean isPinned()
    {
        delegate.isPinned()
    }

    @Override
    boolean isLegalSquare(Square dest)
    {
        delegate.isLegalSquare(dest)
    }

    @Override
    boolean isLegalSquare(Square dest, boolean ignorePins)
    {
        delegate.isLegalSquare(dest, ignorePins)
    }

    @Override
    boolean isAttacking(Square dest)
    {
        delegate.isAttacking(dest)
    }

    @Override
    boolean isAttacking(Square dest, boolean ignorePins)
    {
        delegate.isAttacking(dest, ignorePins)
    }

    @Override
    List<Square> getLegalSquares()
    {
        delegate.legalSquares
    }

    /**
     * Accessor for the original pawn which was promoted.
     *
     * @return The promoted pawn
     */
    Pawn getPawn()
    {
        pawn
    }

    /**
     * Accessor for the promoted piece.
     *
     * @return The piece which the pawn was promoted to
     */
    Piece getPromotedPiece()
    {
        promotedPiece
    }

    /**
     * Promote the pawn to a new piece type.
     */
    void promote()
    {
        delegate = promotedPiece
        pawn.square.board.getActivePieces(pawn.color).remove(pawn)
        pawn.square.board.getActivePieces(pawn.color, PieceType.PAWN).remove(pawn)
        pawn.remove()
        promotedPiece.move(pawn.lastSquare)
        promotedPiece.square.board.getActivePieces(promotedPiece.color) << promotedPiece
        promotedPiece.square.board.getActivePieces(promotedPiece.color, promotedPiece.pieceType) << promotedPiece
    }

    /**
     * Demote the promoted piece back to a pawn.
     */
    void demote()
    {
        delegate = pawn
        promotedPiece.square.board.getActivePieces(promotedPiece.color).remove(promotedPiece)
        promotedPiece.square.board.getActivePieces(promotedPiece.color, promotedPiece.pieceType).remove(promotedPiece)
        promotedPiece.remove()
        pawn.move(promotedPiece.lastSquare)
        pawn.square.board.getActivePieces(pawn.color) << pawn
        pawn.square.board.getActivePieces(pawn.color, PieceType.PAWN) << pawn
    }

}
