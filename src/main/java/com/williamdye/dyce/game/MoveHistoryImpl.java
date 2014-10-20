package com.williamdye.dyce.game;

import java.util.*;

import com.google.common.base.Preconditions;

import com.williamdye.dyce.board.Chessboard;

/**
 * Implementation of the {@link MoveHistory} interface.
 *
 * @author William Dye
 */
public class MoveHistoryImpl implements MoveHistory
{

    /** The history itself (a list of moves). */
    protected final List<Move> history;

    /** The chessboard to which the moves belong. */
    protected final Chessboard chessboard;

    /** The current index into the history. */
    protected int index;

    /**
     * Construct an empty {@code MoveHistoryImpl} for the specified chessboard.
     *
     * @param board The chessboard to which the history's moves will belong
     */
    public MoveHistoryImpl(Chessboard board)
    {
        Preconditions.checkNotNull(board, "'board' may not be null when creating move history");

        this.chessboard = board;
        this.history = new ArrayList<>();
        this.index = 0;
    }

    @Override
    public int getSize()
    {
        return history.size();
    }

    @Override
    public int getIndex()
    {
        return index;
    }

    @Override
    public void setIndex(final int newIndex)
    {
        index = newIndex;
    }

    @Override
    public boolean hasNext()
    {
        return ((index >= 0) && (index < history.size()));
    }

    @Override
    public boolean hasPrevious()
    {
        return ((index > 0) && (index <= history.size()));
    }

    @Override
    public Move peekNext()
    {
        Preconditions.checkState(this.hasNext(), "'peekNext' called when 'hasNext' is false");

        return history.get(index);
    }

    @Override
    public Move getNext()
    {
        Preconditions.checkState(this.hasNext(), "'getNext' called when 'hasNext' is false");

        return history.get(index++);
    }

    @Override
    public Move peekPrevious()
    {
        Preconditions.checkState(this.hasPrevious(), "'peekPrevious' called when 'hasPrevious' is false");

        return history.get(index - 1);
    }

    @Override
    public Move getPrevious()
    {
        Preconditions.checkState(this.hasPrevious(), "'getPrevious' called when 'hasPrevious' is false");

        return history.get(--index);
    }

    @Override
    public void rewind()
    {
        index = 0;
    }

    @Override
    public void fastForward()
    {
        index = history.size();
    }

    @Override
    public void add(final Move move)
    {
        history.add(move);
        index = history.size() - 1;
    }

    @Override
    public void clear()
    {
        history.clear();
        index = 0;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        history.stream().forEach(move -> builder.append(move.toString()).append("\n"));
        return builder.toString();
    }

}
