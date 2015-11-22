package com.williamdye.dyce.board.formatter

import com.williamdye.dyce.board.Chessboard

/**
 * A chessboard formatter converts a {@link Chessboard} into an instance of some type.
 * The formatting mechanism is implementation-dependent.
 *
 * @param <T> The type of object that the formatter produces
 * @author William Dye
 */
interface ChessboardFormatter<T>
{

    /**
     * Given a {@link Chessboard}, format it into a {@code T}.
     *
     * @param chessboard The chessboard to format
     * @return The formatted chessboard
     */
    T format(Chessboard chessboard)

}
