package com.williamdye.dyce.board;

import com.williamdye.dyce.game.GameState;
import com.williamdye.dyce.game.GameStateImpl;
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
    protected final GameState state;

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
        this.state = new GameStateImpl();
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
    public GameState getGameState()
    {
        return state;
    }

    @Override
    public List<Piece> getActivePieces(PieceColor color)
    {
        if (color == null)
            return null;
        return ((color == PieceColor.WHITE) ? activeWhitePieces : activeBlackPieces);
    }

    @Override
    public List<Piece> getActivePieces(PieceColor color, PieceType type)
    {
        if (color == null)
            return null;
        if (type == null)
            return getActivePieces(color);
        if ((type == PieceType.KING) || (type == PieceType.QUEEN))
            throw new IllegalArgumentException("getActivePieces() called with King or Queen type argument");
        return ((color == PieceColor.WHITE) ? whitePieces.get(type) : blackPieces.get(type));
    }

    @Override
    public List<Piece> getCapturedPieces(PieceColor color)
    {
        if (color == null)
            return null;
        return ((color == PieceColor.WHITE) ? capturedWhitePieces : capturedBlackPieces);
    }

    @Override
    public King getKing(PieceColor color)
    {
        if (color == null)
            return null;
        King king;
        if (color == PieceColor.WHITE)
            king = (King)(whitePieces.get(PieceType.KING).get(0));
        else
            king = (King)(blackPieces.get(PieceType.KING).get(0));
        return king;
    }

    @Override
    public Queen getQueen(PieceColor color)
    {
        if (color == null)
            return null;
        Queen queen;
        PieceType queenType = PieceType.QUEEN;
        if (color == PieceColor.WHITE) {
            if (whitePieces.get(queenType).size() == 0)
                queen = null;
            else
                queen = (Queen)(whitePieces.get(queenType).get(0));
        } else {
            if (blackPieces.get(queenType).size() == 0)
                queen = null;
            else
                queen = (Queen)(blackPieces.get(queenType).get(0));
        }
        return queen;
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
    /* TODO:
     *   - validate move
     *   - update halfMoveClock
     *   - update castling (including on a normal king or rook move)
     *   - update e.p. target
     */
    public void move(Piece piece, Square dest)
    {
        /*if (!piece.isLegalSquare(dest)) ... do something */
        Piece captured = piece.move(dest);
        if (captured != null) {
            PieceColor color = captured.getColor();
            getActivePieces(color).remove(captured);
            getCapturedPieces(color).add(captured);
            getActivePieces(color, captured.getPieceType()).remove(captured);
        }
        state.incrementHalfMoveTotal();
        state.toggleActiveColor();
    }

    @Override
    public void move(String from, String to)
    {
        move(getSquareByName(from).getPiece(), getSquareByName(to));
    }

}
