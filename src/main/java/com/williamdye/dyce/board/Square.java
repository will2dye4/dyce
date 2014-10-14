package com.williamdye.dyce.board;

import com.williamdye.dyce.pieces.Piece;

/**
 * This is dyce's representation of a square on a chessboard.
 * @author William Dye
 */
public interface Square
{

    /**
     * Accessor for a square's chessboard.
     * @return the chessboard to which this square belongs
     */
    public Chessboard getBoard();

    /**
     * Accessor for a square's rank.
     * @return the rank (horizontal row) of this square
     */
    public Rank getRank();

    /**
     * Accessor for a square's file.
     * @return the file (vertical column) of this square
     */
    public File getFile();

    /**
     * Accessor for a square's piece, if the square is currently occupied.
     * @return the piece currently occupying the square, or <code>null</code> if the square is empty
     */
    public Piece getPiece();

    /**
     * Mutator for a square's piece.
     * @param target the new piece for this square
     */
    public void setPiece(Piece target);

    /**
     * Accessor for a square's name.
     * @return the name of this square
     */
    public String getName();

    /**
     * Checks whether a square is empty or occupied by a piece.
     * @return <code>true</code> if the square is empty, <code>false</code> otherwise
     */
    public boolean isEmpty();

}
