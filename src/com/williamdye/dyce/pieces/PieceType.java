package com.williamdye.dyce.pieces;

/**
 * @author William Dye
 */
public enum PieceType
{
    PAWN(   'p',   "pawn",  1),
    KNIGHT( 'n', "knight",  3),
    BISHOP( 'b', "bishop",  3),
    ROOK(   'r',   "rook",  5),
    QUEEN(  'q',  "queen",  9),
    KING(   'k',   "king",  Integer.MAX_VALUE);

    protected char symbol;
    protected String name;
    protected int materialValue;

    PieceType(char type, String name, int value)
    {
        this.symbol = type;
        this.name = name;
        this.materialValue = value;
    }

    public char getSymbol()
    {
        return symbol;
    }

    public int getMaterialValue()
    {
        return materialValue;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
