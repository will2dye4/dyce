package com.williamdye.dyce.pieces

import com.williamdye.dyce.board.Square

/**
 * Special {@code Piece} type representing a pawn which has just been promoted.
 */
class PromotedPawn extends AbstractPiece implements Piece
{

    private Pawn pawn
    private PieceType promotedPieceType

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
        this.promotedPieceType = type
    }

    @Override
    PieceType getPieceType()
    {
        promotedPieceType
    }

    @Override
    Square getSquare()
    {
        pawn.square
    }

    @Override
    Square getLastSquare()
    {
        pawn.lastSquare
    }

    @Override
    boolean isPinned()
    {
        pawn.isPinned()
    }

    @Override
    boolean isLegalSquare(Square dest)
    {
        pawn.isLegalSquare(dest)
    }

    @Override
    boolean isLegalSquare(Square dest, boolean ignorePins)
    {
        pawn.isLegalSquare(dest, ignorePins)
    }

    @Override
    boolean isAttacking(Square dest)
    {
        pawn.isAttacking(dest)
    }

    @Override
    boolean isAttacking(Square dest, boolean ignorePins)
    {
        pawn.isAttacking(dest, ignorePins)
    }

    @Override
    List<Square> getLegalSquares()
    {
        pawn.legalSquares
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

}
