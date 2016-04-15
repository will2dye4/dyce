package com.williamdye.dyce.game

import com.williamdye.dyce.board.Chessboard
import com.williamdye.dyce.notation.FEN
import com.williamdye.dyce.notation.PGN

/**
 * This is dyce's representation of a chess game.
 *
 * @author William Dye
 */
interface Game
{

    Chessboard getChessboard()

    GameState getState()

    GameEnding getEnding()

    MoveHistory getMoveHistory()

    FEN getFEN()

    PGN getPGN()

    void finishGame(GameEndingType type)

    boolean isFinished()

}
