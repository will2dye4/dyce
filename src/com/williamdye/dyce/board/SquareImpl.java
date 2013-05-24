package com.williamdye.dyce.board;

import com.williamdye.dyce.pieces.Piece;

/**
 * @author William Dye
 */
public class SquareImpl implements Square
{
    protected Rank rank;
    protected File file;
    protected Piece piece;

    public SquareImpl(Rank r, File f)
    {
        this(r, f, null);
    }

    public SquareImpl(Rank r, File f, Piece p)
    {
        this.rank = r;
        this.file = f;
        setPiece(p);
    }

    public static int getRankDistance(Square start, Square end)
    {
        if ((start == null) || (end == null))
            return -1;
        return Math.abs(start.getRank().getNumber() - end.getRank().getNumber());
    }

    public static int getFileDistance(Square start, Square end)
    {
        if ((start == null) || (end == null))
            return -1;
        return Math.abs(start.getFile().getNumber() - end.getFile().getNumber());
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
    public void setPiece(Piece target)
    {
        piece = target;
        if ((piece != null) && (piece.getSquare() == null))
            piece.move(this);
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
