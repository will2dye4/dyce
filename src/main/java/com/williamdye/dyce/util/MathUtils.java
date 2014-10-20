package com.williamdye.dyce.util;

/**
 * Utility class for common mathematical operations.
 *
 * @author William Dye
 */
public final class MathUtils
{

    /** Prevent instantiation. */
    private MathUtils() { }

    /**
     * Given an integer value, return its signum. In other words, return -1, 0, or 1 based on the sign of the argument.
     *
     * @param value The value for which to find the signum
     * @return The signum of the value (-1, 0, or 1)
     */
    public static int signum(int value)
    {
        return signum((long)value);
    }

    /**
     * Given a long value, return its signum. In other words, return -1, 0, or 1 based on the sign of the argument.
     *
     * @param value The value for which to find the signum
     * @return The signum of the value (-1, 0, or 1)
     */
    public static int signum(long value)
    {
        return ((value > 0) ? 1 : ((value == 0) ? 0 : -1));
    }

}
