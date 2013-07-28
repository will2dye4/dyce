package com.williamdye.dyce.game;

import com.williamdye.dyce.board.Square;
import com.williamdye.dyce.pieces.Piece;

/**
 * @author William Dye
 */
public class MoveImpl implements Move
{
    protected final Piece movedPiece;
    protected final Piece capturedPiece;
    protected final Square startSquare;
    protected final Square endSquare;
    protected final MoveType moveType;
    protected final String pgnString;
    protected final int moveNumber;

    public MoveImpl(Piece moved, Piece captured, Square start, Square end, String pgn, int number)
    {
        this(moved, captured, start, end, MoveType.NORMAL, pgn, number);
    }

    public MoveImpl(Piece moved, Piece captured, Square start, Square end, MoveType type, String pgn, int number)
    {
        if (moved == null || start == null || end == null || type == null || number < 1 || !start.getBoard().equals(end.getBoard()))
            throw new IllegalArgumentException();
        this.movedPiece = moved;
        this.capturedPiece = captured;
        this.startSquare = start;
        this.endSquare = end;
        this.pgnString = pgn;
        this.moveNumber = number;
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
        return capturedPiece;
    }

    @Override
    public Square getStartSquare()
    {
        return startSquare;
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
        return pgnString;
    }

    @Override
    public int getMoveNumber()
    {
        return moveNumber;
    }

    @Override
    public String toString()
    {
        String captureInfo = ((capturedPiece == null) ? "" : String.format(" (captured %s)", capturedPiece.toString()));
        return String.format("(%d) %s: %s => %s%s", moveNumber, movedPiece.toString(), startSquare, endSquare, captureInfo);
    }
}
