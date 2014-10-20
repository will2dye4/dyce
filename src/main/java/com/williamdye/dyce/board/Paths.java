package com.williamdye.dyce.board;

import static com.williamdye.dyce.board.DefaultChessboard.NUM_FILES;

import com.google.common.base.Preconditions;

import com.williamdye.dyce.pieces.PieceType;
import com.williamdye.dyce.util.ChessboardUtils;
import com.williamdye.dyce.util.MathUtils;

/**
 * A utility class for determining various information about distances and paths between two squares.
 *
 * @author William Dye
 */
public final class Paths
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
        Preconditions.checkArgument(isValidPair(start, end), "Invalid squares supplied to getRankDistance");

        return Math.abs(start.getRank().getNumber() - end.getRank().getNumber());
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
        Preconditions.checkArgument(isValidPair(start, end), "Invalid squares supplied to getFileDistance");

        return Math.abs(start.getFile().getNumber() - end.getFile().getNumber());
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
        Preconditions.checkArgument(isValidPair(start, end), "Invalid squares supplied to isSameDiagonal");

        return (getFileDistance(start, end) == getRankDistance(start, end));
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
        Preconditions.checkArgument(isValidPair(start, end), "Invalid squares supplied to isPathClear");

        final int startRank = start.getRank().getNumber();
        final int endRank = end.getRank().getNumber();
        final int startFile = start.getFile().getNumber();
        final int endFile = end.getFile().getNumber();

        final int startIndex = ChessboardUtils.getBoardIndex(startRank, startFile);
        final int endIndex = ChessboardUtils.getBoardIndex(endRank, endFile);
        final Square[] squares = start.getBoard().getBoard();

        boolean clear = false;
        if (isSameSquareOrKnightMove(start, end))
            clear = true;   /* path is always clear for knights since they can jump over any other piece */
        else if (startRank == endRank)      /* strictly horizontal (e.g., a4 => d4) */
            clear = isPathClearSameRank((startFile < endFile), startIndex, endIndex, squares);
        else if (startFile == endFile)      /* strictly vertical (e.g., c7 => c3) */
            clear = isPathClearSameFile((startRank < endRank), startIndex, endIndex, squares);
        else if (isSameDiagonal(start, end))      /* diagonal */
            clear = isPathClearSameDiagonal((endRank - startRank), (endFile - startFile), startIndex, endIndex, squares);

        return clear;
    }

    /** Helper to check if the two squares are actually the same square or if there is a knight on the start square. */
    private static boolean isSameSquareOrKnightMove(final Square start, final Square end)
    {
        return (start.equals(end) || (start.getPiece().isPresent() && (PieceType.KNIGHT == start.getPiece().get().getPieceType())));
    }

    /** Helper to check if all the squares between the starting and ending indices (along the same rank) are empty. */
    private static boolean isPathClearSameRank(final boolean towardKingside, final int startIndex, final int endIndex, final Square[] squares)
    {
        boolean clear = true;
        if (towardKingside) {
            for (int i = startIndex + 1; i < endIndex; i++)
                clear &= squares[i].isEmpty();
        } else {    /* moving kingside => queenside */
            for (int i = endIndex - 1; i > startIndex; i--)
                clear &= squares[i].isEmpty();
        }
        return clear;
    }

    /** Helper to check if all the squares between the starting and ending indices (along the same file) are empty. */
    private static boolean isPathClearSameFile(final boolean towardEighthRank, final int startIndex, final int endIndex, final Square[] squares)
    {
        boolean clear = true;
        if (towardEighthRank) {
            for (int i = startIndex + NUM_FILES; i < endIndex; i += NUM_FILES)
                clear &= squares[i].isEmpty();
        } else {    /* moving 8th rank => 1st rank */
            for (int i = startIndex - NUM_FILES; i > endIndex; i -= NUM_FILES)
                clear &= squares[i].isEmpty();
        }
        return clear;
    }

    /** Helper to check if all the squares between the starting and ending indices (along the same diagonal) are empty. */
    private static boolean isPathClearSameDiagonal(final int rankDistance, final int fileDistance, final int startIndex,
                                                   final int endIndex, final Square[] squares)
    {
        boolean clear = true;
        int increment;
        if (MathUtils.signum(rankDistance) == MathUtils.signum(fileDistance)) {
            increment = NUM_FILES + 1;
            if (rankDistance > 0) {
                for (int i = startIndex + increment; i < endIndex; i += increment)
                    clear &= squares[i].isEmpty();
            } else {
                for (int i = endIndex - increment; i > startIndex; i -= increment)
                    clear &= squares[i].isEmpty();
            }
        } else {
            increment = NUM_FILES - 1;
            if (rankDistance > 0) {
                for (int i = startIndex + increment; i < endIndex; i += increment)
                    clear &= squares[i].isEmpty();
            } else {
                for (int i = endIndex - increment; i > startIndex; i -= increment)
                    clear &= squares[i].isEmpty();
            }
        }
        return clear;
    }

    /** Helper to check if two squares are compatible. */
    private static boolean isValidPair(final Square a, final Square b)
    {
        return ((a != null) && (b != null) && (a.getBoard() != null) && (a.getBoard().equals(b.getBoard())));
    }

}
