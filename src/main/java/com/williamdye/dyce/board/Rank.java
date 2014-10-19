package com.williamdye.dyce.board;

import com.google.common.base.Preconditions;

import com.williamdye.dyce.pieces.PieceColor;

/**
 * Enumeration of the ranks (horizontal rows) on the chessboard.
 *
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
     * Construct a {@code Rank} with the specified number.
     *
     * @param num The rank's number
     */
    Rank(Integer num)
    {
        this.number = num;
        this.string = num.toString();
    }

    /**
     * Find a {@code Rank} by number.
     *
     * @param number The number of the rank to return
     * @return The rank with the specified number
     */
    public static Rank forNumber(final int number)
    {
        Rank result = null;
        for (Rank rank : values()) {
            if (number == rank.getNumber()) {
                result = rank;
                break;
            }
        }
        return result;
    }

    /**
     * Returns the starting rank for the major pieces of a certain color.
     *
     * @param color The target color
     * @return The starting rank for the specified color
     */
    public static Rank getStartingRank(final PieceColor color)
    {
        Preconditions.checkNotNull(color, "'color' may not be null when getting the starting rank");

        return ((color == PieceColor.WHITE) ? FIRST_RANK : EIGHTH_RANK);
    }

    /**
     * Returns the starting rank for the pawns of a certain color.
     *
     * @param color The target color
     * @return The starting rank for the pawns of the specified color
     */
    public static Rank getStartingPawnRank(final PieceColor color)
    {
        Preconditions.checkNotNull(color, "'color' may not be null when getting the starting pawn rank");

        return ((color == PieceColor.WHITE) ? SECOND_RANK : SEVENTH_RANK);
    }

    /**
     * Accessor for a rank's number.
     *
     * @return The rank's number
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
