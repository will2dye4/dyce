package com.williamdye.dyce.board;

import com.williamdye.dyce.FEN;
import com.williamdye.dyce.pieces.*;

import java.util.regex.Pattern;

public class ChessboardImpl implements Chessboard
{
    public static final int NUM_RANKS = 8;
    public static final int NUM_FILES = 8;
    public static final int NUM_SQUARES = NUM_RANKS * NUM_FILES;

    /* fen is the Forsyth-Edwards Notation of the current position */
    protected FEN fen;
    /* squares is a one-dimensional array of squares representing the chessboard as follows:
     * [a1, b1, c1, d1, e1, f1, g1, h1,
     *  a2, b2, c2, d2, e2, f2, g2, h2,
     *  ...,
     *  a8, b8, c8, d8, e8, f8, g8, h8]
     */
    protected Square[] squares;
    protected Square enPassantTarget;
    protected PieceColor toMove;
    protected String castling;
    protected int moveCount;
    protected int halfMoveTotal;
    protected int halfMoveClock;

    public ChessboardImpl()
    {
        this.fen = new FEN(this);
        this.squares = new Square[NUM_SQUARES];
        this.enPassantTarget = null;
        this.toMove = PieceColor.WHITE;
        this.castling = "KQkq";
        this.moveCount = 1;
        this.halfMoveTotal = 0;
        this.halfMoveClock = 0;
        initialize();
    }

    private void initialize()
    {
        int i = 0;
        Square square;
        for (Rank rank : Rank.values()) {
            for (File file : File.values()) {
                switch (rank) {
                    case SECOND_RANK:
                        square = new SquareImpl(rank, file, new Pawn(PieceColor.WHITE));
                        break;
                    case SEVENTH_RANK:
                        square = new SquareImpl(rank, file, new Pawn(PieceColor.BLACK));
                        break;
                    case FIRST_RANK:
                    case EIGHTH_RANK:
                        PieceColor color = ((rank == Rank.FIRST_RANK) ? PieceColor.WHITE : PieceColor.BLACK);
                        switch (file) {
                            case A_FILE:
                            case H_FILE:
                                square = new SquareImpl(rank, file, new Rook(color));
                                break;
                            case B_FILE:
                            case G_FILE:
                                square = new SquareImpl(rank, file, new Knight(color));
                                break;
                            case C_FILE:
                            case F_FILE:
                                square = new SquareImpl(rank, file, new Bishop(color));
                                break;
                            case D_FILE:
                                square = new SquareImpl(rank, file, new Queen(color));
                                break;
                            case E_FILE:
                                square = new SquareImpl(rank, file, new King(color));
                                break;
                            default:
                                throw new IllegalStateException("File.values() returned a non-File member!");
                        }   /* switch (file) */
                        break;
                    default:
                        square = new SquareImpl(rank, file);
                        break;
                }   /* switch (rank) */
                squares[i++] = square;
            }   /* for each file */
        }   /* for each rank */
    }

    @Override
    public FEN getFEN()
    {
        return fen;
    }

    @Override
    public String prettyPrint()
    {
        final String SPACING = "  ";
        final String RANK_SEPARATOR =   SPACING + "  +---+---+---+---+---+---+---+---+\n";
        final String FILE_LABELS    =   SPACING + "    a   b   c   d   e   f   g   h  \n";
        StringBuilder builder = new StringBuilder();
        builder.append(RANK_SEPARATOR);
        String[] ranks = getFEN().getFENString().split("/");
        int i = 8;
        for (String rank : ranks) {
            builder.append(SPACING + i + " |");
            for (int j = 0; j < rank.length(); j++) {
                if (Character.isDigit(rank.charAt(j))) {
                    int k = Integer.parseInt(String.valueOf(rank.charAt(j)));
                    while (k > 0) {
                        builder.append("   |");
                        k--;
                    }
                }
                else {
                    builder.append(String.format(" %s |", rank.charAt(j)));
                }
            }
            builder.append("\n" + RANK_SEPARATOR);
            i--;
        }
        builder.append(FILE_LABELS);
        return builder.toString();
    }

    @Override
    public Square[] getBoard()
    {
        return squares;
    }

    @Override
    public Square getSquareByName(String name)
    {
        Square square = null;
        if (Pattern.matches("^(?:[a-h])[1-8]$", name)) {
            String whichFile = name.substring(0, 1);
            int whichRank = Integer.parseInt(name.substring(1));
            int offset = File.forName(whichFile).getNumber() - 1;
            /*int offset = 0;
            for (File file : File.values()) {
                if (file.toString().equals(whichFile))
                    break;
                offset++;
            }*/
            int i = ((whichRank - 1) * NUM_FILES) + offset;
            square = squares[i];
        }
        return square;
    }

    @Override
    public Square getEnPassantTargetSquare()
    {
        return enPassantTarget;
    }

    @Override
    public PieceColor getActiveColor()
    {
        return toMove;
    }

    @Override
    public int getMoveCount()
    {
        return moveCount;
    }

    @Override
    public int getHalfMoveClock()
    {
        return halfMoveClock;
    }

    @Override
    public int getHalfMoveTotal()
    {
        return halfMoveTotal;
    }

    @Override
    public String getCastlingAvailability()
    {
        return castling;
    }

    @Override
    /* TODO:
     *   - validate move
     *   - update halfMoveClock
     *   - update castling
     *   - update e.p. target
     */
    public void move(Piece piece, Square dest)
    {
        piece.move(dest);
        halfMoveTotal++;
        if (toMove == PieceColor.BLACK) {
            moveCount++;
            toMove = PieceColor.WHITE;
        }
        else
            toMove = PieceColor.BLACK;
    }

    @Override
    public void move(String from, String to)
    {
        move(getSquareByName(from).getPiece(), getSquareByName(to));
    }

}
