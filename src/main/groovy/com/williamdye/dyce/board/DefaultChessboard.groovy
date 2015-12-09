package com.williamdye.dyce.board

import java.util.function.BiFunction
import java.util.function.Function

import com.williamdye.dyce.pieces.Piece
import com.williamdye.dyce.pieces.PieceColor

import static com.williamdye.dyce.board.File.*

/**
 * Chessboard implementation representing the standard chess position at the beginning of a new game.
 *
 * @author William Dye
 */
class DefaultChessboard extends BaseChessboard
{

    /** Create a {@link Piece} instance (rook, knight, etc.) based on piece color and file. */
    protected final BiFunction<PieceColor, File, Piece> createPieceForFile = { color, file ->
        switch (file) {
            case [A_FILE, H_FILE]:
                return pieceFactory.newRook(color)
            case [B_FILE, G_FILE]:
                return pieceFactory.newKnight(color)
            case [C_FILE, F_FILE]:
                return pieceFactory.newBishop(color)
            case D_FILE:
                return pieceFactory.newQueen(color)
            case E_FILE:
                return pieceFactory.newKing(color)
            default:
                throw new IllegalArgumentException("Invalid file: " + file.toString())
        }
    }

    /**
     * Construct a {@code DefaultChessboard} with the default (standard) position and no moves in the history.
     */
    DefaultChessboard()
    {
        super()
        createPawns()
        createMajorPieces()
    }

    /** Create the pawns for both sides on their standard starting ranks. */
    private void createPawns()
    {
        createPieces(
                { color -> Rank.getStartingPawnRank(color) },
                { color, file -> pieceFactory.newPawn(color) }
        )
    }

    /** Create the major pieces for both sides on their standard starting ranks and files. */
    private void createMajorPieces()
    {
        createPieces(
                { color -> Rank.getStartingRank(color) },
                createPieceForFile
        )
    }

    /** Helper to create a set of pieces on a certain rank based on piece color and file. */
    private void createPieces(final Function<PieceColor, Rank> rankFunction,
                              final BiFunction<PieceColor, File, Piece> pieceGenerator)
    {
        PieceColor.values().each { color ->
            final Rank rank = rankFunction.apply(color)
            File.values().each { file ->
                final Piece piece = pieceGenerator.apply(color, file)
                getSquare(file, rank).setPiece(piece)
                updatePieceLists(piece)
            }
        }
    }

}