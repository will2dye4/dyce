package com.williamdye.dyce.game;

import com.williamdye.dyce.board.Chessboard;

import java.util.*;

/**
 * @author William Dye
 */
public class MoveHistoryImpl implements MoveHistory
{

    protected final List<Move> history;
    protected final Chessboard chessboard;
    protected int index;

    public MoveHistoryImpl(Chessboard board)
    {
        this.chessboard = board;
        this.history = new ArrayList<Move>();
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
        if (index >= history.size())
            return null;
        return history.get(index);
    }

    @Override
    public Move getNext()
    {
        if (index >= history.size())
            return null;
        return history.get(index++);
    }

    @Override
    public Move peekPrevious()
    {
        if (index == 0)
            return null;
        return history.get(index - 1);
    }

    @Override
    public Move getPrevious()
    {
        if (index == 0)
            return null;
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
        for (Move move : history) {
            builder.append(move.toString());
            builder.append("\n");
        }
        return builder.toString();
    }

}
