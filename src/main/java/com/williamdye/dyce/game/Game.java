package com.williamdye.dyce.game;

import com.williamdye.dyce.board.Chessboard;
import com.williamdye.dyce.notation.PGN;

/**
 * Created by William on 6/27/15.
 */
public interface Game
{

    public Chessboard getChessboard();

    public MoveHistory getMoveHistory();

    public PGN getPGN();

}
