package com.williamdye.dyce.game

import groovy.util.logging.Slf4j
import javax.annotation.Nonnull

import com.google.common.base.Preconditions

import com.williamdye.dyce.board.Chessboard
import com.williamdye.dyce.board.File
import com.williamdye.dyce.board.Rank
import com.williamdye.dyce.pieces.Pawn
import com.williamdye.dyce.pieces.Piece
import com.williamdye.dyce.pieces.PieceColor
import com.williamdye.dyce.pieces.PieceType
import com.williamdye.dyce.pieces.PromotedPawn

/**
 * Implementation of the {@link MoveHistory} interface.
 *
 * @author William Dye
 */
@Slf4j
class MoveHistoryImpl implements MoveHistory
{

    /** The history itself (a list of moves). */
    protected final List<Move> history

    /** Stack containing the state of the game after every move. */
    protected final Stack<GameState> states

    /** The chessboard to which the moves belong. */
    protected final Chessboard chessboard

    /** The current index into the history. */
    protected int index

    /**
     * Construct an empty {@code MoveHistoryImpl} for the specified chessboard.
     *
     * @param board The chessboard to which the history's moves will belong
     */
    MoveHistoryImpl(final @Nonnull Chessboard board)
    {
        this.chessboard = board
        this.history = new LinkedList<>()
        this.states = new Stack<>()
        this.states.push(board.game?.state ?: new GameStateImpl())
        this.index = 0
    }

    @Override
    int getSize()
    {
        history.size()
    }

    @Override
    int getIndex()
    {
        index
    }

    @Override
    void setIndex(final int newIndex)
    {
        index = newIndex
    }

    @Override
    boolean hasNext()
    {
        index >= 0 && index < history.size()
    }

    @Override
    boolean hasPrevious()
    {
        index > 0 && index <= history.size()
    }

    @Override
    Move peekNext()
    {
        Preconditions.checkState(this.hasNext(), "'peekNext' called when 'hasNext' is false")

        history.get(index)
    }

    @Override
    Move getNext()
    {
        Preconditions.checkState(this.hasNext(), "'getNext' called when 'hasNext' is false")

        history.get(index++)
    }

    @Override
    Move peekPrevious()
    {
        Preconditions.checkState(this.hasPrevious(), "'peekPrevious' called when 'hasPrevious' is false")

        history.get(index - 1)
    }

    @Override
    Move getPrevious()
    {
        Preconditions.checkState(this.hasPrevious(), "'getPrevious' called when 'hasPrevious' is false")

        history.get(--index)
    }

    @Override
    void rewind()
    {
        while (hasPrevious()) {
            back()
        }
    }

    @Override
    void fastForward()
    {
        while (hasNext()) {
            forward()
        }
    }

    @Override
    void add(final Move move)
    {
        history << move
        if (index == history.size() - 1) {
            states.push(move.state)
        }
        index = history.size()
    }

    @Override
    void clear()
    {
        history.clear()
        states.clear()
        index = 0
    }

    @Override
    void back()
    {
        Preconditions.checkState(this.hasPrevious(), "'back' called when 'hasPrevious' is false")

        Move previousMove = getPrevious()
        log.debug("Undoing move [$previousMove.PGNString] [${previousMove.toString()}]")
        //chessboard.move(previousMove.movedPiece, previousMove.startSquare)
        previousMove.movedPiece.move(previousMove.startSquare)
        if (previousMove.capturedPiece) {
            previousMove.capturedPiece.uncapture()
        }
        if (previousMove.moveType == MoveType.CASTLING) {
            final PieceColor activeColor = previousMove.movedPiece.color
            final int status = activeColor == PieceColor.WHITE ? CastlingAvailability.WHITE_CASTLED_KINGSIDE : CastlingAvailability.BLACK_CASTLED_KINGSIDE
            final boolean kingside = previousMove.state.castlingAvailability.isStatus(status)
            final Piece castledRook = chessboard.getActivePieces(activeColor, PieceType.ROOK).find { it.square.file == (kingside ? File.F_FILE : File.D_FILE) }
            if (castledRook) {
                castledRook.move(
                        chessboard.getSquare(
                                kingside ? File.H_FILE : File.A_FILE,
                                activeColor == PieceColor.WHITE ? Rank.FIRST_RANK : Rank.EIGHTH_RANK
                        )
                )
            } else {
                log.warn("Failed to locate castled rook!")
            }
        }
        if (previousMove.isPawnPromotion()) {
            (previousMove.movedPiece as PromotedPawn).demote()
        }
        states.pop()
        updateState(states.peek())
    }

    @Override
    void back(int n)
    {
        Preconditions.checkArgument(n >= 0, "'n' must be non-negative")

        while (n-- > 0 && hasPrevious()) {
            back()
        }
    }

    @Override
    void forward()
    {
        Preconditions.checkState(this.hasNext(), "'forward' called when 'hasNext' is false")

        Move nextMove = getNext()
        log.debug("Redoing move [$nextMove.PGNString] [${nextMove.toString()}]")
        chessboard.move(nextMove.movedPiece, nextMove.endSquare, nextMove.moveType, null, false)
        states.push(nextMove.state)
        updateState(nextMove.state)
    }

    @Override
    void forward(int n)
    {
        Preconditions.checkArgument(n >= 0, "'n' must be non-negative")

        while (n-- > 0 && hasNext()) {
            forward()
        }
    }

    private void updateState(final GameState state)
    {
        chessboard.game.state.activeColor = state.activeColor
        chessboard.game.state.moveCount = state.moveCount
        chessboard.game.state.enPassantTargetSquare = state.enPassantTargetSquare
        chessboard.game.state.halfMoveClock = state.halfMoveClock
        chessboard.game.state.halfMoveTotal = state.halfMoveTotal
        chessboard.game.state.castlingAvailability.state = state.castlingAvailability.state
    }

    @Override
    String toString()
    {
        history.collect { it.toString() }.join("\n")
    }

}
