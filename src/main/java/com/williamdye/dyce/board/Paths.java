package com.williamdye.dyce.board;

import static com.williamdye.dyce.board.ChessboardImpl.NUM_FILES;

import com.google.common.base.Preconditions;

import com.williamdye.dyce.pieces.PieceType;
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
        Preconditions.checkArgument(isValidPair(start, end), "Invalid squares");

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
        Preconditions.checkArgument(isValidPair(start, end), "Invalid squares");

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
        Preconditions.checkArgument(isValidPair(start, end), "Invalid squares");

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
        Preconditions.checkArgument(isValidPair(start, end), "Invalid squares");

        final Square[] squares = start.getBoard().getBoard();
        final int startIndex = (((start.getRank().getNumber() - 1) * NUM_FILES) + (start.getFile().getNumber() - 1));
        final int endIndex = (((end.getRank().getNumber() - 1) * NUM_FILES) + (end.getFile().getNumber() - 1));
        int i;
        boolean clear = false;
        if (start.equals(end) || ((start.getPiece().isPresent()) && (PieceType.KNIGHT == start.getPiece().get().getPieceType())))
            clear = true;   /* path is always clear for knights since they can jump over any other piece */
        else if (start.getRank() == end.getRank()) {    /* strictly horizontal (e.g., a4 => d4) */
            clear = true;
            if (start.getFile().getNumber() < end.getFile().getNumber()) {
                for (i = startIndex + 1; i < endIndex; i++)
                    clear &= squares[i].isEmpty();
            } else {    /* moving kingside => queenside */
                for (i = endIndex - 1; i > startIndex; i--)
                    clear &= squares[i].isEmpty();
            }
        } else if (start.getFile() == end.getFile()) {  /* strictly vertical (e.g., c7 => c3) */
            clear = true;
            if (start.getRank().getNumber() < end.getRank().getNumber()) {
                for (i = startIndex + NUM_FILES; i < endIndex; i += NUM_FILES)
                    clear &= squares[i].isEmpty();
            } else {    /* moving 8th rank => 1st rank */
                for (i = startIndex - NUM_FILES; i > endIndex; i -= NUM_FILES)
                    clear &= squares[i].isEmpty();
            }
        } else if (Paths.getRankDistance(start, end) == Paths.getFileDistance(start, end)) { /* diagonal */
            clear = true;
            int rankDistance = end.getRank().getNumber() - start.getRank().getNumber();
            int fileDistance = end.getFile().getNumber() - start.getFile().getNumber();
            int increment;
            if (MathUtils.signum(rankDistance) == MathUtils.signum(fileDistance)) {
                increment = NUM_FILES + 1;
                if (rankDistance > 0) {
                    for (i = startIndex + increment; i < endIndex; i += increment)
                        clear &= squares[i].isEmpty();
                } else {
                    for (i = endIndex - increment; i > startIndex; i -= increment)
                        clear &= squares[i].isEmpty();
                }
            } else {
                increment = NUM_FILES - 1;
                if (rankDistance > 0) {
                    for (i = startIndex + increment; i < endIndex; i += increment)
                        clear &= squares[i].isEmpty();
                } else {
                    for (i = endIndex - increment; i > startIndex; i -= increment)
                        clear &= squares[i].isEmpty();
                }
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
