package com.williamdye.dyce.board

import com.williamdye.dyce.pieces.PieceColor
import com.williamdye.dyce.pieces.PieceType

/**
 * Implementation of the builder pattern for {@link BuildableChessboard}s.
 *
 * @author William Dye
 */
class ChessboardBuilder
{

    /** BuildableChessboard instance that will be built up. */
    private BuildableChessboard chessboard

    /**
     * Construct a {@code ChessboardBuilder}.
     */
    public ChessboardBuilder()
    {
        this.chessboard = new BuildableChessboardImpl()
    }

    /**
     * Place a new piece of the specified color and type on the specified square.
     *
     * @param color The color of the piece to place
     * @param type The type of piece to place
     * @param square The name of the square on which to place the new piece
     * @return The builder itself to allow method chaining
     */
    public ChessboardBuilder place(final PieceColor color, final PieceType type, final String square)
    {
        chessboard.placePiece(color, type, square)
        this
    }

    /**
     * Return the chessboard that has been built up so far.
     *
     * @return The built chessboard
     */
    public BuildableChessboard build()
    {
        chessboard
    }

}
