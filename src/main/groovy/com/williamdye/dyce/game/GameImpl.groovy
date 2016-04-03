package com.williamdye.dyce.game

import com.williamdye.dyce.board.Chessboard
import com.williamdye.dyce.notation.FEN
import com.williamdye.dyce.notation.PGN

/**
 * Implementation of the {@code Game} interface.
 */
class GameImpl implements Game
{

    private Chessboard chessboard
    private GameState state = new GameStateImpl()
    private GameEnding ending = null
    private MoveHistory moveHistory
    private FEN fen
    private PGN pgn

    GameImpl(Chessboard chessboard)
    {
        this.chessboard = chessboard
        this.moveHistory = new MoveHistoryImpl(chessboard)
        this.fen = new FEN(chessboard)
        this.pgn = new PGN(chessboard)
    }

    @Override
    Chessboard getChessboard()
    {
        chessboard
    }

    @Override
    GameState getState()
    {
        state
    }

    @Override
    MoveHistory getMoveHistory()
    {
        moveHistory
    }

    @Override
    FEN getFEN()
    {
        fen
    }

    @Override
    PGN getPGN()
    {
        pgn
    }

    @Override
    void finishGame(GameEndingType type) {
        switch (type) {
            case GameEndingType.CHECKMATE:
                ending = GameEndingImpl.checkmateFor(state.activeColor)
                break
            case GameEndingType.STALEMATE:
                ending = GameEndingImpl.stalemate()
                break
            case GameEndingType.DRAW:
                ending = GameEndingImpl.draw()
                break
            case GameEndingType.RESIGNATION:
                ending = GameEndingImpl.resignationBy(state.activeColor)
                break
        }
    }

    @Override
    boolean isFinished() {
        ending != null
    }

}
