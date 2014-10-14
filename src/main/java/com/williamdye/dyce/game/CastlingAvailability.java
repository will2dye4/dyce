package com.williamdye.dyce.game;

import com.williamdye.dyce.pieces.PieceColor;

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

    public boolean canCastle(final PieceColor color, final boolean kingside)
    {
        if (color == null)
            return false;
        if (PieceColor.WHITE == color)
            return (kingside ? isStatus(WHITE_CAN_CASTLE_KINGSIDE) : isStatus(WHITE_CAN_CASTLE_QUEENSIDE));
        return (kingside ? isStatus(BLACK_CAN_CASTLE_KINGSIDE) : isStatus(BLACK_CAN_CASTLE_QUEENSIDE));
    }

    public boolean castled(final PieceColor color)
    {
        return (color != null) && (PieceColor.WHITE == color ? isStatus(WHITE_CASTLED) : isStatus(BLACK_CASTLED));
    }

    public void castle(final PieceColor color, final boolean kingside)
    {
        if (color == null)
            return;
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
