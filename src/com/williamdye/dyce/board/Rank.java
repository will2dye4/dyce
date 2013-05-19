package com.williamdye.dyce.board;

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
