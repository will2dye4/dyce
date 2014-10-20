package com.williamdye.dyce.game;

/**
 * A move history represents a set of moves in a sequence, as in a chess game in progress (or a completed game).
 *
 * @author William Dye
 */
public interface MoveHistory
{

    /**
     * Accessor for the number of moves in the history.
     *
     * @return The size of the history (number of moves)
     */
    public int getSize();

    /**
     * Accessor for the current index into the history.
     *
     * @return The current history index
     */
    public int getIndex();

    /**
     * Mutator for the index into the history.
     *
     * @param newIndex The new history index
     */
    public void setIndex(int newIndex);

    /**
     * Check whether the history has a next (subsequent) move.
     *
     * @return {@code true} if there is at least one more move, {@code false} otherwise
     */
    public boolean hasNext();

    /**
     * Check whether the history has a previous move.
     *
     * @return {@code true} if there is at least one prior move, {@code false} otherwise
     */
    public boolean hasPrevious();

    /**
     * Peek at the next move in the history without updating the current index.
     *
     * @return The next move in the history
     */
    public Move peekNext();

    /**
     * Retrieve the next move in the history and increment the current index.
     *
     * @return The next move in the history
     */
    public Move getNext();

    /**
     * Peek at the previous move in the history without updating the current index.
     *
     * @return The previous move in the history
     */
    public Move peekPrevious();

    /**
     * Retrieve the next move in the history and decrement the current index.
     *
     * @return The previous move in the history
     */
    public Move getPrevious();

    /**
     * Rewind the history back to the beginning.
     */
    public void rewind();

    /**
     * Fast forward the history to the end.
     */
    public void fastForward();

    /**
     * Insert a new move to the end of the history.
     *
     * @param move The move to add
     */
    public void add(Move move);

    /**
     * Clear the history, i.e., reset it to a clean and empty state.
     */
    public void clear();

}
