package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.Square;

/**
 * @author William Dye
 */
public interface Piece
{

    public PieceType getPieceType();

    public PieceColor getColor();

    public Square getSquare();

    public Square getLastSquare();

    public boolean isCaptured();

/*    public boolean isLegalSquare(Square square); */

    public void capture();

    public void move(Square dest);

}
