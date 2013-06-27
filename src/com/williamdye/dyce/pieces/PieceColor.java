package com.williamdye.dyce.pieces;

/**
 * @author William Dye
 */
public enum PieceColor
{
    WHITE("w"),
    BLACK("b");

    private String string;

    PieceColor(String name)
    {
        this.string = name;
    }

    public static PieceColor oppositeOf(final PieceColor color)
    {
        return ((color == WHITE) ? BLACK : WHITE);
    }

    public String getName()
    {
        return ("w".equals(string) ? "white" : "black");
    }

    @Override
    public String toString()
    {
        return string;
    }

}
