package com.williamdye.dyce.board.formatter;

import java.util.List;

import com.google.common.base.Splitter;

import com.williamdye.dyce.board.Chessboard;
import com.williamdye.dyce.pieces.*;
import com.williamdye.dyce.util.StringUtils;

/**
 * Default string-based implementation of the {@link com.williamdye.dyce.board.formatter.ChessboardFormatter} interface.
 * The formatted string is an ASCII-based depiction of the chessboard intended for displaying to users.
 *
 * @author William Dye
 */
public class DefaultChessboardFormatter implements ChessboardFormatter<String> {

    /** The spacing we use from the left margin. */
    protected final String SPACING = "  ";

    /** The spacing we use between the board and the status information on the right. */
    protected final String WIDE_SPACING = "\t\t";

    /** The horizontal "lines" in the chessboard table. */
    protected final String RANK_SEPARATOR = SPACING + "  +---+---+---+---+---+---+---+---+\n";

    /** The labels for the files along the bottom of the chessboard. */
    protected final String FILE_LABELS = SPACING + "    a   b   c   d   e   f   g   h  \n";

    /** A string splitter that splits on the forward slash (/) character. */
    protected final Splitter rankSplitter = Splitter.on('/');

    @Override
    public String format(Chessboard chessboard)
    {
        final List<Piece> capturedWhitePieces = chessboard.getCapturedPieces(PieceColor.WHITE);
        final List<Piece> capturedBlackPieces = chessboard.getCapturedPieces(PieceColor.BLACK);
        final StringBuilder builder = new StringBuilder(RANK_SEPARATOR);

        int i = 8;
        for (String rank : rankSplitter.split(chessboard.getFEN().getFENString())) {
            builder.append(String.format("%s%d |", SPACING, i));
            formatRank(rank, builder);
            formatStatusInfo(i, capturedWhitePieces, capturedBlackPieces, builder);
            builder.append("\n" + RANK_SEPARATOR);
            i--;
        }

        return builder.append(FILE_LABELS).toString();
    }

    private void formatRank(final String rank, final StringBuilder builder)
    {
        for (char c : rank.toCharArray()) {
            if (Character.isDigit(c)) {
                appendBlankSpaces(c, builder);
            } else {
                builder.append(String.format(" %s |", c));
            }
        }
    }

    private void appendBlankSpaces(final char count, final StringBuilder builder)
    {
        int i = Integer.parseInt(String.valueOf(count));
        while (i > 0) {
            builder.append("   |");
            i--;
        }
    }

    private void formatStatusInfo(final int rank, final List<Piece> capturedWhitePieces,
                                  final List<Piece> capturedBlackPieces, final StringBuilder builder)
    {
        if ((rank == 7) && (!capturedWhitePieces.isEmpty() || !capturedBlackPieces.isEmpty()))
            builder.append(String.format("%s[[ Captured Pieces ]]", WIDE_SPACING));
        else if ((rank == 6) && !capturedWhitePieces.isEmpty())
            builder.append(String.format("%sW: %s", WIDE_SPACING, StringUtils.joinPieceList(capturedWhitePieces, " ", true)));
        else if ((rank == 5) && !capturedBlackPieces.isEmpty())
            builder.append(String.format("%sB: %s", WIDE_SPACING, StringUtils.joinPieceList(capturedBlackPieces, " ", true)));
    }

}
