package com.williamdye.dyce.notation;

import com.williamdye.dyce.board.*;
import com.williamdye.dyce.exception.*;
import com.williamdye.dyce.game.*;
import com.williamdye.dyce.pieces.*;

import java.util.*;

public class PGN
{
    /* These are uppercase letter 'O's, not zeros. */
    public static final String CASTLING_KINGSIDE = "O-O";
    public static final String CASTLING_QUEENSIDE = "O-O-O";

    protected final Chessboard chessboard;
    protected final List<String> moves;

    public PGN(Chessboard board)
    {
        this.chessboard = board;
        this.moves = new ArrayList<String>();
    }

    public PartialMove parseMove(final PieceColor toMove, String move) throws IllegalMoveException, AmbiguousMoveException
    {
        if (toMove == null || move == null)
            throw new IllegalArgumentException();

        move = move.trim();
        if (CASTLING_KINGSIDE.equals(move) || CASTLING_QUEENSIDE.equals(move))
            return parseCastlingMove(toMove, move);

        move = move.replaceAll("[+#]", "").replaceAll("=[BNQR]", "");
        if (move.length() < 2 || move.length() > 5)
            throw new IllegalMoveException();

        Square dest = null;
        int xIndex = move.indexOf("x");
        if (xIndex > 0)
            dest = chessboard.getSquareByName(move.substring(xIndex + 1, xIndex + 3));

        return (Character.isLowerCase(move.charAt(0)) ? parsePawnMove(toMove, move, dest) : parsePieceMove(toMove, move, dest));
    }

    private PartialMove parseCastlingMove(final PieceColor toMove, final String move) throws IllegalMoveException
    {
        CastlingAvailability castling = chessboard.getGameState().getCastlingAvailability();
        boolean kingside = CASTLING_KINGSIDE.equals(move);
        if (castling.canCastle(toMove, kingside)) {
            String destName = String.format("%s%d", (kingside ? "g" : "c"), (PieceColor.WHITE == toMove ? 1 : 8));
            return new PartialMove(chessboard.getKing(toMove), chessboard.getSquareByName(destName), MoveType.CASTLING);
        }
        throw new IllegalMoveException();
    }

    private PartialMove parsePawnMove(final PieceColor toMove, final String move, Square dest) throws IllegalMoveException
    {
        String fileString = move.substring(0, 1);
        if (!"abcdefgh".contains(fileString))
            throw new IllegalMoveException();
        if (dest == null)
            dest = chessboard.getSquareByName(move.substring(0, 2));
        File file = File.forName(fileString);
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
    }

    private PartialMove parsePieceMove(final PieceColor toMove, final String move, Square dest) throws
            IllegalMoveException, AmbiguousMoveException
    {
        if (!"BKNQR".contains(move.substring(0, 1)))
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

        PieceType pieceType = PieceType.forSymbol(move.charAt(0));
        if (PieceType.KING == pieceType) {
            Piece king = chessboard.getKing(toMove);
            if (king.isLegalSquare(dest))
                return new PartialMove(king, dest);
            throw new IllegalMoveException();
        }

        List<Piece> candidates = new ArrayList<Piece>();
        for (Piece piece : chessboard.getActivePieces(toMove, pieceType)) {
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
