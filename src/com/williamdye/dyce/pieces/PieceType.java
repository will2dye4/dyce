package com.williamdye.dyce.pieces;

/**
 * @author William Dye
 */
public enum PieceType
{
    PAWN('p', "pawn"),
    KNIGHT('n', "knight"),
    BISHOP('b', "bishop"),
    ROOK('r', "rook"),
    QUEEN('q', "queen"),
    KING('k', "king");

    protected char symbol;
    protected String name;

    PieceType(char type, String name)
    {
        this.symbol = type;
        this.name = name;
    }

    public char getSymbol()
    {
        return symbol;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
