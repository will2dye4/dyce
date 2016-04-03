package com.williamdye.dyce.game

import com.williamdye.dyce.pieces.PieceColor

/**
 * Implementation of the {@code GameEnding} interface.
 */
class GameEndingImpl implements GameEnding
{

    private GameEndingType type
    private PieceColor winningColor

    /**
     * Construct a {@code GameEndingImpl} with the specified type and winning color.
     *
     * @param type The type of ending (checkmate, stalemate, etc.)
     * @param winningColor The winning color (applicable only for checkmate and resignation)
     */
    GameEndingImpl(GameEndingType type, PieceColor winningColor)
    {
        this.type = type
        this.winningColor = winningColor
    }

    /**
     * Convenience method to instantiate a {@code GameStateImpl} representing checkmate for the specified color.
     *
     * @param winningColor The winning color
     * @return A new {@code GameStateImpl} representing checkmate for the specified color
     */
    static GameEndingImpl checkmateFor(PieceColor winningColor)
    {
        new GameEndingImpl(GameEndingType.CHECKMATE, winningColor)
    }

    /**
     * Convenience method to instantiate a {@code GameStateImpl} representing a stalemate.
     *
     * @return A new {@code GameStateImpl} representing stalemate
     */
    static GameEndingImpl stalemate()
    {
        new GameEndingImpl(GameEndingType.STALEMATE, null)
    }

    /**
     * Convenience method to instantiate a {@code GameStateImpl} representing a draw.
     *
     * @return A new {@code GameStateImpl} representing a draw
     */
    static GameEndingImpl draw()
    {
        new GameEndingImpl(GameEndingType.DRAW, null)
    }

    /**
     * Convenience method to instantiate a {@code GameStateImpl} representing resignation by one side.
     *
     * @param losingColor The color (side) which resigned the game
     * @return A new {@code GameStateImpl} representing resignation by {@code losingColor}
     */
    static GameEndingImpl resignationBy(PieceColor losingColor)
    {
        new GameEndingImpl(GameEndingType.RESIGNATION, ~losingColor)
    }

    @Override
    GameEndingType getType() {
        type
    }

    @Override
    PieceColor getWinningColor() {
        winningColor
    }

}
