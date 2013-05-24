package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.Square;

/**
 * @author William Dye
 */
public class Queen extends AbstractPiece
{

    public Queen(PieceColor color)
    {
        super(color);
    }

    @Override
    public PieceType getPieceType()
    {
        return PieceType.QUEEN;
    }

    /*@Override
    public boolean isLegalSquare(Square square)
    {    */
        /* TODO: FIX */
     /*  return true;
    } */

}
