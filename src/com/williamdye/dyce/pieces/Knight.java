package com.williamdye.dyce.pieces;

/**
 * @author William Dye
 */
public class Knight extends AbstractPiece
{

    public Knight(PieceColor color)
    {
        super(color);
    }

    @Override
    public PieceType getPieceType()
    {
        return PieceType.KNIGHT;
    }

}
