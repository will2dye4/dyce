package com.williamdye.dyce.board

import javax.annotation.Nonnull

import com.williamdye.dyce.game.MoveType
import com.williamdye.dyce.pieces.Piece
import com.williamdye.dyce.pieces.PieceColor
import com.williamdye.dyce.pieces.PieceType

/**
 * Implementation of the {@link BuildableChessboard} interface.
 *
 * @author William Dye
 */
public class BuildableChessboardImpl extends BaseChessboard implements BuildableChessboard
{

    @Override
    public void move(final @Nonnull Piece piece, final @Nonnull Square dest, final MoveType moveType)
    {
        piece.move(dest)
    }

    @Override
    protected void capture(final Piece piece)
    {
        if (piece.pieceType == PieceType.KING) {
            final PieceColor color = piece.color
            getActivePieces(color).remove(piece)
            getCapturedPieces(color) << piece
            final Map<PieceType, List<Piece>> pieceMap = (color == PieceColor.WHITE) ? whitePieces : blackPieces
            pieceMap[PieceType.KING].remove(piece)
        } else {
            super.capture(piece)
        }
    }

    @Override
    public Piece placePiece(final @Nonnull PieceColor color, final @Nonnull PieceType type, final String squareName)
    {
        final Square square = getSquareByName(squareName)
        if (!square.empty) {
            throw new IllegalArgumentException("Square may not be occupied when placing a new piece")
        }

        final Piece piece = pieceFactory.newPiece(color, type)
        updatePieceLists(piece)
        placePiece(piece, square)
        piece
    }

    @Override
    public void placePiece(final @Nonnull Piece piece, final @Nonnull Square square)
    {
        piece.move(square)
        square.setPiece(piece)
    }

    @Override
    public void removePiece(String square)
    {
        removePiece(getSquareByName(square))
    }

    @Override
    public void removePiece(final @Nonnull Square square)
    {
        square.piece.ifPresent { removePiece(it) }
    }

    @Override
    public void removePiece(final @Nonnull Piece piece)
    {
        final Square square = piece.square
        piece.capture()
        capture(piece)
        square.setPiece(null)
    }

}
