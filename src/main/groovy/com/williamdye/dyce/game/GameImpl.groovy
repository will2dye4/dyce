package com.williamdye.dyce.game

import com.williamdye.dyce.board.Chessboard
import com.williamdye.dyce.board.DefaultChessboard
import com.williamdye.dyce.notation.PGN

/**
 * Implementation of the {@code Game} interface.
 */
class GameImpl implements Game
{

    // TODO - chessboard already has history and PGN internally
    private Chessboard chessboard
    private MoveHistory moveHistory
    private PGN pgn

    GameImpl()
    {
        this.chessboard = new DefaultChessboard()
        this.moveHistory = new MoveHistoryImpl(chessboard)
        this.pgn = new PGN(chessboard)
    }

    @Override
    Chessboard getChessboard()
    {
        chessboard
    }

    @Override
    MoveHistory getMoveHistory()
    {
        moveHistory
    }

    @Override
    PGN getPGN()
    {
        pgn
    }

}
