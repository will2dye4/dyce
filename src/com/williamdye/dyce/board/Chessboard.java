package com.williamdye.dyce.board;

import com.williamdye.dyce.FEN;
import com.williamdye.dyce.pieces.Piece;
import com.williamdye.dyce.pieces.PieceColor;

/**
 * @author William Dye
 */
public interface Chessboard
{

    public FEN getFEN();

    public String prettyPrint();

    public Square[] getBoard();

    public Square getSquareByName(String name);

    public Square getEnPassantTargetSquare();

    public PieceColor getActiveColor();

    public int getMoveCount();

    public int getHalfMoveClock();

    public int getHalfMoveTotal();

    public String getCastlingAvailability();

    public void move(Piece piece, Square dest);

    /* convenience method for testing ... */
    public void move(String from, String to);

}
