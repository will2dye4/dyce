package com.williamdye.dyce.pieces;

/**
 * Enumeration of the possible colors for chess pieces.
 *
 * @author William Dye
 */
public enum PieceColor
{
    WHITE("w"),
    BLACK("b");

    /** The color's string (w/b). */
    private String string;

    /**
     * Construct a {@code PieceColor} with the specified string.
     *
     * @param string The color's string
     */
    PieceColor(String string)
    {
        this.string = string;
    }

    /**
     * Given a {@code PieceColor}, return its opposite.
     *
     * @param color The color whose opposite to find
     * @return The opposite of the specified color
     */
    public static PieceColor oppositeOf(final PieceColor color)
    {
        return ((color == WHITE) ? BLACK : WHITE);
    }

    /**
     * Accessor for a piece color's name.
     *
     * @return The color's name
     */
    public String getName()
    {
        return (this == WHITE ? "white" : "black");
    }

    @Override
    public String toString()
    {
        return string;
    }

}
