package com.williamdye.dyce.pieces;

/**
 * @author William Dye
 */
public class Pawn extends AbstractPiece
{

    public Pawn(PieceColor color)
    {
        super(color);
    }

    @Override
    public PieceType getPieceType()
    {
        return PieceType.PAWN;
    }

}
