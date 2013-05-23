package com.williamdye.dyce.board;

import com.williamdye.dyce.FEN;
import com.williamdye.dyce.pieces.Piece;

/**
 * @author William Dye
 */
public interface Chessboard
{

    public FEN getFEN();

    public String prettyPrint();

    public Square[] getBoard();

    public Square getSquareByName(String name);

    public void move(Piece piece, Square dest);

    /* convenience method for testing ... */
    public void move(String from, String to);

}
