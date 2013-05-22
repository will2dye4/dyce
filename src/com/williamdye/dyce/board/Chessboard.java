package com.williamdye.dyce.board;

/**
 * @author William Dye
 */
public interface Chessboard
{

    public String getFEN();

    public Square[] getBoard();

    public Square getSquareByName(String name);

}
