package com.williamdye.dyce.board;

import com.williamdye.dyce.notation.FEN;
import com.williamdye.dyce.pieces.*;

import java.util.List;

/**
 * @author William Dye
 */
public interface Chessboard
{

    public FEN getFEN();

    public String prettyPrint();

    public Square[] getBoard();

    public List<Piece> getActiveWhitePieces();

    public List<Piece> getActiveWhitePieces(PieceType type);

    public List<Piece> getCapturedWhitePieces();

    public List<Piece> getActiveBlackPieces();

    public List<Piece> getActiveBlackPieces(PieceType type);

    public List<Piece> getCapturedBlackPieces();

    public King getWhiteKing();

    public Queen getWhiteQueen();

    public King getBlackKing();

    public Queen getBlackQueen();

    public Square getSquareByName(String name);

    public Square getEnPassantTargetSquare();

    public PieceColor getActiveColor();

    public int getMoveCount();

    public int getHalfMoveClock();

    public int getHalfMoveTotal();

    public String getCastlingAvailability();

    public void move(Piece piece, Square dest);

    /* convenience method for testing ... */
    public void move(String from, String to);

}
