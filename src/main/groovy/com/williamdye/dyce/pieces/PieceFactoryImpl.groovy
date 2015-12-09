package com.williamdye.dyce.pieces

import javax.annotation.Nonnull

import static com.williamdye.dyce.pieces.PieceType.*

/**
 * Implementation of the {@link PieceFactory} interface.
 *
 * @author William Dye
 */
class PieceFactoryImpl implements PieceFactory
{

    @Override
    Piece newPiece(final @Nonnull PieceColor color, final @Nonnull PieceType type)
    {
        switch (type) {
            case BISHOP:
                return newBishop(color)
            case KING:
                return newKing(color)
            case KNIGHT:
                return newKnight(color)
            case PAWN:
                return newPawn(color)
            case QUEEN:
                return newQueen(color)
            case ROOK:
                return newRook(color)
            default:
                throw new IllegalArgumentException("Invalid piece type supplied to newPiece")
        }
    }

    @Override
    Bishop newBishop(final PieceColor color)
    {
        new Bishop(color)
    }

    @Override
    King newKing(final PieceColor color)
    {
        new King(color)
    }

    @Override
    Knight newKnight(final PieceColor color)
    {
        new Knight(color)
    }

    @Override
    Pawn newPawn(final PieceColor color)
    {
        new Pawn(color)
    }

    @Override
    Queen newQueen(final PieceColor color)
    {
        new Queen(color)
    }

    @Override
    Rook newRook(final PieceColor color)
    {
        new Rook(color)
    }

}
