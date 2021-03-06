package com.williamdye.dyce.board

/**
 * An enumeration of the files (vertical columns) on the chessboard.
 *
 * @author William Dye
 */
enum File implements Comparable<File>
{
    /** The A file, the file farthest to the queenside. */
    A_FILE("a", 1),
    /** The B file. */
    B_FILE("b", 2),
    /** The C file. */
    C_FILE("c", 3),
    /** The D file. */
    D_FILE("d", 4),
    /** The E file. */
    E_FILE("e", 5),
    /** The F file. */
    F_FILE("f", 6),
    /** The G File. */
    G_FILE("g", 7),
    /** The H file, the file farthest to the kingside. */
    H_FILE("h", 8);

    /** The file's name (in lower case). */
    protected String name

    /** The file's number, where the A file is #1, B file is #2, etc. */
    protected int num

    /**
     * Construct a {@code File} with the specified name and number.
     *
     * @param string The name of the file
     * @param number The file's number
     */
    File(String string, int number)
    {
        this.name = string
        this.num = number
    }

    /**
     * Find a {@code File} by name.
     *
     * @param name The name of the file to return
     * @return The file with the specified name
     */
    public static File forName(final String name)
    {
        values().find { it.toString() == name }
    }

    /**
     * Accessor for a file's number.
     *
     * @return The number of the file (A = 1, B = 2, C = 3, etc.)
     */
    int getNumber()
    {
        num
    }

    boolean isKingside()
    {
        num > 4
    }

    @Override
    String toString()
    {
        name
    }

    int minus(File file)
    {
        num - file.number
    }

}
