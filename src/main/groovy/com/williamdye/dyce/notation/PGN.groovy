package com.williamdye.dyce.notation

import groovy.util.logging.Slf4j
import javax.annotation.Nonnull

import com.williamdye.dyce.board.Chessboard
import com.williamdye.dyce.board.File
import com.williamdye.dyce.board.Rank
import com.williamdye.dyce.board.Square
import com.williamdye.dyce.exception.AmbiguousMoveException
import com.williamdye.dyce.exception.IllegalMoveException
import com.williamdye.dyce.game.MoveType
import com.williamdye.dyce.game.PartialMove
import com.williamdye.dyce.pieces.Piece
import com.williamdye.dyce.pieces.PieceColor
import com.williamdye.dyce.pieces.PieceType

/**
 * The {@code PGN} class is capable of parsing moves in Portable Game Notation (PGN).
 *
 * @author William Dye
 */
@Slf4j
class PGN
{

    /** The PGN representation of castling kingside; those are uppercase letter 'O's, not zeros. */
    public static final String CASTLING_KINGSIDE = "O-O"

    /** The PGN representation of castling queenside. */
    public static final String CASTLING_QUEENSIDE = "O-O-O"

    /** The PGN representation for denoting the end of a game won by Black. */
    public static final String GAME_TERMINATION_BLACK_WINS = "0-1"

    /** The PGN representation for denoting the end of a drawn game. */
    public static final String GAME_TERMINATION_DRAW = "1/2-1/2"

    /** The PGN representation for denoting the end of a game won by White. */
    public static final String GAME_TERMINATION_WHITE_WINS = "1-0"

    /** The chessboard that we will be parsing PGN for. */
    protected final Chessboard chessboard

    /** A list of parsed moves. */
    protected final List<String> moves

    /** The set of tag pairs (key-value pairs) associated with the PGN. */
    protected final Map<String, String> tagPairs

    /**
     * Construct a {@code PGN} that will parse for the specified chessboard.
     *
     * @param board The chessboard that we will be parsing PGN for
     */
    PGN(final @Nonnull Chessboard board)
    {
        this.chessboard = board
        this.moves = new LinkedList<>()
        this.tagPairs = new LinkedHashMap<>()
    }

    /**
     * Try to parse a PGN string into a {@link PartialMove}.
     *
     * @param toMove The currently active color
     * @param move The PGN string to parse
     * @return A {@link PartialMove} representing the PGN string (if the string is valid)
     * @throws IllegalMoveException If the PGN string represents an illegal move
     * @throws AmbiguousMoveException If the PGN string represents an ambiguous move
     */
    PartialMove parseMove(final @Nonnull PieceColor toMove, @Nonnull String move) throws IllegalMoveException, AmbiguousMoveException
    {
        log.debug("Attempting to parse move '$move' for $toMove.name")

        move = move.trim()
        if ([CASTLING_KINGSIDE, CASTLING_QUEENSIDE].contains(move)) {
            return parseCastlingMove(toMove, move)
        }

        move = move.replaceAll("[+#]", "").replaceAll("=[BNQR]", "")
        if (move.length() < 2 || move.length() > 5) {
            throw new IllegalMoveException("Expected move fragment [$move] to look like [Bc3] or [N2d4] or [Rgd6] or [Qc8f5]")
        }

        Square dest = null
        int xIndex = move.indexOf("x")
        if (xIndex > 0) {
            dest = chessboard.getSquareByName(move.substring(xIndex + 1, xIndex + 3))
        } else if (Character.isLowerCase(move.charAt(0))) {
            dest = chessboard.getSquareByName(move.substring(0, 2))
        }

        Character.isLowerCase(move.charAt(0)) ? parsePawnMove(toMove, move, dest) : parsePieceMove(toMove, move, dest)
    }

    /** Helper to parse a PGN string as a castling move. */
    private PartialMove parseCastlingMove(final PieceColor toMove, final String move) throws IllegalMoveException
    {
        final boolean kingside = CASTLING_KINGSIDE == move
        if (chessboard.game.state.castlingAvailability.canCastle(toMove, kingside)) {
            final String destName = (kingside ? "g" : "c") + (PieceColor.WHITE == toMove ? 1 : 8)
            log.debug("Move '$move' parsed as castling move [kingside=$kingside, destination=$destName]")
            return new PartialMove(chessboard.getKing(toMove), chessboard.getSquareByName(destName), MoveType.CASTLING)
        }
        throw new IllegalMoveException("$toMove is not allowed to castle ${kingside ? 'king' : 'queen'}side")
    }

    /** Helper to parse a PGN string as a pawn move. */
    private PartialMove parsePawnMove(final PieceColor toMove, final String move, final Square dest) throws IllegalMoveException
    {
        final String fileString = move.substring(0, 1)
        if (!(fileString ==~ /[a-h]/)) {
            throw new IllegalMoveException("Expected pawn move [$move] to begin with a file name (a-h)")
        }
        final Piece pawn = chessboard.getActivePieces(toMove, PieceType.PAWN).find { piece ->
            piece.square.file == File.forName(fileString) && (piece.isLegalSquare(dest) || piece.isAttacking(dest))
        }
        if (!pawn) {
            throw new IllegalMoveException("Could not find a pawn to move for [$move]")
        }

        final MoveType type = (dest == chessboard.game.state.enPassantTargetSquare) ? MoveType.EN_PASSANT : MoveType.NORMAL
        log.debug("Move '$move' parsed as pawn move [destination=$dest.name, type=${type.toString()}]")
        new PartialMove(pawn, dest, type)
    }

    /** Helper to parse a PGN string as a major piece (i.e., non-pawn) move. */
    private PartialMove parsePieceMove(final PieceColor toMove, final String move, Square dest) throws IllegalMoveException, AmbiguousMoveException
    {
        if (!(move.substring(0, 1) ==~ /[BKNQR]/)) {
            throw new IllegalMoveException("Expected piece move [$move] to start with B, K, N, Q, or R")
        }
        Rank rank = null
        File file = null
        if ((move.contains("x") && move.length() > 4) || move.length() > 3) {
            final String secondChar = move.substring(1, 2)
            if (Character.isDigit(move.charAt(1))) {
                rank = Rank.forNumber(Integer.parseInt(secondChar))
            } else {
                file = File.forName(secondChar)
            }
        }
        if (!dest) {
            if (rank || file) {
                dest = chessboard.getSquareByName(move.substring(2, 4))
            } else {
                dest = chessboard.getSquareByName(move.substring(1, 3))
            }
        }

        final PieceType pieceType = PieceType.forSymbol(move.charAt(0))
        if (PieceType.KING == pieceType) {
            Piece king = chessboard.getKing(toMove)
            if (king.isLegalSquare(dest)) {
                log.debug("Move '$move' parsed as king move [destination=$dest.name]")
                return new PartialMove(king, dest)
            }
            throw new IllegalMoveException("King is not allowed to make move [$move]")
        }

        final List<Piece> candidates = chessboard.getActivePieces(toMove, pieceType).findAll { piece ->
            piece.isLegalSquare(dest) && (!rank || piece.square.rank == rank) && (!file || piece.square.file == file)
        }
        if (!candidates) {
            throw new IllegalMoveException("Could not find a piece to move for [$move]")
        }
        if (candidates.size() > 1) {
            throw new AmbiguousMoveException(candidates)
        }

        log.debug("Move '$move' parsed as normal piece move [type=${pieceType.toString()}, destination=$dest.name]")
        new PartialMove(candidates.first(), dest)
    }

    /**
     * Accessor for the tag pairs defined for the PGN.
     *
     * @return The set of tag pairs (key-value pairs)
     */
    Map<String, String> getTagPairs()
    {
        tagPairs
    }

    /**
     * Retrieve the value of the tag pair with the specified name.
     *
     * @param tagName The name of the tag pair whose value to look up
     * @return The value of the tag pair with the given name
     */
    String getTagValue(final String tagName)
    {
        tagPairs[tagName]
    }

    /**
     * Update the value of the tag pair with the specified name.
     *
     * @param tagName The name of the tag pair whose value to change
     * @param tagValue The new value for the tag pair with the given name
     */
    void setTagValue(final String tagName, final String tagValue)
    {
        tagPairs[tagName] = tagValue
    }

}
