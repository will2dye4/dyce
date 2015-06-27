package com.williamdye.dyce.game;

import com.williamdye.dyce.board.Chessboard;
import com.williamdye.dyce.board.DefaultChessboard;
import com.williamdye.dyce.notation.PGN;

/**
 * Implementation of the <code>Game</code> interface.
 */
public class GameImpl implements Game
{

    // TODO - chessboard already has history and PGN internally
    private Chessboard chessboard;
    private MoveHistory moveHistory;
    private PGN pgn;

    public GameImpl()
    {
        this.chessboard = new DefaultChessboard();
        this.moveHistory = new MoveHistoryImpl(chessboard);
        this.pgn = new PGN(chessboard);
    }

    @Override
    public Chessboard getChessboard()
    {
        return chessboard;
    }

    @Override
    public MoveHistory getMoveHistory()
    {
        return moveHistory;
    }

    @Override
    public PGN getPGN()
    {
        return pgn;
    }

}
