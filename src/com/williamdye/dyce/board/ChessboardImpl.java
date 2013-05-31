package com.williamdye.dyce.board;

import com.williamdye.dyce.notation.FEN;
import com.williamdye.dyce.pieces.*;
import com.williamdye.dyce.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

public class ChessboardImpl implements Chessboard
{
    public static final int NUM_RANKS = 8;
    public static final int NUM_FILES = 8;
    public static final int NUM_SQUARES = NUM_RANKS * NUM_FILES;

    /* fen is the Forsyth-Edwards Notation of the current position */
    protected final FEN fen;
    /* squares is a one-dimensional array of squares representing the chessboard as follows:
     * [a1, b1, c1, d1, e1, f1, g1, h1,
     *  a2, b2, c2, d2, e2, f2, g2, h2,
     *  ...,
     *  a8, b8, c8, d8, e8, f8, g8, h8]
     */
    protected final Square[] squares;
    protected final Map<PieceType, List<Piece>> whitePieces;
    protected final List<Piece> activeWhitePieces;
    protected final List<Piece> capturedWhitePieces;
    protected final Map<PieceType, List<Piece>> blackPieces;
    protected final List<Piece> activeBlackPieces;
    protected final List<Piece> capturedBlackPieces;
    protected PieceColor toMove;
    protected Square enPassantTarget;
    protected String castling;
    protected int moveCount;
    protected int halfMoveTotal;
    protected int halfMoveClock;

    public ChessboardImpl()
    {
        this.fen = new FEN(this);
        this.squares = new Square[NUM_SQUARES];
        this.whitePieces = new LinkedHashMap<PieceType, List<Piece>>();
        this.activeWhitePieces = new ArrayList<Piece>();
        this.capturedWhitePieces = new ArrayList<Piece>();
        this.blackPieces = new LinkedHashMap<PieceType, List<Piece>>();
        this.activeBlackPieces = new ArrayList<Piece>();
        this.capturedBlackPieces = new ArrayList<Piece>();
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
        for (PieceType type : PieceType.values()) {
            whitePieces.put(type, new ArrayList<Piece>());
            blackPieces.put(type, new ArrayList<Piece>());
        }
        int i = 0;
        Square square;
        Piece piece;
        for (Rank rank : Rank.values()) {
            for (File file : File.values()) {
                switch (rank) {
                    case SECOND_RANK:
                        piece = new Pawn(PieceColor.WHITE);
                        square = new SquareImpl(this, rank, file, piece);
                        activeWhitePieces.add(piece);
                        whitePieces.get(PieceType.PAWN).add(piece);
                        break;
                    case SEVENTH_RANK:
                        piece = new Pawn(PieceColor.BLACK);
                        square = new SquareImpl(this, rank, file, piece);
                        activeBlackPieces.add(piece);
                        blackPieces.get(PieceType.PAWN).add(piece);
                        break;
                    case FIRST_RANK:
                    case EIGHTH_RANK:
                        PieceColor color;
                        List<Piece> list;
                        Map<PieceType, List<Piece>> map;
                        if (rank == Rank.FIRST_RANK) {
                            color = PieceColor.WHITE;
                            list = activeWhitePieces;
                            map = whitePieces;
                        } else {
                            color = PieceColor.BLACK;
                            list = activeBlackPieces;
                            map = blackPieces;
                        }
                        switch (file) {
                            case A_FILE:
                            case H_FILE:
                                piece = new Rook(color);
                                list.add(piece);
                                map.get(PieceType.ROOK).add(piece);
                                square = new SquareImpl(this, rank, file, piece);
                                break;
                            case B_FILE:
                            case G_FILE:
                                piece = new Knight(color);
                                list.add(piece);
                                map.get(PieceType.KNIGHT).add(piece);
                                square = new SquareImpl(this, rank, file, piece);
                                break;
                            case C_FILE:
                            case F_FILE:
                                piece = new Bishop(color);
                                list.add(piece);
                                map.get(PieceType.BISHOP).add(piece);
                                square = new SquareImpl(this, rank, file, piece);
                                break;
                            case D_FILE:
                                piece = new Queen(color);
                                list.add(piece);
                                map.get(PieceType.QUEEN).add(piece);
                                square = new SquareImpl(this, rank, file, piece);
                                break;
                            case E_FILE:
                                piece = new King(color);
                                list.add(piece);
                                map.get(PieceType.KING).add(piece);
                                square = new SquareImpl(this, rank, file, piece);
                                break;
                            default:
                                throw new IllegalStateException("File.values() returned a non-File member!");
                        }   /* switch (file) */
                        break;
                    default:
                        square = new SquareImpl(this, rank, file);
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
        final String WIDE_SPACING = "\t\t";
        final String RANK_SEPARATOR =   SPACING + "  +---+---+---+---+---+---+---+---+\n";
        final String FILE_LABELS    =   SPACING + "    a   b   c   d   e   f   g   h  \n";
        StringBuilder builder = new StringBuilder();
        builder.append(RANK_SEPARATOR);
        String[] ranks = getFEN().getFENString().split("/");
        int i = 8;
        for (String rank : ranks) {
            builder.append(String.format("%s%d |", SPACING, i));
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
            if ((i == 7) && ((capturedWhitePieces.size() > 0) || (capturedBlackPieces.size() > 0)))
                builder.append(String.format("%s[[ Captured Pieces ]]", WIDE_SPACING));
            else if ((i == 6) && (capturedWhitePieces.size() > 0))
                builder.append(String.format("%sW: %s", WIDE_SPACING, StringUtils.joinPieceList(capturedWhitePieces, " ", true)));
            else if ((i == 5) && (capturedBlackPieces.size() > 0))
                builder.append(String.format("%sB: %s", WIDE_SPACING, StringUtils.joinPieceList(capturedBlackPieces, " ", true)));
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
    public List<Piece> getActiveWhitePieces()
    {
        return activeWhitePieces;
    }

    @Override
    public List<Piece> getActiveWhitePieces(PieceType type)
    {
        return getActivePieces(PieceColor.WHITE, type);
    }

    @Override
    public List<Piece> getCapturedWhitePieces()
    {
        return capturedWhitePieces;
    }

    @Override
    public List<Piece> getActiveBlackPieces()
    {
        return activeBlackPieces;
    }

    @Override
    public List<Piece> getActiveBlackPieces(PieceType type)
    {
        return getActivePieces(PieceColor.BLACK, type);
    }

    @Override
    public List<Piece> getCapturedBlackPieces()
    {
        return capturedBlackPieces;
    }

    @Override
    public King getWhiteKing()
    {
        return (King)(whitePieces.get(PieceType.KING).get(0));
    }

    @Override
    public Queen getWhiteQueen()
    {
        if (whitePieces.get(PieceType.QUEEN).size() == 0)
            return null;
        return (Queen)(whitePieces.get(PieceType.QUEEN).get(0));
    }

    @Override
    public King getBlackKing()
    {
        return (King)(blackPieces.get(PieceType.KING).get(0));
    }

    @Override
    public Queen getBlackQueen()
    {
        if (blackPieces.get(PieceType.QUEEN).size() == 0)
            return null;
        return (Queen)(blackPieces.get(PieceType.QUEEN).get(0));
    }

    @Override
    public Square getSquareByName(String name)
    {
        Square square = null;
        if (Pattern.matches("^(?:[a-h])[1-8]$", name)) {
            String whichFile = name.substring(0, 1);
            int whichRank = Integer.parseInt(name.substring(1));
            int offset = File.forName(whichFile).getNumber() - 1;
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
        /*if (!piece.isLegalSquare(dest)) ... do something */
        Piece captured = piece.move(dest);
        if (captured != null) {
            if (captured.getColor() == PieceColor.WHITE) {
                activeWhitePieces.remove(captured);
                capturedWhitePieces.add(captured);
                whitePieces.get(captured.getPieceType()).remove(captured);
            } else {
                activeBlackPieces.remove(captured);
                capturedBlackPieces.add(captured);
                blackPieces.get(captured.getPieceType()).remove(captured);
            }
        }
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

    private List<Piece> getActivePieces(PieceColor color, PieceType type)
    {
        if (color == null)
            return null;
        if (type == null)
            return ((color == PieceColor.WHITE) ? getActiveWhitePieces() : getActiveBlackPieces());
        if ((type == PieceType.KING) || (type == PieceType.QUEEN))
            throw new IllegalArgumentException("getActivePieces() called with King or Queen type argument");
        return ((color == PieceColor.WHITE) ? whitePieces.get(type) : blackPieces.get(type));
    }

}
