package com.williamdye.dyce.util;

import com.williamdye.dyce.pieces.Piece;
import com.williamdye.dyce.pieces.PieceColor;

import java.util.Collections;
import java.util.List;

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
