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

    public static PieceColor oppositeOf(PieceColor color)
    {
        return ((color == WHITE) ? BLACK : WHITE);
    }

    @Override
    public String toString()
    {
        return string;
    }

}
