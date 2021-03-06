package com.williamdye.dyce.board

import groovy.util.logging.Slf4j
import java.util.regex.Pattern
import javax.annotation.Nonnull

import com.google.common.base.Preconditions

import com.williamdye.dyce.board.formatter.DefaultChessboardFormatter
import com.williamdye.dyce.exception.AmbiguousMoveException
import com.williamdye.dyce.exception.IllegalMoveException
import com.williamdye.dyce.game.CastlingAvailability
import com.williamdye.dyce.game.Game
import com.williamdye.dyce.game.GameEndingType
import com.williamdye.dyce.game.GameState
import com.williamdye.dyce.game.MoveImpl
import com.williamdye.dyce.game.MoveType
import com.williamdye.dyce.game.PartialMove
import com.williamdye.dyce.pieces.King
import com.williamdye.dyce.pieces.Pawn
import com.williamdye.dyce.pieces.Piece
import com.williamdye.dyce.pieces.PieceColor
import com.williamdye.dyce.pieces.PieceFactory
import com.williamdye.dyce.pieces.PieceFactoryImpl
import com.williamdye.dyce.pieces.PieceType
import com.williamdye.dyce.pieces.PromotedPawn
import com.williamdye.dyce.util.ChessboardUtils

/**
 * Abstract implementation of the {@link Chessboard} interface.
 *
 * @author William Dye
 */
@Slf4j
abstract class BaseChessboard implements Chessboard
{

    /** The number of ranks (horizontal rows) on the chessboard. */
    public static final int NUM_RANKS = 8

    /** The number of files (vertical columns) on the chessboard. */
    public static final int NUM_FILES = 8

    /** The number of squares on the chessboard (the product of the numbers of files and ranks). */
    public static final int NUM_SQUARES = NUM_RANKS * NUM_FILES

    /** A {@code Pattern} representing the expected format for square names (e.g., "e4"). */
    protected static final Pattern SQUARE_NAME_PATTERN = ~/^[a-h][1-8]$/

    /** The game to which this chessboard belongs. */
    protected Game game

    /** The game's current state. */
    protected GameState state

    /** A one-dimensional array of squares which comprise the board itself.
     * The board is represented as follows:
     * [a1, b1, c1, d1, e1, f1, g1, h1,
     *  a2, b2, c2, d2, e2, f2, g2, h2,
     *  ...,
     *  a8, b8, c8, d8, e8, f8, g8, h8]
     */
    protected final Square[] squares

    /** A map relating piece types to lists of active white pieces. */
    protected final Map<PieceType, List<Piece>> whitePieces

    /** A list of all active white pieces. */
    protected final List<Piece> activeWhitePieces

    /** A list of all captured white pieces. */
    protected final List<Piece> capturedWhitePieces

    /** A map relating piece types to lists of active black pieces. */
    protected final Map<PieceType, List<Piece>> blackPieces

    /** A list of all active black pieces. */
    protected final List<Piece> activeBlackPieces

    /** A list of all captured black pieces. */
    protected final List<Piece> capturedBlackPieces

    /** A piece factory (for creating the board's pieces). */
    protected final PieceFactory pieceFactory

    /**
     * Construct a {@code BaseChessboard} with no pieces.
     */
    protected BaseChessboard() {
        this.squares = new Square[NUM_SQUARES]
        this.whitePieces = new LinkedHashMap<>().withDefault { k -> new LinkedList<>() }
        this.activeWhitePieces = new LinkedList<>()
        this.capturedWhitePieces = new LinkedList<>()
        this.blackPieces = new LinkedHashMap<>().withDefault { k -> new LinkedList<>() }
        this.activeBlackPieces = new LinkedList<>()
        this.capturedBlackPieces = new LinkedList<>()
        this.pieceFactory = new PieceFactoryImpl()
        createSquares()
    }

    /** Initialize the internal array of squares. */
    private void createSquares() {
        int i = 0
        Rank.values().each { rank ->
            File.values().each { file ->
                squares[i++] = new SquareImpl(this, rank, file)
            }
        }
    }

    /** Update the appropriate list and map of pieces with a newly created piece. */
    protected void updatePieceLists(final Piece piece)
    {
        if (piece.color == PieceColor.WHITE) {
            activeWhitePieces << piece
            whitePieces[piece.pieceType] << piece
        } else {
            activeBlackPieces << piece
            blackPieces[piece.pieceType] << piece
        }
    }

    @Override
    public Game getGame()
    {
        game
    }

    @Override
    public String prettyPrint()
    {
        DefaultChessboardFormatter.instance.format(this)
    }

    @Override
    public Square[] getBoard()
    {
        squares
    }

    @Override
    public List<Piece> getActivePieces(final @Nonnull PieceColor color)
    {
        (color == PieceColor.WHITE) ? activeWhitePieces : activeBlackPieces
    }

    @Override
    public List<Piece> getActivePieces(final @Nonnull PieceColor color, final @Nonnull PieceType type)
    {
        Preconditions.checkArgument(type != PieceType.KING, "Use BaseChessboard#getKing(PieceColor) instead")

        (color == PieceColor.WHITE) ? whitePieces[type] : blackPieces[type]
    }

    @Override
    public List<Piece> getCapturedPieces(final @Nonnull PieceColor color)
    {
        (color == PieceColor.WHITE) ? capturedWhitePieces : capturedBlackPieces
    }

    @Override
    public King getKing(final @Nonnull PieceColor color)
    {
        final Map<PieceType, List<Piece>> map = (color == PieceColor.WHITE ? whitePieces : blackPieces)
        (King) (map[PieceType.KING].first())
    }

    @Override
    public Square getSquare(final @Nonnull com.williamdye.dyce.board.File file, final @Nonnull Rank rank)
    {
        getSquareByName("${file.toString()}$rank.number")
    }

    @Override
    public Square getSquareByName(final @Nonnull String name)
    {
        Preconditions.checkArgument(name ==~ SQUARE_NAME_PATTERN, "Invalid square name")

        final String whichFile = name.substring(0, 1)
        final int whichRank = name.substring(1).toInteger()
        final int i = ChessboardUtils.getBoardIndex(whichRank, File.forName(whichFile).number)
        squares[i]
    }

    @Override
    public void move(final @Nonnull Piece piece, final @Nonnull Square dest) throws IllegalMoveException
    {
        move(piece, dest, null)
    }

    @Override
    public void move(final @Nonnull String pgnString) throws AmbiguousMoveException, IllegalMoveException
    {
        PartialMove partial = game.PGN.parseMove(state.activeColor, pgnString)
        log.info("${state.activeColor.name} played $pgnString")
        move(partial.movedPiece, partial.endSquare, partial.moveType, pgnString)
    }

    protected void move(@Nonnull Piece piece, final @Nonnull Square dest, MoveType moveType) throws IllegalMoveException
    {
        move(piece, dest, moveType, null)
    }

    protected void move(@Nonnull Piece piece, final @Nonnull Square dest, MoveType moveType, final String pgn) throws IllegalMoveException
    {
        move(piece, dest, moveType, pgn, true)
    }

    @Override
    public void move(@Nonnull Piece piece, final @Nonnull Square dest, MoveType moveType, final String pgn, final boolean updateHistory) throws IllegalMoveException
    {
        if (!piece.isLegalSquare(dest)) {
            throw new IllegalMoveException("Piece [$piece on $piece.square] is not allowed to move to square [$dest]")
        }

        if (isIgnoringCheck(piece, dest)) {
            throw new IllegalMoveException("${state.activeColor.name} must move out of check!")
        }

        // These two checks should be covered by the check for piece.isLegalSquare above - remove them?
        if (isMovingIntoCheck(piece, dest)) {
            throw new IllegalMoveException("${state.activeColor.name} may not move into check!")
        }

        if (moveWouldResultInCheck(piece, dest)) {
            throw new IllegalMoveException("Move would result in check for ${state.activeColor.name}!")
        }

        handleCastling(piece, dest, moveType)
        Piece pawnCapturedEnPassant = handleEnPassant(piece, dest, moveType)
        Piece newPiece = handlePawnPromotion(piece, dest)

        Optional<Piece> capturedPiece = newPiece.move(dest)
        capturedPiece.ifPresent { capture(it) }

        if (capturedPiece.present || newPiece.pieceType == PieceType.PAWN) {
            state.resetHalfMoveClock()
        } else {
            state.incrementHalfMoveClock()
        }

        final PieceColor opposingColor = ~state.activeColor
        final King opposingKing = getKing(opposingColor)
        if (opposingKing.isInCheck()) {
            log.debug("{} king is in check", opposingColor.name)
            Map<Piece, List<Square>> moves = getActivePieces(opposingColor).collectEntries { [it, it.legalSquares] }
            if (!moves.any { it.value.any { sq -> !new ExploratoryChessboard(this).then(it.key, sq).getKing(opposingColor).isInCheck() } }) {
                log.info("Checkmate for {}", state.activeColor.name)
                moveType = MoveType.CHECKMATE
                game.finishGame(GameEndingType.CHECKMATE)
            }
        } else if (!getActivePieces(opposingColor)*.legalSquares.flatten()) {
            log.info("Stalemate - no moves for {}", opposingColor.name)
            game.finishGame(GameEndingType.STALEMATE)
        }

        state.toggleActiveColor()
        if (updateHistory) {
            final boolean isPawnPromotion = newPiece instanceof PromotedPawn
            final Square start = (isPawnPromotion ? (newPiece as PromotedPawn).pawn.lastSquare : newPiece.lastSquare)
            game.moveHistory.add(new MoveImpl(newPiece, capturedPiece.orElse(pawnCapturedEnPassant), start, dest, moveType, isPawnPromotion, pgn, state.moveCount))
        }

        state.incrementHalfMoveTotal()
        if (state.activeColor == PieceColor.WHITE && state.halfMoveTotal > 0) {
            state.incrementMoveCount()
        }
    }

    private boolean isIgnoringCheck(final Piece piece, final Square dest)
    {
        getKing(state.activeColor).isInCheck() && new ExploratoryChessboard(this).then(piece, dest).getKing(state.activeColor).isInCheck()
    }

    private boolean isMovingIntoCheck(final Piece piece, final Square dest)
    {
        piece.pieceType == PieceType.KING && getActivePieces(~state.activeColor).any { it.isAttacking(dest) }
    }

    private boolean moveWouldResultInCheck(final Piece piece, final Square dest)
    {
        new ExploratoryChessboard(this).then(piece, dest).getKing(state.activeColor).isInCheck()
    }

    private void handleCastling(final Piece piece, final Square dest, final MoveType moveType) throws IllegalMoveException
    {
        final CastlingAvailability castling = state.castlingAvailability
        final PieceColor color = piece.color
        final boolean castlingKingside = dest.file.kingside

        if (MoveType.CASTLING == moveType) {
            if (castling.canCastle(color, castlingKingside) && !isAvoidingCheck(piece, dest)) {
                castling.castle(color, castlingKingside)
                final Rank startingRank = Rank.getStartingRank(color)
                final Piece rook = getSquare((castlingKingside ? File.H_FILE : File.A_FILE), startingRank).piece.get()
                rook.move(getSquare((castlingKingside ? File.F_FILE : File.D_FILE), startingRank))
            } else {
                throw new IllegalMoveException("$color is not allowed to castle ${castlingKingside ? 'king' : 'queen'}side")
            }
        } else if (!piece.lastSquare) {
            if (PieceType.KING == piece.pieceType) {
                castling.revokeCastlingRights(color)
            } else if (PieceType.ROOK == piece.pieceType && castling.canCastle(color, piece.square.file.kingside)) {
                castling.revokeCastlingRights(color, piece.square.file.kingside)
            }
        }
    }

    /* Cannot castle into, out of, or through check */
    private boolean isAvoidingCheck(final Piece king, final Square dest)
    {
        getActivePieces(~king.color).any { piece ->
            [king.square, dest, getSquare(dest.file.kingside ? File.F_FILE : File.D_FILE, king.square.rank)].any { square ->
                piece.isAttacking(square, true)
            }
        }
    }

    private Piece handleEnPassant(final Piece piece, final Square dest, final MoveType moveType)
    {
        final int offset = (piece.color == PieceColor.WHITE) ? -1 : 1
        Square newEnPassantTarget = null
        Piece capturedPawn = null

        if (PieceType.PAWN == piece.pieceType && !piece.lastSquare && Paths.getRankDistance(piece.square, dest) == 2) {
            newEnPassantTarget = getSquare(dest.file, Rank.forNumber(dest.rank.number + offset))
            log.info("Updating en passant target square to $newEnPassantTarget.name")
        } else if (MoveType.EN_PASSANT == moveType) {
            final Square captureSquare = getSquare(dest.file, Rank.forNumber(dest.rank.number + offset))
            capturedPawn = captureSquare.piece.get()
            capturedPawn.capture()
            capture(capturedPawn)
            captureSquare.piece = null
        }

        state.setEnPassantTargetSquare(newEnPassantTarget)

        capturedPawn
    }

    private static Piece handlePawnPromotion(Piece piece, final Square dest) throws IllegalMoveException
    {
        final Rank promotingRank = piece.color == PieceColor.WHITE ? Rank.EIGHTH_RANK : Rank.FIRST_RANK
        Piece newPiece = piece
        if (dest.rank == promotingRank) {
            if (piece instanceof Pawn) {
                log.debug("Pawn must be promoted - assuming promotion to queen")
                piece = new PromotedPawn(piece as Pawn, PieceType.QUEEN)
            }
            if (piece instanceof PromotedPawn) {
                log.info("Promoting ${piece.color.name} pawn to ${piece.promotedPiece.pieceType.toString()} on $dest")
                (piece as PromotedPawn).promote()
            }
        }
        newPiece
    }

    /** Capture the specified piece, updating the internal data structures appropriately. */
    protected void capture(final @Nonnull Piece piece)
    {
        final PieceColor color = piece.color
        getActivePieces(color).remove(piece)
        getCapturedPieces(color) << piece
        getActivePieces(color, piece.pieceType).remove(piece)
    }

    protected void setGame(final Game game)
    {
        this.game = game
        this.state = game.state
    }

}
