package com.williamdye.dyce.pieces;

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

}
