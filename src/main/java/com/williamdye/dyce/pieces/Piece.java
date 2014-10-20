package com.williamdye.dyce.pieces;

import java.util.Optional;

import com.williamdye.dyce.board.Square;

/**
 * This is dyce's representation of a generic piece on a chessboard.
 *
 * @author William Dye
 */
public interface Piece extends Comparable<Piece>
{

    /**
     * Accessor for the piece's type (queen, knight, pawn, etc.).
     *
     * @return The type of the piece
     */
    public PieceType getPieceType();

    /**
     * Accessor for the piece's color.
     *
     * @return The color of the piece
     */
    public PieceColor getColor();

    /**
     * Returns the piece's representation on the board (e.g., 'Q' for a white queen).
     *
     * @return The piece's representation
     */
    public char getBoardRepresentation();

    /**
     * Accessor for the piece's current square.
     *
     * @return The piece's current square, or {@code null} if the piece is captured
     */
    public Square getSquare();

    /**
     * Accessor for the piece's previous square.
     *
     * @return The piece's previous square
     */
    public Square getLastSquare();

    /**
     * Check whether the piece has been captured.
     *
     * @return {@code true} if the piece is captured, {@code false} otherwise
     */
    public boolean isCaptured();

    /**
     * Check whether the piece is pinned. A piece is pinned if it may not move because doing so would result in check.
     *
     * @return {@code true} if the piece is pinned, {@code false} otherwise
     */
    public boolean isPinned();

    /* TODO
    public boolean isRelativelyPinned(Piece piece);
     */

    /**
     * Check whether the piece is legally allowed to move to the specified square.
     *
     * @param dest The square to check
     * @return {@code true} if the piece may move to the specified square, {@code false} otherwise
     */
    public boolean isLegalSquare(Square dest);

    /**
     * Check whether the piece is legally allowed to move to the specified square, optionally ignoring if the piece is pinned.
     *
     * @param dest The square to check
     * @param ignorePins Whether to ignore the fact that the piece may be "pinned" to the king
     * @return {@code true} if the piece may move to the specified square, {@code false} otherwise
     */
    public boolean isLegalSquare(Square dest, boolean ignorePins);

    /**
     * Check if the piece is attacking the specified square.
     *
     * @param dest The square to check
     * @return {@code true} if the piece is attacking the specified square, {@code false} otherwise
     */
    public boolean isAttacking(Square dest);

    /**
     * Capture the piece.
     */
    public void capture();

    /**
     * Move the piece to the specified square, returning the piece that was captured as a result of the move (if any).
     *
     * @param dest The square to which to move the piece
     * @return The piece that was captured, if any
     */
    public Optional<Piece> move(Square dest);

}
