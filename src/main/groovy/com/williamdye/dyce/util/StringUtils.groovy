package com.williamdye.dyce.util

import com.williamdye.dyce.pieces.Piece

/**
 * Utility class for joining lists of strings and {@code Piece}s.
 */
final class StringUtils
{

    /** Prevent instantiation. */
    private StringUtils() { }

    /**
     * Given an array of strings and a separator string, return a string consisting of the elements in the array
     * with the separator in between them. Any {@code null} elements in the array are skipped.
     *
     * Example:
     * <pre>
     * {@code
     * String joined = StringUtils.join(new String[]{"foo", null, "bar", "baz"}, "--");
     * assert joined.equals("foo--bar--baz");
     * }
     * </pre>
     *
     * @param array Array of strings to be joined
     * @param separator String to insert between elements of the array
     * @return A string consisting of the array joined using the separator
     */
    public static String join(final String[] array, String separator)
    {
        join(array.toList(), separator)
    }

    /**
     * Given a list of strings and a separator string, return a string consisting of the elements in the list with
     * the separator in between them. This method behaves just like {@link #join(String[], String)} except that the
     * first parameter is a {@code List} rather than an array.
     *
     * @param list List of strings to be joined
     * @param separator String to insert between elements of the list
     * @return A string consisting of the list joined using the separator
     */
    public static String join(final List<String> list, String separator) {
        list.findAll { it != null }.join(separator ?: "")
    }

    /**
     * Given a list of {@link com.williamdye.dyce.pieces.Piece}s and a separator, return a string consisting of
     * the elements of the list with the separator in between them. The result may be optionally sorted by the
     * material value of the pieces. Any {@code null} elements in the list are skipped.
     *
     * Example:
     * <pre>
     * {@code
     * String joined = StringUtils.join(Arrays.asList(pawn, bishop, rook, knight, queen), "_");
     * assert joined.equals("Q_R_B_N_P");
     * }
     * </pre>
     *
     * @param list List of pieces to be joined
     * @param separator String to insert between elements of the list
     * @param sort Whether to sort the result by material value
     * @return A string consisting of the (possibly sorted) list joined using the separator
     */
    public static String joinPieceList(final List<Piece> list, String separator, final boolean sort)
    {
        if (sort) {
            Collections.sort(list)
            Collections.reverse(list)
        }
        join(list.collect { String.valueOf(it.boardRepresentation) }, separator)
    }

}
