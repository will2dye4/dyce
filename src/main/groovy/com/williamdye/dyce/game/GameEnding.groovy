package com.williamdye.dyce.game

import com.williamdye.dyce.pieces.PieceColor

/**
 * A representation of the end of a chess game.
 */
interface GameEnding
{

    /**
     * Accessor for the type of ending (checkmate, stalemate, etc.).
     *
     * @return The ending type of the game
     */
    GameEndingType getType()

    /**
     * Accessor for the game's winning color.
     *
     * @return The winning color of the game
     */
    PieceColor getWinningColor()

}
