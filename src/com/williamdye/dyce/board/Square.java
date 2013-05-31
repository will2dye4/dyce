package com.williamdye.dyce.board;

import com.williamdye.dyce.pieces.Piece;

/**
 * @author William Dye
 */
public interface Square
{

    public Chessboard getBoard();

    public Rank getRank();

    public File getFile();

    public Piece getPiece();

    public void setPiece(Piece target);

    public String getName();

    public boolean isEmpty();

}
