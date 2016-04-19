package com.williamdye.dyce.board.formatter

import com.williamdye.dyce.board.Chessboard
import com.williamdye.dyce.pieces.Piece
import com.williamdye.dyce.pieces.PieceColor
import com.williamdye.dyce.util.StringUtils

/**
 * Default string-based implementation of the {@link ChessboardFormatter} interface.
 * The formatted string is an ASCII-based depiction of the chessboard intended for displaying to users.
 *
 * @author William Dye
 */
final class DefaultChessboardFormatter implements ChessboardFormatter<String>
{

    /** The spacing we use from the left margin. */
    protected static final String SPACING = "  "

    /** The spacing we use between the board and the status information on the right. */
    protected static final String WIDE_SPACING = "\t\t"

    /** The horizontal "lines" in the chessboard table. */
    protected static final String RANK_SEPARATOR = SPACING + "  +---+---+---+---+---+---+---+---+\n"

    /** The labels for the files along the bottom of the chessboard. */
    protected static final String FILE_LABELS = SPACING + "    a   b   c   d   e   f   g   h  \n"

    /** Singleton implementation. */
    private static final DefaultChessboardFormatter formatter = new DefaultChessboardFormatter()

    /**
     * Returns a singleton instance of the {@code DefaultChessboardFormatter} class.
     *
     * @return The {@code DefaultChessboardFormatter} singleton
     */
    public static DefaultChessboardFormatter getInstance()
    {
        formatter
    }

    /** Prevent instantiation. */
    private DefaultChessboardFormatter() { }

    /**
     * Given a chessboard, return a string containing an ASCII-based representation of the board.
     *
     * @param chessboard The chessboard to format
     * @return The formatted chessboard (as a string)
     */
    @Override
    String format(Chessboard chessboard)
    {
        final List<Piece> capturedWhitePieces = chessboard.getCapturedPieces(PieceColor.WHITE)
        final List<Piece> capturedBlackPieces = chessboard.getCapturedPieces(PieceColor.BLACK)
        final StringBuilder builder = new StringBuilder(RANK_SEPARATOR)

        int i = 8
        chessboard.game.FEN.FENString.split('/').each { rank ->
            builder.append(SPACING).append(i).append(" |")
            formatRank(rank, builder)
            formatStatusInfo(i, capturedWhitePieces, capturedBlackPieces, builder)
            builder.append("\n").append(RANK_SEPARATOR)
            i--
        }

        builder.append(FILE_LABELS).toString()
    }

    /** Helper to format one rank from a FEN string. */
    private static void formatRank(final String rank, final StringBuilder builder)
    {
        rank.toCharArray().each { c ->
            if (Character.isDigit(c)) {
                appendBlankSpaces(c, builder)
            } else {
                builder.append(" $c |")
            }
        }
    }

    /** Helper to append some number of spaces to the string builder. */
    private static void appendBlankSpaces(final char count, final StringBuilder builder)
    {
        builder.append("   |" * Integer.parseInt(String.valueOf(count)))
    }

    /** Helper to append status information to the string builder based on the rank. */
    private static void formatStatusInfo(final int rank, final List<Piece> capturedWhitePieces,
                                         final List<Piece> capturedBlackPieces, final StringBuilder builder)
    {
        if ((rank == 7) && (capturedWhitePieces || capturedBlackPieces)) {
            builder.append(WIDE_SPACING).append("[[ Captured Pieces ]]")
        } else if ((rank == 6) && capturedWhitePieces) {
            builder.append(WIDE_SPACING) \
                    .append("W: ") \
                    .append(StringUtils.joinPieceList(capturedWhitePieces, " ", true)) \
                    .append(" (") \
                    .append(getSumOfPieceValues(capturedWhitePieces)) \
                    .append(")")
        } else if ((rank == 5) && capturedBlackPieces) {
            builder.append(WIDE_SPACING) \
                    .append("B: ") \
                    .append(StringUtils.joinPieceList(capturedBlackPieces, " ", true)) \
                    .append(" (") \
                    .append(getSumOfPieceValues(capturedBlackPieces)) \
                    .append(")")
            }
    }

    private static int getSumOfPieceValues(List<Piece> pieces)
    {
        pieces.inject(0) { sum, piece -> sum + piece.pieceType.materialValue }
    }

}
