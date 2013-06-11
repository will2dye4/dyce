package com.williamdye.dyce.notation;

import com.williamdye.dyce.board.Chessboard;
import com.williamdye.dyce.board.File;
import com.williamdye.dyce.board.Rank;
import com.williamdye.dyce.board.Square;
import com.williamdye.dyce.exception.AmbiguousMoveException;
import com.williamdye.dyce.exception.IllegalMoveException;
import com.williamdye.dyce.game.PartialMove;
import com.williamdye.dyce.pieces.Piece;
import com.williamdye.dyce.pieces.PieceColor;
import com.williamdye.dyce.pieces.PieceType;

import java.util.ArrayList;
import java.util.List;

public class PGN
{
    public static final String CASTLING_KINGSIDE = "O-O";
    public static final String CASTLING_QUEENSIDE = "O-O-O";

    protected final Chessboard chessboard;
    protected final List<String> moves;

    public PGN(Chessboard board)
    {
        this.chessboard = board;
        this.moves = new ArrayList<String>();
    }

    public PartialMove parseMove(PieceColor toMove, String move) throws IllegalMoveException, AmbiguousMoveException
    {
        /* TODO : handle castling, split this method into helper methods */
        if (toMove == null || move == null)
            return null;
        move = move.replaceAll("[+#]", "").replaceAll("=[BNQR]", "");
        if (move.length() < 2 || move.length() > 5)
            throw new IllegalMoveException();
        String firstChar = move.substring(0, 1);
        Square dest = null;
        int xIndex = move.indexOf("x");
        if (xIndex > 0)
            dest = chessboard.getSquareByName(move.substring(xIndex + 1, xIndex + 3));
        if (Character.isLowerCase(move.charAt(0))) {
            /* pawn move */
            if (!"abcdefgh".contains(firstChar))
                throw new IllegalMoveException();
            if (dest == null)
                dest = chessboard.getSquareByName(move.substring(0, 2));
            File file = File.forName(firstChar);
            Piece pawn = null;
            for (Piece piece : chessboard.getActivePieces(toMove, PieceType.PAWN)) {
                if ((piece.getSquare().getFile() == file) && piece.isLegalSquare(dest)) {
                    pawn = piece;
                    break;
                }
            }
            if (pawn == null)
                throw new IllegalMoveException();
            return new PartialMove(pawn, dest);
        } else {
            /* non-pawn move */
            if (!"BKNQR".contains(firstChar))
                throw new IllegalMoveException();
            Rank rank = null;
            File file = null;
            if ((move.contains("x") && move.length() > 4) || move.length() > 3) {
                String secondChar = move.substring(1, 2);
                if (Character.isDigit(move.charAt(1)))
                    rank = Rank.forNumber(Integer.parseInt(secondChar));
                else
                    file = File.forName(secondChar);
            }
            if (dest == null) {
                if (rank != null || file != null)
                    dest = chessboard.getSquareByName(move.substring(2, 4));
                else
                    dest = chessboard.getSquareByName(move.substring(1, 3));
            }
            List<Piece> candidates = new ArrayList<Piece>();
            /* TODO : can't call getActivePieces when type is king or queen */
            for (Piece piece : chessboard.getActivePieces(toMove, PieceType.forSymbol(move.charAt(0)))) {
                piece.toString();
                if (piece.isLegalSquare(dest) &&
                        ((rank == null) || (piece.getSquare().getRank() == rank)) &&
                        ((file == null) || (piece.getSquare().getFile() == file))) {
                    candidates.add(piece);
                }
            }
            if (candidates.size() > 1)
                throw new AmbiguousMoveException();
            if (candidates.size() == 0)
                throw new IllegalMoveException();
            return new PartialMove(candidates.get(0), dest);
        }
    }

}
