package com.williamdye.dyce.game;

import com.williamdye.dyce.board.Square;
import com.williamdye.dyce.pieces.PieceColor;

/**
 * @author William Dye
 */
public interface GameState
{

    public PieceColor getActiveColor();

    public void toggleActiveColor();

    public void setActiveColor(PieceColor color);

    public CastlingAvailability getCastlingAvailability();

    public String getCastlingString();

    public Square getEnPassantTargetSquare();

    public void setEnPassantTargetSquare(Square target);

    public int getMoveCount();

    public void incrementMoveCount();

    public void setMoveCount(int count);

    public int getHalfMoveClock();

    public void incrementHalfMoveClock();

    public void setHalfMoveClock(int clock);

    public int getHalfMoveTotal();

    public void incrementHalfMoveTotal();

    public void setHalfMoveTotal(int total);

}
