package com.williamdye.dyce;

import com.williamdye.dyce.board.ChessboardImpl;
import com.williamdye.dyce.board.Square;

import java.util.regex.Pattern;

/**
 * @author William Dye
 */
public class FEN
{
    protected String string;
    protected Square[] squares;

    public FEN(String fen)
    {
        this(fen, null);
    }

    public FEN(Square[] board)
    {
        this(null, board);
    }

    public FEN(String fen, Square[] board)
    {
        if ((fen == null) && (board == null))
            throw new IllegalArgumentException("A string or an array of squares must be provided to instantiate FEN!");
        if ((board != null) && (board.length != ChessboardImpl.BOARD_SIZE))
            throw new IllegalArgumentException("Square array must be of size " + ChessboardImpl.BOARD_SIZE + "!");
        if ((fen != null) && !(isValidFEN(fen)))
            throw new IllegalArgumentException("Invalid FEN provided!");
        this.string = fen;
        this.squares = board;
    }

    /* See http://chessprogramming.wikispaces.com/Forsyth-Edwards+Notation for a complete description
     * of the format of Forsyth-Edwards Notation.
     */
    public static boolean isValidFEN(String test)
    {
        String[] components = test.split(" ");  /* split the string into its components, delimited by spaces */
        if (components.length == 6) {
            String[] ranks = components[0].split("/");  /* split the position representation into individual ranks */
            if (ranks.length == 8) {
                for (String rank : ranks)
                    if (!isValidRankString(rank))
                        return false;   /* invalid rank string makes the whole FEN invalid */
                boolean valid = ("b".equals(components[1]) || "w".equals(components[1]));
                valid &= Pattern.matches("^(?:KQkq|KQk|KQq|Kkq|Qkq|KQ|kq|Kk|Kq|Qk|Qq|K|Q|k|q|-)$", components[2]);
                valid &= Pattern.matches("^(?:(?:(?:[a-h])[36])|-)$", components[3]);
                valid &= (Pattern.matches("^[0-9]+$", components[4]) && Pattern.matches("^[0-9]+$", components[5]));
                return valid;   /* position is good; valid represents the validity of the last five components */
            }
        }
        return false;   /* invalid number of components or invalid number of ranks */
    }

    public Square[] getSquares()
    {
        if (squares == null)
            computeSquares();
        return squares;
    }

    private void computeSquares()
    {
        squares = new Square[ChessboardImpl.BOARD_SIZE];
        /* TODO */
    }

    private static boolean isValidRankString(String rank)
    {
        boolean valid = false;
        if (Pattern.matches("^(?:(?:[1-7]?[bknpqrBKNPQR](?:[1-7]?[bknpqrBKNPQR])*[1-7]?)|8)$", rank)) {
            int total = 0;  /* count the number of pieces + empty squares on this rank */
            for (int i = 0; i < rank.length(); i++) {
                if (Character.isDigit(rank.charAt(i)))
                    total += Integer.parseInt(rank.substring(i, i + 1));
                else
                    total += 1; /* rank.charAt(i) represents one valid piece */
            }
            valid = (total == 8);

        }
        return valid;
    }

}
