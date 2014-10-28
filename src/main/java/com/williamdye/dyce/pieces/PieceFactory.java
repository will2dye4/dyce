package com.williamdye.dyce.pieces;

/**
 * Implementation of the factory pattern for creating new pieces.
 *
 * @author William Dye
 */
public interface PieceFactory
{

    /**
     * Returns a new {@link Piece} of the specified color and type.
     *
     * @param color The color of the new piece
     * @param type The type of piece to create
     * @return A new {@code Piece} instance of the specified color and type
     */
    public Piece newPiece(PieceColor color, PieceType type);

    /**
     * Convenience method for creating a new bishop.
     *
     * @param color The color of the new bishop
     * @return A new {@link Bishop} instance of the specified color
     */
    public Bishop newBishop(PieceColor color);

    /**
     * Convenience method for creating a new king.
     *
     * @param color The color of the new king
     * @return A new {@link King} instance of the specified color
     */
    public King newKing(PieceColor color);

    /**
     * Convenience method for creating a new knight.
     *
     * @param color The color of the new knight
     * @return A new {@link Knight} instance of the specified color
     */
    public Knight newKnight(PieceColor color);

    /**
     * Convenience method for creating a new pawn.
     *
     * @param color The color of the new pawn
     * @return A new {@link Pawn} instance of the specified color
     */
    public Pawn newPawn(PieceColor color);

    /**
     * Convenience method for creating a new queen.
     *
     * @param color The color of the new queen
     * @return A new {@link Queen} instance of the specified color
     */
    public Queen newQueen(PieceColor color);

    /**
     * Convenience method for creating a new rook.
     *
     * @param color The color of the new rook
     * @return A new {@link Rook} instance of the specified color
     */
    public Rook newRook(PieceColor color);

}
