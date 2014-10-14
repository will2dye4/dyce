package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.Square;

/**
 * @author William Dye
 */
public interface Piece extends Comparable<Piece>
{

    public PieceType getPieceType();

    public PieceColor getColor();

    public Square getSquare();

    public Square getLastSquare();

    public boolean isCaptured();

    public boolean isPinned();

    /* TODO
    public boolean isRelativelyPinned(Piece piece);
     */

    public boolean isLegalSquare(Square dest);

    public boolean isLegalSquare(Square dest, boolean ignorePins);

    public boolean isAttacking(Square dest);

    public void capture();

    public Piece move(Square dest);

}
