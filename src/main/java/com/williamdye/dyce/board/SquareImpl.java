package com.williamdye.dyce.board;

import com.williamdye.dyce.pieces.Piece;

/**
 * Implementation of the <code>Square</code> interface.
 * @author William Dye
 */
public class SquareImpl implements Square
{
    /** The chessboard to which this square belongs. */
    protected final Chessboard board;
    /** This square's rank. */
    protected final Rank rank;
    /** This square's file. */
    protected final File file;
    /** The piece currently occupying this square, if there is one. */
    protected Piece piece;

    /**
     * Construct a <code>SquareImpl</code> on a certain board at a certain rank and file.
     * @param board the board to which the square belongs
     * @param rank the square's rank (horizontal row)
     * @param file the square's file (vertical column)
     */
    public SquareImpl(Chessboard board, Rank rank, File file)
    {
        this(board, rank, file, null);
    }

    /**
     * Construct a <code>SquareImpl</code> on a certain board at a certain rank and file, occupied by a certain piece.
     * @param chessboard the board to which the square belongs
     * @param r the square's rank (horizontal row)
     * @param f the square's file (vertical column)
     * @param p the initial piece for the square
     */
    public SquareImpl(Chessboard chessboard, Rank r, File f, Piece p)
    {
        this.board = chessboard;
        this.rank = r;
        this.file = f;
        setPiece(p);
    }

    @Override
    public Chessboard getBoard()
    {
        return board;
    }

    @Override
    public Rank getRank()
    {
        return rank;
    }

    @Override
    public File getFile()
    {
        return file;
    }

    @Override
    public Piece getPiece()
    {
        return piece;
    }

    @Override
    public void setPiece(final Piece target)
    {
        if ((target != null) && (target.getSquare() == null))
            target.move(this);
        piece = target;
    }

    @Override
    public String getName()
    {
        return file.toString() + rank.toString();
    }

    @Override
    public boolean isEmpty()
    {
        return (piece == null);
    }

}
