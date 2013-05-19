package com.williamdye.dyce.pieces;

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

}
