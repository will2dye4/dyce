package com.williamdye.dyce.pieces;

/**
 * @author William Dye
 */
public class Rook extends AbstractPiece
{

    public Rook(PieceColor color)
    {
        super(color);
    }

    @Override
    public PieceType getPieceType()
    {
        return PieceType.ROOK;
    }

}
