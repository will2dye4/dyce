package com.williamdye.dyce.util;

public class StringUtils
{

    public static String join(String[] array, String separator)
    {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i != array.length - 1)
                builder.append(separator);
        }
        return builder.toString();
    }

}
