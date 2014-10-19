package com.williamdye.dyce.util;

/**
 * @author William Dye
 */
public final class MathUtils
{

    private MathUtils() { }

    public static int signum(int value)
    {
        return signum((long)value);
    }

    public static int signum(long value)
    {
        return ((value > 0) ? 1 : ((value == 0) ? 0 : -1));
    }

}
