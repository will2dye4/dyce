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

    /** Create the pawns for both sides on their standard starting ranks. */
    private void createPawns()
    {
        createPieces(Rank::getStartingPawnRank, (color, file) -> new Pawn(color));
    }

    /** Create the major pieces for both sides on their standard starting ranks and files. */
    private void createMajorPieces()
    {
        createPieces(Rank::getStartingRank, this::createPieceForFile);
    }

    /** Helper to create a set of pieces on a certain rank based on piece color and file. */
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

    /** Create a {@link Piece} instance (rook, knight, etc.) based on piece color and file. */
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

    /** Update the appropriate list and map of pieces with a newly created piece. */
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