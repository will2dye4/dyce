package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.Square;

/**
 * @author William Dye
 */
public abstract class AbstractPiece implements Piece
{
    protected PieceColor color;
    protected Square square;
    protected Square lastSquare;
    protected boolean captured;

    protected AbstractPiece(PieceColor colour)
    {
        this.color = colour;
        this.square = null;
        this.lastSquare = null;
        this.captured = false;
    }

    @Override
    public PieceColor getColor()
    {
        return color;
    }

    @Override
    public Square getSquare()
    {
        return square;
    }

    @Override
    public Square getLastSquare()
    {
        return lastSquare;
    }

    @Override
    public boolean isCaptured()
    {
        return captured;
    }

    @Override
    public void capture()
    {
        captured = true;
        lastSquare = square;
        square = null;
    }

    @Override
    public void move(Square dest)
    {
        /* handle capture (move has already been checked for legality) */
        if (!dest.isEmpty())
            dest.getPiece().capture();
        if (square != null) {
            lastSquare = square;
            lastSquare.setPiece(null);
            dest.setPiece(this);
        }
        square = dest;
    }

    @Override
    public String toString()
    {
        return "<" + ((color == PieceColor.WHITE) ? "white" : "black") + " " + getPieceType().toString() + ">";
    }

}
