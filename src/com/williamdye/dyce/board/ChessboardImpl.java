package com.williamdye.dyce.board;

import com.williamdye.dyce.pieces.Bishop;
import com.williamdye.dyce.pieces.King;
import com.williamdye.dyce.pieces.Knight;
import com.williamdye.dyce.pieces.Pawn;
import com.williamdye.dyce.pieces.PieceColor;
import com.williamdye.dyce.pieces.Queen;
import com.williamdye.dyce.pieces.Rook;

public class ChessboardImpl implements Chessboard
{
    public static final String INITIAL_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    public static final int BOARD_SIZE = 64;

    /* fen is the Forsyth-Edwards Notation of the current position */
    protected String fen;
    /* squares is a one-dimensional array of squares representing the chessboard as follows:
     * [a1, b1, c1, d1, e1, f1, g1, h1,
     *  a2, b2, c2, d2, e2, f2, g2, h2,
     *  ...,
     *  a8, b8, c8, d8, e8, f8, g8, h8]
     */
    protected Square[] squares;

    public ChessboardImpl()
    {
        this.fen = INITIAL_FEN;
        this.squares = new Square[BOARD_SIZE];
        initialize();
    }

    private void initialize()
    {
        int i = 0;
        Square square;
        for (Rank rank : Rank.values()) {
            for (File file : File.values()) {
                square = new SquareImpl(rank, file);
                switch (rank) {
                    case SECOND_RANK:
                        square = new SquareImpl(rank, file, new Pawn(PieceColor.WHITE));
                        break;
                    case SEVENTH_RANK:
                        square = new SquareImpl(rank, file, new Pawn(PieceColor.BLACK));
                        break;
                    case FIRST_RANK:
                    case EIGHTH_RANK:
                        PieceColor color = ((rank == Rank.FIRST_RANK) ? PieceColor.WHITE : PieceColor.BLACK);
                        switch (file) {
                            case A_FILE:
                            case H_FILE:
                                square = new SquareImpl(rank, file, new Rook(color));
                                break;
                            case B_FILE:
                            case G_FILE:
                                square = new SquareImpl(rank, file, new Knight(color));
                                break;
                            case C_FILE:
                            case F_FILE:
                                square = new SquareImpl(rank, file, new Bishop(color));
                                break;
                            case D_FILE:
                                square = new SquareImpl(rank, file, new Queen(color));
                                break;
                            case E_FILE:
                                square = new SquareImpl(rank, file, new King(color));
                                break;
                        }   /* switch (file) */
                        break;
                    default:
                        square = new SquareImpl(rank, file);
                        break;
                }   /* switch (rank) */
                squares[i++] = square;
            }   /* for each file */
        }   /* for each rank */
    }

    @Override
    public String getFEN()
    {
        return fen;
    }

    @Override
    public Square[] getBoard()
    {
        return squares;
    }
}
