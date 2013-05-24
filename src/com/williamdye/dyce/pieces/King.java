package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.Square;

/**
 * @author William Dye
 */
public class King extends AbstractPiece
{

    public King(PieceColor color)
    {
        super(color);
    }

    @Override
    public PieceType getPieceType()
    {
        return PieceType.KING;
    }

    /*@Override
    public boolean isLegalSquare(Square square)
    {
        if (square.getFile() == square.getFile())
            // ...
    }*/

}
