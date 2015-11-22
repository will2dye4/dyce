package com.williamdye.dyce.game

import javax.annotation.Nonnull

import com.google.common.base.Preconditions

import com.williamdye.dyce.board.Chessboard

/**
 * Implementation of the {@link MoveHistory} interface.
 *
 * @author William Dye
 */
class MoveHistoryImpl implements MoveHistory
{

    /** The history itself (a list of moves). */
    protected final List<Move> history

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
        index = 0
    }

    @Override
    void fastForward()
    {
        index = history.size()
    }

    @Override
    void add(final Move move)
    {
        history << move
        index = history.size() - 1
    }

    @Override
    void clear()
    {
        history.clear()
        index = 0
    }

    @Override
    String toString()
    {
        history.collect { it.toString() }.join("\n")
    }

}
