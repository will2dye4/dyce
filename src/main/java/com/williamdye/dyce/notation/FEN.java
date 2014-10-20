package com.williamdye.dyce.notation;

import static com.williamdye.dyce.board.DefaultChessboard.*;

import java.util.regex.Pattern;

import com.williamdye.dyce.board.*;
import com.williamdye.dyce.util.StringUtils;

/**
 * The {@code FEN} class is capable of producing a Forsyth-Edwards Notation (FEN) string from a {@link Chessboard} instance.
 *
 * @author William Dye
 */
public class FEN
{

    /** The FEN string for the standard chess starting position. */
    public static final String INITIAL_FEN_STRING = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    /** The number of components in a well-formed FEN string. */
    public static final int NUM_COMPONENTS = 6;

    /** A pattern for matching the castling component of a FEN string. */
    protected static final Pattern CASTLING_PATTERN = Pattern.compile("^(KQkq|KQk|KQq|Kkq|Qkq|KQ|kq|Kk|Kq|Qk|Qq|K|Q|k|q|-)$");

    /** A pattern for matching the en passant component of a FEN string. */
    protected static final Pattern EN_PASSANT_SQUARE_PATTERN = Pattern.compile("^([a-h][36]|-)$");

    /** A pattern for matching arbitrary integer values. */
    protected static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]+$");

    /** A pattern for matching the FEN representation of a single rank on a chessboard. */
    protected static final Pattern RANK_PATTERN = Pattern.compile("^(([1-7]?[bknpqrBKNPQR]([1-7]?[bknpqrBKNPQR])*[1-7]?)|8)$");

    /** The FEN string that this object represents. */
    protected String fenString;

    /** The chessboard that this object represents. */
    protected Chessboard board;

    /** Whether or not this object was created from a FEN string. */
    private boolean fromString;

    /**
     * Construct a {@code FEN} from the specified valid FEN string.
     *
     * @param fen The FEN string
     */
    public FEN(String fen)
    {
        this(fen, null, true);
    }

    /**
     * Construct a {@code FEN} from the specified chessboard.
     *
     * @param chessboard The chessboard to represent in FEN
     */
    public FEN(Chessboard chessboard)
    {
        this(null, chessboard, false);
    }

    /**
     * Helper constructor for the monad constructors above.
     *
     * @param fen The FEN string
     * @param chessboard The chessboard to represent in FEN
     * @param useString Whether to use the FEN string or the chessboard
     */
    protected FEN(String fen, Chessboard chessboard, boolean useString)
    {
        if (useString && (fen == null || !isValidFENString(fen)))
            throw new IllegalArgumentException("Invalid FEN string provided!");
        if (!useString && chessboard == null)
            throw new IllegalArgumentException("Invalid chessboard provided!");
        this.fenString = fen;
        this.board = chessboard;
        this.fromString = useString;
    }

    /**
     * Check whether the specified string is valid Forsyth-Edwards Notation.
     *
     * @param test The string to validate
     * @return {@code true} if the string is valid, {@code false} otherwise
     * @see <a href="http://chessprogramming.wikispaces.com/Forsyth-Edwards+Notation">Forsyth-Edwards Notation</a>
     */
    public static boolean isValidFENString(final String test)
    {
        String[] components = test.split(" ");  /* split the string into its components, delimited by spaces */
        if (components.length == NUM_COMPONENTS) {
            String[] ranks = components[0].split("/");  /* split the position representation into individual ranks */
            if (ranks.length == NUM_RANKS) {
                for (String rank : ranks)
                    if (!isValidRankString(rank))
                        return false;   /* invalid rank string makes the whole FEN invalid */
                boolean valid = ("b".equals(components[1]) || "w".equals(components[1]));
                valid &= CASTLING_PATTERN.matcher(components[2]).matches();
                valid &= EN_PASSANT_SQUARE_PATTERN.matcher(components[3]).matches();
                valid &= (NUMBER_PATTERN.matcher(components[4]).matches() && NUMBER_PATTERN.matcher(components[5]).matches());
                return valid;   /* position is good; valid represents the validity of the last five components */
            }
        }
        return false;   /* invalid number of components or invalid number of ranks */
    }

    /**
     * Accessor for the FEN string itself.
     *
     * @return The FEN string
     */
    public String getFENString()
    {
        if (!fromString)
            computeFENString();
        return fenString;
    }

    /** Helper to create the FEN string if this instance was created from a chessboard. */
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
                builder.append(String.valueOf(square.getPiece().get().getBoardRepresentation()));
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

    /** Helper to validate a single rank from a FEN string. */
    private static boolean isValidRankString(final String rank)
    {
        boolean valid = false;
        if (RANK_PATTERN.matcher(rank).matches()) {
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
