package com.williamdye.dyce.board;

import com.williamdye.dyce.exception.IllegalMoveException;
import com.williamdye.dyce.game.GameState;
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

    public GameState getGameState();

    public List<Piece> getActivePieces(PieceColor color);

    public List<Piece> getActivePieces(PieceColor color, PieceType type);

    public List<Piece> getCapturedPieces(PieceColor color);

    public King getKing(PieceColor color);

    public Queen getQueen(PieceColor color);

    public Square getSquareByName(String name);

    public void move(Piece piece, Square dest) throws IllegalMoveException;

    /* convenience method for testing ... */
    public void move(String from, String to) throws IllegalMoveException;

}
