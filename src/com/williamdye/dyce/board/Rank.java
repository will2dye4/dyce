package com.williamdye.dyce.board;

import com.williamdye.dyce.pieces.PieceColor;

/**
 * @author William Dye
 */
public enum Rank
{
    FIRST_RANK(1),
    SECOND_RANK(2),
    THIRD_RANK(3),
    FOURTH_RANK(4),
    FIFTH_RANK(5),
    SIXTH_RANK(6),
    SEVENTH_RANK(7),
    EIGHTH_RANK(8);

    protected int number;
    protected String string;

    Rank(Integer num)
    {
        this.number = num;
        this.string = num.toString();
    }

    public static Rank forNumber(int number)
    {
        for (Rank rank : values()) {
            if (number == rank.getNumber())
                return rank;
        }
        return null;
    }

    public static Rank getStartingRank(PieceColor color)
    {
        if (color == null)
            return null;
        return ((color == PieceColor.WHITE) ? FIRST_RANK : EIGHTH_RANK);
    }

    public static Rank getStartingPawnRank(PieceColor color)
    {
        if (color == null)
            return null;
        return ((color == PieceColor.WHITE) ? SECOND_RANK : SEVENTH_RANK);
    }

    public int getNumber()
    {
        return number;
    }

    @Override
    public String toString()
    {
        return string;
    }
}
