package com.williamdye.dyce;

import com.williamdye.dyce.board.Chessboard;
import com.williamdye.dyce.board.ChessboardImpl;
import com.williamdye.dyce.board.Square;
import com.williamdye.dyce.pieces.PieceColor;
import com.williamdye.dyce.util.StringUtils;

import java.util.regex.Pattern;

import static com.williamdye.dyce.board.ChessboardImpl.NUM_FILES;
import static com.williamdye.dyce.board.ChessboardImpl.NUM_RANKS;

/**
 * @author William Dye
 */
public class FEN
{
    public static final String INITIAL_FEN_STRING = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    public static final int NUM_COMPONENTS = 6;

    protected String fenString;
    protected Chessboard board;
    private boolean fromString;

    public FEN(String fen)
    {
        this(fen, null, true);
    }

    public FEN(Chessboard board)
    {
        this(null, board, false);
    }

    public FEN(String fen, Chessboard chessboard, boolean useString)
    {
        if (useString && (fen == null || !isValidFENString(fen)))
            throw new IllegalArgumentException("Invalid FEN string provided!");
        if (!useString && chessboard == null)
            throw new IllegalArgumentException("Invalid chessboard provided!");
        this.fenString = fen;
        this.board = chessboard;
        this.fromString = useString;
    }

    /* See http://chessprogramming.wikispaces.com/Forsyth-Edwards+Notation for a complete description
     * of the format of Forsyth-Edwards Notation.
     */
    public static boolean isValidFENString(String test)
    {
        String[] components = test.split(" ");  /* split the string into its components, delimited by spaces */
        if (components.length == NUM_COMPONENTS) {
            String[] ranks = components[0].split("/");  /* split the position representation into individual ranks */
            if (ranks.length == NUM_RANKS) {
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

    public String getFENString()
    {
        if (!fromString)
            computeFENString();
        return fenString;
    }

    private void computeFENString()
    {
        Square[] squares = board.getBoard();
        String[] ranks = new String[NUM_RANKS];
        StringBuilder builder = new StringBuilder();
        int j = ranks.length - 1;
        int consecutiveEmpties = 0;
        for (int i = 0; i < squares.length; i++) {
            Square square = squares[i];
            if (square.isEmpty())
                consecutiveEmpties++;
            else {
                if (consecutiveEmpties > 0) {
                    builder.append(String.valueOf(consecutiveEmpties));
                    consecutiveEmpties = 0;
                }
                char type = square.getPiece().getPieceType().getSymbol();
                if (square.getPiece().getColor() == PieceColor.WHITE)
                    type -= 32; /* uppercase letters are 32 below lowercase in the ASCII table */
                builder.append(String.valueOf(type));
            }
            if ((i % NUM_FILES) == (NUM_FILES - 1)) {
                if (consecutiveEmpties > 0) {
                    builder.append(String.valueOf(consecutiveEmpties));
                    consecutiveEmpties = 0;
                }
                ranks[j--] = builder.toString();
                builder.setLength(0);
            }
        }
        fenString = StringUtils.join(ranks, "/");
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
            valid = (total == NUM_FILES);
        }
        return valid;
    }

}
