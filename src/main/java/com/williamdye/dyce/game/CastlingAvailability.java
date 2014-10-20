package com.williamdye.dyce.game;

import com.google.common.base.Preconditions;

import com.williamdye.dyce.pieces.PieceColor;

/**
 * Represents the ability to castle of both players in a chess game.
 *
 * @author William Dye
 */
public class CastlingAvailability
{

    public static final int WHITE_CAN_CASTLE_KINGSIDE  =   0x01;
    public static final int WHITE_CAN_CASTLE_QUEENSIDE =   0x02;
    public static final int WHITE_CASTLED_KINGSIDE     =   0x04;
    public static final int WHITE_CASTLED_QUEENSIDE    =   0x08;
    public static final int BLACK_CAN_CASTLE_KINGSIDE  =   0x10;
    public static final int BLACK_CAN_CASTLE_QUEENSIDE =   0x20;
    public static final int BLACK_CASTLED_KINGSIDE     =   0x40;
    public static final int BLACK_CASTLED_QUEENSIDE    =   0x80;

    public static final int WHITE_CAN_CASTLE = (WHITE_CAN_CASTLE_KINGSIDE | WHITE_CAN_CASTLE_QUEENSIDE);
    public static final int WHITE_CASTLED = (WHITE_CASTLED_KINGSIDE | WHITE_CASTLED_QUEENSIDE);
    public static final int BLACK_CAN_CASTLE = (BLACK_CAN_CASTLE_KINGSIDE | BLACK_CAN_CASTLE_QUEENSIDE);
    public static final int BLACK_CASTLED = (BLACK_CASTLED_KINGSIDE | BLACK_CASTLED_QUEENSIDE);

    /** A bitmask representing the current castling state. */
    protected int state;

    /**
     * Construct a {@code CastlingAvailability} in which both players may castle kingside or queenside.
     */
    public CastlingAvailability()
    {
        this.state = (WHITE_CAN_CASTLE | BLACK_CAN_CASTLE);
    }

    /**
     * Check if the castling status matches the specified status bit(s).
     *
     * @param status The status to check
     * @return {@code true} if the current status has at least one bit in common with the argument, {@code false} otherwise
     */
    public boolean isStatus(final int status)
    {
        return ((state & status) > 0);
    }

    /**
     * Update the castling status with the specified status bit(s).
     *
     * @param bit The bit(s) to set
     */
    public void setBit(final int bit)
    {
        this.state |= bit;
    }

    /**
     * Update the castling status by removing the specified status bit(s).
     *
     * @param bit The bit(s) to clear
     */
    public void clearBit(final int bit)
    {
        this.state &= ~bit;
    }

    /**
     * Check if the player of a certain color may castle on a certain side of the board.
     *
     * @param color The color to check
     * @param kingside Whether to check for castling kingside or queenside
     * @return {@code true} if the player of the specified color may castle on the specified side, {@code false} otherwise
     */
    public boolean canCastle(final PieceColor color, final boolean kingside)
    {
        Preconditions.checkNotNull(color, "'color' may not be null when checking castling availability");

        if (PieceColor.WHITE == color)
            return (kingside ? isStatus(WHITE_CAN_CASTLE_KINGSIDE) : isStatus(WHITE_CAN_CASTLE_QUEENSIDE));
        return (kingside ? isStatus(BLACK_CAN_CASTLE_KINGSIDE) : isStatus(BLACK_CAN_CASTLE_QUEENSIDE));
    }

    /**
     * Check if the player of a certain color has already castled.
     *
     * @param color The color to check
     * @return {@code true} if the player of the specified color already castled, {@code false} otherwise
     */
    public boolean castled(final PieceColor color)
    {
        Preconditions.checkNotNull(color, "'color' may not be null when checking for castling");

        return (PieceColor.WHITE == color ? isStatus(WHITE_CASTLED) : isStatus(BLACK_CASTLED));
    }

    /**
     * Update the castling status for the specified color indicating that the player of that color has castled
     * on the specified side of the board.
     *
     * @param color The color of the player who castled
     * @param kingside Whether the player castled kingside
     */
    public void castle(final PieceColor color, final boolean kingside)
    {
        Preconditions.checkNotNull(color, "'color' may not be null when castling");

        if (PieceColor.WHITE == color) {
            clearBit(WHITE_CAN_CASTLE);
            setBit(kingside ? WHITE_CASTLED_KINGSIDE : WHITE_CASTLED_QUEENSIDE);
        } else {
            clearBit(BLACK_CAN_CASTLE);
            setBit(kingside ? BLACK_CASTLED_KINGSIDE : BLACK_CASTLED_QUEENSIDE);
        }
    }

    @Override
    public String toString()
    {
        String result = "-";
        if (isStatus(WHITE_CAN_CASTLE | BLACK_CAN_CASTLE)) {
            StringBuilder builder = new StringBuilder();
            if (isStatus(WHITE_CAN_CASTLE_KINGSIDE))
                builder.append("K");
            if (isStatus(WHITE_CAN_CASTLE_QUEENSIDE))
                builder.append("Q");
            if (isStatus(BLACK_CAN_CASTLE_KINGSIDE))
                builder.append("k");
            if (isStatus(BLACK_CAN_CASTLE_QUEENSIDE))
                builder.append("q");
            result = builder.toString();
        }
        return result;
    }

}
