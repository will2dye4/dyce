package com.williamdye.dyce.board;

import com.williamdye.dyce.pieces.PieceColor;

/**
 * Enumeration of the ranks (horizontal rows) on the chessboard.
 * @author William Dye
 */
public enum Rank
{
    /** The first rank, on which white's major pieces begin the game. */
    FIRST_RANK(1),
    /** The second rank. */
    SECOND_RANK(2),
    /** The third rank. */
    THIRD_RANK(3),
    /** The fourth rank. */
    FOURTH_RANK(4),
    /** The fifth rank. */
    FIFTH_RANK(5),
    /** The sixth rank. */
    SIXTH_RANK(6),
    /** The seventh rank. */
    SEVENTH_RANK(7),
    /** The eighth rank, on which black's major pieces begin the game. */
    EIGHTH_RANK(8);

    /** The rank's number. */
    protected int number;
    /** The rank's number as a string. */
    protected String string;

    /**
     * Construct a <code>Rank</code> with the specified number.
     * @param num the rank's number
     */
    Rank(Integer num)
    {
        this.number = num;
        this.string = num.toString();
    }

    /**
     * Find a <code>Rank</code> by number.
     * @param number the number of the rank to return
     * @return the rank with the specified number
     */
    public static Rank forNumber(int number)
    {
        for (Rank rank : values()) {
            if (number == rank.getNumber())
                return rank;
        }
        return null;
    }

    /**
     * Returns the starting rank for the major pieces of a certain color.
     * @param color the target color
     * @return the starting rank for the specified <code>color</code>
     */
    public static Rank getStartingRank(PieceColor color)
    {
        if (color == null)
            return null;
        return ((color == PieceColor.WHITE) ? FIRST_RANK : EIGHTH_RANK);
    }

    /**
     * Returns the starting rank for the pawns of a certain color.
     * @param color the target color
     * @return the starting rank for the pawns of the specified <code>color</code>
     */
    public static Rank getStartingPawnRank(PieceColor color)
    {
        if (color == null)
            return null;
        return ((color == PieceColor.WHITE) ? SECOND_RANK : SEVENTH_RANK);
    }

    /**
     * Accessor for a rank's number.
     * @return the rank's number
     */
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
