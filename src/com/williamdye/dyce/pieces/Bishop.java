package com.williamdye.dyce.pieces;

/**
 * @author William Dye
 */
public class Bishop extends AbstractPiece
{

    public Bishop(PieceColor color)
    {
        super(color);
    }

    @Override
    public PieceType getPieceType()
    {
        return PieceType.BISHOP;
    }

}
