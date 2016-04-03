package com.williamdye.dyce.game

/**
 * Enumeration of the ways a chess game can end.
 */
enum GameEndingType
{
    /** A game which ended by checkmate */
    CHECKMATE,
    /** A game which ended by stalemate */
    STALEMATE,
    /** A game which ended in a draw */
    DRAW,
    /** A game which ended in resignation by one side */
    RESIGNATION
}
