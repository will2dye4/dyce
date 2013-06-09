package com.williamdye.dyce.game;

import com.williamdye.dyce.board.Square;
import com.williamdye.dyce.pieces.Piece;

public class PartialMove implements Move
{
    protected final Piece movedPiece;
    protected final Square endSquare;

    public PartialMove(Piece moved, Square end)
    {
        this.movedPiece = moved;
        this.endSquare = end;
    }

    @Override
    public Piece getMovedPiece()
    {
        return movedPiece;
    }

    @Override
    public Piece getCapturedPiece()
    {
        return null;
    }

    @Override
    public Square getStartSquare()
    {
        return null;
    }

    @Override
    public Square getEndSquare()
    {
        return endSquare;
    }

    @Override
    public String getPGNString()
    {
        return null;
    }

    @Override
    public int getMoveNumber()
    {
        return 0;
    }

}
