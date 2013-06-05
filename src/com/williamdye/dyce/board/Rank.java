package com.williamdye.dyce.board;

import com.williamdye.dyce.pieces.PieceColor;

/**
 * @author William Dye
 */
public enum Rank
{
    FIRST_RANK(1, "RNBQKBNR"),
    SECOND_RANK(2, "PPPPPPPP"),
    THIRD_RANK(3, "8"),
    FOURTH_RANK(4, "8"),
    FIFTH_RANK(5, "8"),
    SIXTH_RANK(6, "8"),
    SEVENTH_RANK(7, "pppppppp"),
    EIGHTH_RANK(8, "rnbqkbnr");

    protected int number;
    protected String string;
    protected String initialFEN;

    Rank(Integer num, String fen)
    {
        this.number = num;
        this.string = num.toString();
        this.initialFEN = fen;
    }

    public static Rank getStartingRank(PieceColor color)
    {
        if (color == null)
            return null;
        return ((color == PieceColor.WHITE) ? FIRST_RANK : EIGHTH_RANK);
    }

    public int getNumber()
    {
        return number;
    }

    public String getInitialFEN()
    {
        return initialFEN;
    }

    @Override
    public String toString()
    {
        return string;
    }
}
