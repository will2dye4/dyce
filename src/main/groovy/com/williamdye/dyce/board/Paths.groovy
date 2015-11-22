package com.williamdye.dyce.board

import com.google.common.base.Preconditions

import com.williamdye.dyce.pieces.PieceType
import com.williamdye.dyce.util.ChessboardUtils
import com.williamdye.dyce.util.MathUtils

import static com.williamdye.dyce.board.DefaultChessboard.NUM_FILES

/**
 * A utility class for determining various information about distances and paths between two squares.
 *
 * @author William Dye
 */
final class Paths
{

    /** Prevent instantiation. */
    private Paths() { }

    /**
     * Returns the absolute distance between two squares with respect to their ranks (horizontal rows). For example,
     * {@code Paths.getRankDistance(a3, d7)} would return {@code 4}.
     *
     * @param start The first square
     * @param end The second square
     * @return The absolute rank distance between the two squares
     */
    public static int getRankDistance(final Square start, final Square end)
    {
        Preconditions.checkArgument(isValidPair(start, end), "Invalid squares supplied to getRankDistance")

        Math.abs(start.rank.number - end.rank.number)
    }

    /**
     * Returns the absolute distance between two squares with respect to their files (vertical columns). For example,
     * {@code Paths.getFileDistance(a3, d7)} would return {@code 3}.
     *
     * @param start The first square
     * @param end The second square
     * @return The absolute file distance between the two squares
     */
    public static int getFileDistance(final Square start, final Square end)
    {
        Preconditions.checkArgument(isValidPair(start, end), "Invalid squares supplied to getFileDistance")

        Math.abs(start.file.number - end.file.number)
    }

    /**
     * Determines whether two squares are on the same diagonal. For example, {@code Paths.isSameDiagonal(a3, d7)}
     * would return {@code false}, but {@code Paths.isSameDiagonal(a3, d6)} would return {@code true}.
     *
     * @param start The first square
     * @param end The second square
     * @return {@code true} if the two squares are diagonal from each other, {@code false} otherwise
     */
    public static boolean isSameDiagonal(final Square start, final Square end)
    {
        Preconditions.checkArgument(isValidPair(start, end), "Invalid squares supplied to isSameDiagonal")

        getFileDistance(start, end) == getRankDistance(start, end)
    }

    /**
     * Determines whether there is a clear path between two squares. The path may be along a rank, file, or diagonal.
     *
     * @param start The first square
     * @param end The second square
     * @return {@code true} if there is a clear path between the two squares, {@code false} otherwise
     */
    public static boolean isPathClear(final Square start, final Square end)
    {
        Preconditions.checkArgument(isValidPair(start, end), "Invalid squares supplied to isPathClear")

        final int startIndex = ChessboardUtils.getBoardIndex(start.rank.number, start.file.number)
        final int endIndex = ChessboardUtils.getBoardIndex(end.rank.number, end.file.number)
        final Square[] squares = start.board.board

        boolean clear = false
        if (isSameSquareOrKnightMove(start, end)) {
            clear = true   /* path is always clear for knights since they can jump over any other piece */
        } else if (start.rank == end.rank) {      /* strictly horizontal (e.g., a4 => d4) */
            clear = isPathClearSameRank((start.file < end.file), startIndex, endIndex, squares)
        } else if (start.file == end.file) {      /* strictly vertical (e.g., c7 => c3) */
            clear = isPathClearSameFile((start.rank < end.rank), startIndex, endIndex, squares)
        } else if (isSameDiagonal(start, end)) {      /* diagonal */
            clear = isPathClearSameDiagonal((start.rank - end.rank), (end.file - start.file), startIndex, endIndex, squares)
        }
        clear
    }

    /** Helper to check if the two squares are actually the same square or if there is a knight on the start square. */
    private static boolean isSameSquareOrKnightMove(final Square start, final Square end)
    {
        start == end || (start.piece.present && PieceType.KNIGHT == start.piece.get().pieceType)
    }

    /** Helper to check if all the squares between the starting and ending indices (along the same rank) are empty. */
    private static boolean isPathClearSameRank(final boolean towardKingside, final int startIndex, final int endIndex, final Square[] squares)
    {
        boolean clear = true
        if (towardKingside) {
            for (int i = startIndex + 1; i < endIndex; i++) {
                clear &= squares[i].isEmpty()
            }
        } else {    /* moving kingside => queenside */
            for (int i = startIndex - 1; i > endIndex; i--) {
                clear &= squares[i].isEmpty()
            }
        }
        clear
    }

    /** Helper to check if all the squares between the starting and ending indices (along the same file) are empty. */
    private static boolean isPathClearSameFile(final boolean towardEighthRank, final int startIndex, final int endIndex, final Square[] squares)
    {
        boolean clear = true
        if (towardEighthRank) {
            for (int i = startIndex + NUM_FILES; i < endIndex; i += NUM_FILES) {
                clear &= squares[i].isEmpty()
            }
        } else {    /* moving 8th rank => 1st rank */
            for (int i = startIndex - NUM_FILES; i > endIndex; i -= NUM_FILES) {
                clear &= squares[i].isEmpty()
            }
        }
        clear
    }

    /** Helper to check if all the squares between the starting and ending indices (along the same diagonal) are empty. */
    private static boolean isPathClearSameDiagonal(final int rankDistance, final int fileDistance, final int startIndex,
                                                   final int endIndex, final Square[] squares)
    {
        boolean clear = true
        int increment
        if (MathUtils.signum(rankDistance) == MathUtils.signum(fileDistance)) {
            increment = NUM_FILES + 1
            if (rankDistance > 0) {
                for (int i = startIndex + increment; i < endIndex; i += increment) {
                    clear &= squares[i].isEmpty()
                }
            } else {
                for (int i = endIndex - increment; i > startIndex; i -= increment) {
                    clear &= squares[i].isEmpty()
                }
            }
        } else {
            increment = NUM_FILES - 1
            if (rankDistance > 0) {
                for (int i = startIndex + increment; i < endIndex; i += increment) {
                    clear &= squares[i].isEmpty()
                }
            } else {
                for (int i = startIndex - increment; i > endIndex; i -= increment) {
                    clear &= squares[i].isEmpty()
                }
            }
        }
        clear
    }

    /** Helper to check if two squares are compatible. */
    private static boolean isValidPair(final Square a, final Square b)
    {
        a && b && a.board && a.board == b.board
    }

}
