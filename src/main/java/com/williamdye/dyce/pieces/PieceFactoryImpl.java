package com.williamdye.dyce.pieces;

import com.google.common.base.Preconditions;

/**
 * Implementation of the {@link PieceFactory} interface.
 *
 * @author William Dye
 */
public class PieceFactoryImpl implements PieceFactory
{

    @Override
    public Piece newPiece(PieceColor color, PieceType type)
    {
        Preconditions.checkNotNull(color, "'color' may not be null when creating a new piece");
        Preconditions.checkNotNull(type, "'type' may not be null when creating a new piece");

        switch (type) {
            case BISHOP:
                return newBishop(color);
            case KING:
                return newKing(color);
            case KNIGHT:
                return newKnight(color);
            case PAWN:
                return newPawn(color);
            case QUEEN:
                return newQueen(color);
            case ROOK:
                return newRook(color);
            default:
                throw new IllegalArgumentException("Invalid piece type supplied to newPiece");
        }
    }

    @Override
    public Bishop newBishop(PieceColor color)
    {
        return new Bishop(color);
    }

    @Override
    public King newKing(PieceColor color)
    {
        return new King(color);
    }

    @Override
    public Knight newKnight(PieceColor color)
    {
        return new Knight(color);
    }

    @Override
    public Pawn newPawn(PieceColor color)
    {
        return new Pawn(color);
    }

    @Override
    public Queen newQueen(PieceColor color)
    {
        return new Queen(color);
    }

    @Override
    public Rook newRook(PieceColor color)
    {
        return new Rook(color);
    }

}
