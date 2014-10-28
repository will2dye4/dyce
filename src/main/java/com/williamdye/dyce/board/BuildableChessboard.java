package com.williamdye.dyce.board;

import com.williamdye.dyce.pieces.*;

/**
 * A chessboard that is initially empty (has no pieces) and allows clients to place pieces on arbitrary squares.
 * Useful for creating a certain position or situation for testing and also for interactively exploring how different
 * pieces move on the open board.
 *
 * @author William Dye
 */
public interface BuildableChessboard extends Chessboard
{

    /**
     * Places a new piece of the specified color and type on the specified square, assuming the square is unoccupied.
     * If there is already a piece on the square, an {@code IllegalArgumentException} will be thrown.
     *
     * @param color The color of the piece to place
     * @param type The type of piece to place
     * @param square The name of the square on which to place the new piece
     * @return The piece that was created
     */
    public Piece placePiece(PieceColor color, PieceType type, String square);

    /**
     * Moves the specified piece to the specified square, capturing the square's current piece (if any). The piece
     * is moved without adding an entry to the chessboard's move history.
     *
     * @param piece The piece to place
     * @param square The square on which to place the piece
     */
    public void placePiece(Piece piece, Square square);

    /**
     * Removes the piece on the specified square, if there is one.
     *
     * @param square The name of the square from which to remove a piece
     */
    public void removePiece(String square);

    /**
     * Removes the piece on the specified square, if there is one.
     *
     * @param square The square from which to remove a piece
     */
    public void removePiece(Square square);

    /**
     * Removes the specified piece from the board.
     *
     * @param piece The piece to remove
     */
    public void removePiece(Piece piece);

}
