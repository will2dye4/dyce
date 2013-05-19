package com.williamdye.dyce;

import com.williamdye.dyce.board.ChessboardImpl;
import com.williamdye.dyce.board.Square;

import java.util.regex.Pattern;

/**
 * @author William Dye
 */
public class FEN
{
    protected String string;
    protected Square[] squares;

    public FEN(String fen)
    {
        this(fen, null);
    }

    public FEN(Square[] board)
    {
        this(null, board);
    }

    public FEN(String fen, Square[] board)
    {
        if ((fen == null) && (board == null))
            throw new IllegalArgumentException("A string or an array of squares must be provided to instantiate FEN!");
        if ((board != null) && (board.length != ChessboardImpl.BOARD_SIZE))
            throw new IllegalArgumentException("Square array must be of size " + ChessboardImpl.BOARD_SIZE + "!");
        if ((fen != null) && !(isValidFEN(fen)))
            throw new IllegalArgumentException("Invalid FEN provided!");
        this.string = fen;
        this.squares = board;
    }

    public static boolean isValidFEN(String test)
    {
        /* TODO */
        String regex = "^(([1-7]?[bknpqrBKNPQR]([1-7]?[bknpqrBKNPQR])*[1-7]?)|8)/(([1-7]?[bknpqrBKNPQR]([1-7]?[bknpqrBKNPQR])*[1-7]?)|8)/(([1-7]?[bknpqrBKNPQR]([1-7]?[bknpqrBKNPQR])*[1-7]?)|8)/(([1-7]?[bknpqrBKNPQR]([1-7]?[bknpqrBKNPQR])*[1-7]?)|8)/(([1-7]?[bknpqrBKNPQR]([1-7]?[bknpqrBKNPQR])*[1-7]?)|8)/(([1-7]?[bknpqrBKNPQR]([1-7]?[bknpqrBKNPQR])*[1-7]?)|8)/(([1-7]?[bknpqrBKNPQR]([1-7]?[bknpqrBKNPQR])*[1-7]?)|8)/(([1-7]?[bknpqrBKNPQR]([1-7]?[bknpqrBKNPQR])*[1-7]?)|8) ([bw]) (KQkq|KQk|KQq|Kkq|Qkq|KQ|kq|Kk|Kq|Qk|Qq|K|Q|k|q|-) (([a-h][36])|-) ([0-9]+) ([0-9]+)$";
        return Pattern.matches(regex, test);
    }

    public Square[] getSquares()
    {
        if (squares == null)
            computeSquares();
        return squares;
    }

    private void computeSquares()
    {
        squares = new Square[ChessboardImpl.BOARD_SIZE];
        /* TODO */
    }

}
