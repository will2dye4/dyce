package com.williamdye.dyce.util

import javax.annotation.Nonnull

import com.google.common.base.Preconditions

import com.williamdye.dyce.board.Square

import static com.williamdye.dyce.board.BaseChessboard.NUM_FILES

/**
 * Utility class for common chessboard functions.
 */
class ChessboardUtils
{

    /**
     * Given a square, return its index in the chessboard's array of squares.
     *
     * Example:
     * <pre>
     * {@code
     *     Square a4 = chessboard.getSquareByName("a4");
     *     int a4Index = ChessboardUtils.getBoardIndex(a4);
     *     Square[] board = chessboard.getBoard();
     *     assert a4.equals(board[a4Index]);
     * }
     * </pre>
     *
     * @param square The square whose index to find
     * @return The index into the chessboard's array of squares that corresponds to the specified square
     */
    public static int getBoardIndex(final @Nonnull Square square)
    {
        getBoardIndex(square.rank.number, square.file.number)
    }

    /**
     * Given a rank number and a file number, return the index in the chessboard's array of squares corresponding
     * to the square with that rank and file. The rank and file arguments should be the same values returned by
     * {@link com.williamdye.dyce.board.Rank#getNumber} and {@link com.williamdye.dyce.board.File#getNumber}, respectively.
     *
     * @param rank The number of the square's rank
     * @param file The number of the square's file
     * @return The index into the chessboard's array of squares that corresponds to the specified square (rank/file)
     */
    public static int getBoardIndex(final int rank, final int file)
    {
        Preconditions.checkArgument((rank > 0) && (rank < 9), "'rank' must be between 1 and 8, inclusive")
        Preconditions.checkArgument((file > 0) && (file < 9), "'file' must be between 1 and 8, inclusive")

        ((rank - 1) * NUM_FILES) + (file - 1)
    }

}
