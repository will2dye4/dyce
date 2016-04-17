package com.williamdye.dyce.pieces

import com.williamdye.dyce.board.Square

/**
 * This is dyce's representation of a generic piece on a chessboard.
 *
 * @author William Dye
 */
interface Piece extends Comparable<Piece>
{

    /**
     * Accessor for the piece's type (queen, knight, pawn, etc.).
     *
     * @return The type of the piece
     */
    PieceType getPieceType()

    /**
     * Accessor for the piece's color.
     *
     * @return The color of the piece
     */
    PieceColor getColor()

    /**
     * Returns the piece's representation on the board (e.g., 'Q' for a white queen).
     *
     * @return The piece's representation
     */
    char getBoardRepresentation()

    /**
     * Accessor for the piece's current square.
     *
     * @return The piece's current square, or {@code null} if the piece is captured
     */
    Square getSquare()

    /**
     * Accessor for the piece's previous square.
     *
     * @return The piece's previous square
     */
    Square getLastSquare()

    /**
     * Check whether the piece has been captured.
     *
     * @return {@code true} if the piece is captured, {@code false} otherwise
     */
    boolean isCaptured()

    /**
     * Check whether the piece is pinned. A piece is pinned if it may not move because doing so would result in check.
     *
     * @return {@code true} if the piece is pinned, {@code false} otherwise
     */
    boolean isPinned()

    /* TODO
    public boolean isRelativelyPinned(Piece piece);
     */

    /**
     * Check whether the piece is legally allowed to move to the specified square.
     *
     * @param dest The square to check
     * @return {@code true} if the piece may move to the specified square, {@code false} otherwise
     */
    boolean isLegalSquare(Square dest)

    /**
     * Check whether the piece is legally allowed to move to the specified square, optionally ignoring if the piece is pinned.
     *
     * @param dest The square to check
     * @param ignorePins Whether to ignore the fact that the piece may be "pinned" to the king
     * @return {@code true} if the piece may move to the specified square, {@code false} otherwise
     */
    boolean isLegalSquare(Square dest, boolean ignorePins)

    /**
     * Check if the piece is attacking the specified square.
     *
     * @param dest The square to check
     * @return {@code true} if the piece is attacking the specified square, {@code false} otherwise
     */
    boolean isAttacking(Square dest)

    /**
     * Check if the piece is attacking the specified square, optionally ignoring if the piece is pinned.
     *
     * @param dest The square to check
     * @param ignorePins Whether to ignore the fact that the piece may be "pinned" to the king
     * @return {@code true} if the piece is attacking the specified square, {@code false} otherwise
     */
    boolean isAttacking(Square dest, boolean ignorePins)

    /**
     * Get a list of squares that the piece may legally move to.
     *
     * @return List of legal squares for the piece
     */
    List<Square> getLegalSquares()

    /**
     * Capture the piece.
     */
    void capture()

    /**
     * Remove the piece from the board without capturing it.
     */
    void remove()

    /**
     * Uncapture the piece.
     */
    void uncapture()

    /**
     * Move the piece to the specified square, returning the piece that was captured as a result of the move (if any).
     *
     * @param dest The square to which to move the piece
     * @return The piece that was captured, if any
     */
    Optional<Piece> move(Square dest)

}
