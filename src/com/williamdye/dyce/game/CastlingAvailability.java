package com.williamdye.dyce.game;

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

    protected int state;

    public CastlingAvailability()
    {
        this.state = (WHITE_CAN_CASTLE | BLACK_CAN_CASTLE);
    }

    public boolean isStatus(final int status)
    {
        return ((state & status) > 0);
    }

    public void setBit(final int bit)
    {
        this.state |= bit;
    }

    public void clearBit(final int bit)
    {
        this.state &= ~bit;
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
