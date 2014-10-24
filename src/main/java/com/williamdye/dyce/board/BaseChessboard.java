package com.williamdye.dyce.board;

import java.util.*;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;
import com.williamdye.dyce.util.ChessboardUtils;
import org.slf4j.*;

import com.williamdye.dyce.board.formatter.*;
import com.williamdye.dyce.exception.*;
import com.williamdye.dyce.game.*;
import com.williamdye.dyce.notation.*;
import com.williamdye.dyce.pieces.*;

/**
 * Abstract implementation of the {@link Chessboard} interface.
 *
 * @author William Dye
 */
public abstract class BaseChessboard implements Chessboard
{

    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(BaseChessboard.class);

    /** The number of ranks (horizontal rows) on the chessboard. */
    public static final int NUM_RANKS = 8;

    /** The number of files (vertical columns) on the chessboard. */
    public static final int NUM_FILES = 8;

    /** The number of squares on the chessboard (the product of the numbers of files and ranks). */
    public static final int NUM_SQUARES = NUM_RANKS * NUM_FILES;

    /** A {@code Pattern} representing the expected format for square names (e.g., "e4"). */
    protected static final Pattern SQUARE_NAME_PATTERN = Pattern.compile("^[a-h][1-8]$");

    /** The Forsyth-Edwards Notation (FEN) of the current position. */
    protected final FEN fen;

    /** The Portable Game Notation (PGN) object for this chessboard. */
    protected final PGN pgn;

    /** A one-dimensional array of squares which comprise the board itself.
     * The board is represented as follows:
     * [a1, b1, c1, d1, e1, f1, g1, h1,
     *  a2, b2, c2, d2, e2, f2, g2, h2,
     *  ...,
     *  a8, b8, c8, d8, e8, f8, g8, h8]
     */
    protected final Square[] squares;

    /** A map relating piece types to lists of active white pieces. */
    protected final Map<PieceType, List<Piece>> whitePieces;

    /** A list of all active white pieces. */
    protected final List<Piece> activeWhitePieces;

    /** A list of all captured white pieces. */
    protected final List<Piece> capturedWhitePieces;

    /** A map relating piece types to lists of active black pieces. */
    protected final Map<PieceType, List<Piece>> blackPieces;

    /** A list of all active black pieces. */
    protected final List<Piece> activeBlackPieces;

    /** A list of all captured black pieces. */
    protected final List<Piece> capturedBlackPieces;

    /** The game's current state. */
    protected final GameState state;

    /** A history of all moves in the game. */
    protected final MoveHistory history;

    /**
     * Construct a {@code BaseChessboard} with no pieces.
     */
    protected BaseChessboard()
    {
        this.fen = new FEN(this);
        this.pgn = new PGN(this);
        this.squares = new Square[NUM_SQUARES];
        this.whitePieces = new LinkedHashMap<>();
        this.activeWhitePieces = new ArrayList<>();
        this.capturedWhitePieces = new ArrayList<>();
        this.blackPieces = new LinkedHashMap<>();
        this.activeBlackPieces = new ArrayList<>();
        this.capturedBlackPieces = new ArrayList<>();
        this.state = new GameStateImpl();
        this.history = new MoveHistoryImpl(this);
        initializePieceMaps();
        createSquares();
    }

    private void initializePieceMaps() {
        for (PieceType pieceType : PieceType.values()) {
            whitePieces.put(pieceType, new ArrayList<>());
            blackPieces.put(pieceType, new ArrayList<>());
        }
    }

    private void createSquares() {
        int i = 0;
        for (Rank rank : Rank.values()) {
            for (File file : File.values()) {
                squares[i++] = new SquareImpl(this, rank, file);
            }
        }
    }

    @Override
    public FEN getFEN()
    {
        return fen;
    }

    @Override
    public String prettyPrint()
    {
        return DefaultChessboardFormatter.getInstance().format(this);
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
    public List<Piece> getActivePieces(final PieceColor color)
    {
        Preconditions.checkNotNull(color, "'color' may not be null when getting active pieces");

        return ((color == PieceColor.WHITE) ? activeWhitePieces : activeBlackPieces);
    }

    @Override
    public List<Piece> getActivePieces(final PieceColor color, final PieceType type)
    {
        Preconditions.checkNotNull(color, "'color' may not be null when getting active pieces");
        Preconditions.checkNotNull(type, "Use BaseChessboard#getActivePieces(PieceColor) instead");
        Preconditions.checkArgument(type != PieceType.KING, "Use BaseChessboard#getKing(PieceColor) instead");

        return ((color == PieceColor.WHITE) ? whitePieces.get(type) : blackPieces.get(type));
    }

    @Override
    public List<Piece> getCapturedPieces(final PieceColor color)
    {
        Preconditions.checkNotNull(color, "'color' may not be null when getting captured pieces");

        return ((color == PieceColor.WHITE) ? capturedWhitePieces : capturedBlackPieces);
    }

    @Override
    public King getKing(final PieceColor color)
    {
        Preconditions.checkNotNull(color, "'color' may not be null when getting the king");

        Map<PieceType, List<Piece>> map = (color == PieceColor.WHITE ? whitePieces : blackPieces);
        return (King)(map.get(PieceType.KING).get(0));
    }

    @Override
    public Square getSquare(final File file, final Rank rank)
    {
        return getSquareByName(String.format("%s%d", file.toString(), rank.getNumber()));
    }

    @Override
    public Square getSquareByName(final String name)
    {
        Preconditions.checkArgument(SQUARE_NAME_PATTERN.matcher(name).matches(), "Invalid square name");

        String whichFile = name.substring(0, 1);
        int whichRank = Integer.parseInt(name.substring(1));
        int i = ChessboardUtils.getBoardIndex(whichRank, File.forName(whichFile).getNumber());
        return squares[i];
    }

    @Override
    public void move(final Piece piece, final Square dest) throws IllegalMoveException
    {
        move(piece, dest, null);
    }

    @Override
    public void move(final String pgnString) throws AmbiguousMoveException, IllegalMoveException
    {
        PartialMove partial = pgn.parseMove(state.getActiveColor(), pgnString);
        logger.info("{} played {}", state.getActiveColor().getName(), pgnString);
        move(partial.getMovedPiece(), partial.getEndSquare(), partial.getMoveType());
    }

    /* TODO:
     *   - check moveType for checkmate
     *   - check halfMoveClock
     */
    private void move(final Piece piece, final Square dest, MoveType moveType) throws IllegalMoveException
    {
        Preconditions.checkNotNull(piece, "'piece' may not be null when making a move");
        Preconditions.checkNotNull(dest, "'dest' may not be null when making a move");

        if (!piece.isLegalSquare(dest))
            throw new IllegalMoveException();

        handleCastling(piece, dest, moveType);
        handleEnPassant(piece, dest, moveType);

        Optional<Piece> capturedPiece = piece.move(dest);
        if (capturedPiece.isPresent())
            capture(capturedPiece.get());

        if (capturedPiece.isPresent() || piece.getPieceType() == PieceType.PAWN)
            state.resetHalfMoveClock();
        else
            state.incrementHalfMoveClock();

        history.add(new MoveImpl(piece, capturedPiece.orElse(null), piece.getLastSquare(), dest, null, state.getMoveCount()));
        state.incrementHalfMoveTotal();
        state.toggleActiveColor();
    }

    /* TODO: cannot castle into, out of, or through check */
    private void handleCastling(final Piece piece, final Square dest, final MoveType moveType) throws IllegalMoveException
    {
        final CastlingAvailability castling = state.getCastlingAvailability();
        final PieceColor color = piece.getColor();
        boolean castlingKingside = dest.getFile().isKingside();

        if (MoveType.CASTLING == moveType) {
            if (castling.canCastle(color, castlingKingside)) {
                castling.castle(color, castlingKingside);
                final Rank startingRank = Rank.getStartingRank(color);
                final Piece rook = getSquare((castlingKingside ? File.H_FILE : File.A_FILE), startingRank).getPiece().get();
                rook.move(getSquare((castlingKingside ? File.F_FILE : File.D_FILE), startingRank));
            } else
                throw new IllegalMoveException();
        } else if (piece.getLastSquare() == null) {
            if (PieceType.KING == piece.getPieceType())
                castling.revokeCastlingRights(color);
            else if (PieceType.ROOK == piece.getPieceType() && castling.canCastle(color, piece.getSquare().getFile().isKingside()))
                castling.revokeCastlingRights(color, piece.getSquare().getFile().isKingside());
        }
    }

    private void handleEnPassant(final Piece piece, final Square dest, final MoveType moveType)
    {
        final int offset = (piece.getColor() == PieceColor.WHITE ? -1 : 1);
        Square newEnPassantTarget = null;

        if (PieceType.PAWN == piece.getPieceType() && piece.getLastSquare() == null && Paths.getRankDistance(piece.getSquare(), dest) == 2) {
            newEnPassantTarget = getSquare(dest.getFile(), Rank.forNumber(dest.getRank().getNumber() + offset));
            logger.info("Updating en passant target square to {}", newEnPassantTarget.getName());
        } else if (MoveType.EN_PASSANT == moveType) {
            Square captureSquare = getSquare(dest.getFile(), Rank.forNumber(dest.getRank().getNumber() + offset));
            Piece pawn = captureSquare.getPiece().get();
            pawn.capture();
            capture(pawn);
            captureSquare.setPiece(null);
        }

        state.setEnPassantTargetSquare(newEnPassantTarget);
    }

    private void capture(Piece piece)
    {
        Preconditions.checkNotNull(piece, "'piece' may not be null when capturing");

        PieceColor color = piece.getColor();
        getActivePieces(color).remove(piece);
        getCapturedPieces(color).add(piece);
        getActivePieces(color, piece.getPieceType()).remove(piece);
    }

}
