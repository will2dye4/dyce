package com.williamdye.dyce.board;

import java.util.function.*;

import com.williamdye.dyce.pieces.*;

/**
 * Chessboard implementation representing the standard chess position at the beginning of a new game.
 *
 * @author William Dye
 */
public class DefaultChessboard extends BaseChessboard
{

    /**
     * Construct a {@code DefaultChessboard} with the default (standard) position and no moves in the history.
     */
    public DefaultChessboard()
    {
        super();
        createPawns();
        createMajorPieces();
    }

    private void createPawns()
    {
        createPieces(Rank::getStartingPawnRank, (color, file) -> new Pawn(color));
    }

    private void createMajorPieces()
    {
        createPieces(Rank::getStartingRank, this::createPieceForFile);
    }

    private void createPieces(final Function<PieceColor, Rank> rankFunction,
                              final BiFunction<PieceColor, File, Piece> pieceGenerator)
    {
        for (PieceColor color : PieceColor.values()) {
            Rank rank = rankFunction.apply(color);
            for (File file : File.values()) {
                Piece piece = pieceGenerator.apply(color, file);
                getSquare(file, rank).setPiece(piece);
                updatePieceLists(piece);
            }
        }
    }

    private Piece createPieceForFile(final PieceColor color, final File file)
    {
        switch (file) {
            case A_FILE:
            case H_FILE:
                return new Rook(color);
            case B_FILE:
            case G_FILE:
                return new Knight(color);
            case C_FILE:
            case F_FILE:
                return new Bishop(color);
            case D_FILE:
                return new Queen(color);
            case E_FILE:
                return new King(color);
            default:
                throw new IllegalArgumentException("Invalid file: " + file.toString());
        }
    }

    private void updatePieceLists(final Piece piece)
    {
        if (piece.getColor() == PieceColor.WHITE) {
            activeWhitePieces.add(piece);
            whitePieces.get(piece.getPieceType()).add(piece);
        } else {
            activeBlackPieces.add(piece);
            blackPieces.get(piece.getPieceType()).add(piece);
        }
    }

}