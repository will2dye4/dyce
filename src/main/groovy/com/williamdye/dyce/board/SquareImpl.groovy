package com.williamdye.dyce.board

import javax.annotation.Nonnull

import com.williamdye.dyce.pieces.Piece

/**
 * Implementation of the {@link Square} interface.
 *
 * @author William Dye
 */
class SquareImpl implements Square
{
    /** The chessboard to which this square belongs. */
    protected final Chessboard board

    /** This square's rank. */
    protected final Rank rank

    /** This square's file. */
    protected final File file

    /** The piece currently occupying this square, if there is one. */
    protected Piece piece

    /**
     * Construct a {@code SquareImpl} on a certain board at a certain rank and file, occupied by a certain piece.
     *
     * @param chessboard the board to which the square belongs
     * @param rank the square's rank (horizontal row)
     * @param file the square's file (vertical column)
     * @param piece the initial piece for the square
     */
    SquareImpl(final @Nonnull Chessboard chessboard, final @Nonnull Rank rank, final @Nonnull File file, final Piece piece = null)
    {
        this.board = chessboard
        this.rank = rank
        this.file = file
        setPiece(piece)
    }

    @Override
    Chessboard getBoard()
    {
        board
    }

    @Override
    Rank getRank()
    {
        rank
    }

    @Override
    File getFile()
    {
        file
    }

    @Override
    Optional<Piece> getPiece()
    {
        Optional.ofNullable(piece)
    }

    @Override
    void setPiece(final Piece target)
    {
        if (target && !target.square) {
            target.move(this)
        }

        piece = target
    }

    @Override
    String getName()
    {
        file.toString() + rank.toString()
    }

    @Override
    boolean isEmpty()
    {
        !piece
    }

    @Override
    String toString()
    {
        name
    }

}
