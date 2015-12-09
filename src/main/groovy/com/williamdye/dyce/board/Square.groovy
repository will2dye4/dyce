package com.williamdye.dyce.board

import com.williamdye.dyce.pieces.Piece

/**
 * This is dyce's representation of a square on a chessboard.
 *
 * @author William Dye
 */
interface Square
{

    /**
     * Accessor for a square's chessboard.
     *
     * @return The chessboard to which this square belongs
     */
    Chessboard getBoard()

    /**
     * Accessor for a square's rank.
     *
     * @return The rank (horizontal row) of this square
     */
    Rank getRank()

    /**
     * Accessor for a square's file.
     *
     * @return The file (vertical column) of this square
     */
    File getFile()

    /**
     * Accessor for a square's piece, if the square is currently occupied.
     *
     * @return The piece currently occupying the square wrapped in an {@code Optional}
     */
    Optional<Piece> getPiece()

    /**
     * Mutator for a square's piece.
     *
     * @param target The new piece for this square
     */
    void setPiece(Piece target)

    /**
     * Accessor for a square's name.
     *
     * @return The name of this square
     */
    String getName()

    /**
     * Checks whether a square is empty or occupied by a piece.
     *
     * @return {@code true} if the square is empty, {@code false} otherwise
     */
    boolean isEmpty()

}
