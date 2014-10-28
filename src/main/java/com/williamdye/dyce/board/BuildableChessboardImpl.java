package com.williamdye.dyce.board;

import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;

import com.williamdye.dyce.pieces.*;

/**
 * Implementation of the {@link BuildableChessboard} interface.
 *
 * @author William Dye
 */
public class BuildableChessboardImpl extends BaseChessboard implements BuildableChessboard
{

    @Override
    protected void capture(final Piece piece)
    {
        if (piece.getPieceType() == PieceType.KING) {
            PieceColor color = piece.getColor();
            getActivePieces(color).remove(piece);
            getCapturedPieces(color).add(piece);
            Map<PieceType, List<Piece>> pieceMap = (color == PieceColor.WHITE ? whitePieces : blackPieces);
            pieceMap.get(PieceType.KING).remove(piece);
        } else
            super.capture(piece);
    }

    @Override
    public Piece placePiece(final PieceColor color, final PieceType type, final String squareName)
    {
        Preconditions.checkNotNull(color, "'color' may not be null when placing a new piece");
        Preconditions.checkNotNull(type, "'type' may not be null when placing a new piece");

        final Square square = getSquareByName(squareName);
        if (!square.isEmpty())
            throw new IllegalArgumentException("Square may not be occupied when placing a new piece");

        Piece piece = pieceFactory.newPiece(color, type);
        updatePieceLists(piece);
        placePiece(piece, square);

        return piece;
    }

    @Override
    public void placePiece(Piece piece, Square square)
    {
        Preconditions.checkNotNull(piece, "'piece' may not be null when placing a piece");
        Preconditions.checkNotNull(square, "'square' may not be null when placing a piece");

        piece.move(square);
        square.setPiece(piece);
    }

    @Override
    public void removePiece(String square)
    {
        removePiece(getSquareByName(square));
    }

    @Override
    public void removePiece(Square square)
    {
        Preconditions.checkNotNull(square, "'square' may not be null when removing a piece");

        if (square.getPiece().isPresent())
            removePiece(square.getPiece().get());
    }

    @Override
    public void removePiece(Piece piece)
    {
        Preconditions.checkNotNull(piece, "'piece' may not be null when removing pieces");

        Square square = piece.getSquare();
        piece.capture();
        capture(piece);
        square.setPiece(null);
    }

}
