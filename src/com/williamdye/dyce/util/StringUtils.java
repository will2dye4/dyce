package com.williamdye.dyce.util;

public class StringUtils
{

    public static String join(String[] array, String separator) throws IllegalArgumentException
    {
        if (separator == null)
            throw new IllegalArgumentException("Separator may not be null!");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null)
                builder.append(array[i]);
            if (i != array.length - 1)
                builder.append(separator);
        }
        return builder.toString();
    }

}
