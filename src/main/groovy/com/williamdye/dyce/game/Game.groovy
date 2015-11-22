package com.williamdye.dyce.game

import com.williamdye.dyce.board.Chessboard
import com.williamdye.dyce.notation.PGN

/**
 * This is dyce's representation of a chess game.
 *
 * @author William Dye
 */
interface Game
{

    Chessboard getChessboard()

    MoveHistory getMoveHistory()

    PGN getPGN()

}
