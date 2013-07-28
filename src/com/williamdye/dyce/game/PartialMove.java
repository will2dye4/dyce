package com.williamdye.dyce.game;

import com.williamdye.dyce.board.Square;
import com.williamdye.dyce.pieces.Piece;

public class PartialMove implements Move
{
    protected final Piece movedPiece;
    protected final Square endSquare;
    protected final MoveType moveType;

    public PartialMove(Piece moved, Square end)
    {
        this(moved, end, MoveType.NORMAL);
    }

    public PartialMove(Piece moved, Square end, MoveType type)
    {
        if (moved == null || end == null || type == null)
            throw new IllegalArgumentException();
        this.movedPiece = moved;
        this.endSquare = end;
        this.moveType = type;
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
    public MoveType getMoveType()
    {
        return moveType;
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
