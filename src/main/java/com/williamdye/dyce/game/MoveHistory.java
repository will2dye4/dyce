package com.williamdye.dyce.game;

/**
 * @author William Dye
 */
public interface MoveHistory
{

    public int getSize();

    public int getIndex();

    public void setIndex(int newIndex);

    public boolean hasNext();

    public boolean hasPrevious();

    public Move peekNext();

    public Move getNext();

    public Move peekPrevious();

    public Move getPrevious();

    public void rewind();

    public void fastForward();

    public void add(Move move);

    public void clear();

}
