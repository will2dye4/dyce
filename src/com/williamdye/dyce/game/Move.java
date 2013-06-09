package com.williamdye.dyce.game;

import com.williamdye.dyce.board.Square;
import com.williamdye.dyce.pieces.Piece;

/**
 * @author William Dye
 */
public interface Move
{
    public Piece getMovedPiece();

    public Piece getCapturedPiece();

    public Square getStartSquare();

    public Square getEndSquare();

    public String getPGNString();

    public int getMoveNumber();
}
