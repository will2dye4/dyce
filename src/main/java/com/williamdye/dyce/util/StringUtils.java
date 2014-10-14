package com.williamdye.dyce.util;

import com.williamdye.dyce.pieces.*;

import java.util.*;

public abstract class StringUtils
{

    public static String join(String[] array, String separator)
    {
        if (separator == null)
            separator = "";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null)
                builder.append(array[i]);
            if (i != array.length - 1)
                builder.append(separator);
        }
        return builder.toString();
    }

    public static String joinPieceList(List<Piece> list, String separator, boolean sort)
    {
        if (separator == null)
            separator = "";
        if (sort) {
            Collections.sort(list);
            Collections.reverse(list);
        }
        String[] array = new String[list.size()];
        int i = 0;
        for (Piece piece : list) {
            char type = piece.getPieceType().getSymbol();
            if (piece.getColor() == PieceColor.WHITE)
                type -= 32; /* uppercase letters are 32 below lowercase in the ASCII table */
            array[i++] = String.valueOf(type);
        }
        return join(array, separator);
    }

}
