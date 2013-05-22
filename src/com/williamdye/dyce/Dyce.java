package com.williamdye.dyce;

import com.williamdye.dyce.board.Chessboard;
import com.williamdye.dyce.board.ChessboardImpl;
import com.williamdye.dyce.board.Square;
import com.williamdye.dyce.pieces.Piece;

/**
 * @author William Dye
 */
public class Dyce
{

    public static void main(String[] args)
    {
        out("###########################################");
        out("#  Welcome to dyce, the Dye chess engine  #");
        out("#  written by William Dye                 #");
        out("###########################################\n");
        out("Creating a new chessboard...");
        Chessboard board = new ChessboardImpl();
        FEN fen = new FEN(board);
        out("FEN string: " + fen.getFENString());
        Piece k = board.getSquareByName("b1").getPiece();
        k.move(board.getSquareByName("c3"));
        out("After 1. Nc3: " + fen.getFENString());
        Piece p = board.getSquareByName("d7").getPiece();
        p.move(board.getSquareByName("d5"));
        out("After 1...d5: " + fen.getFENString());
        /*for (Square square : board.getBoard()) {
            String string = "\t" + square.getName() + ": ";
            if (square.isEmpty())
                string += "<empty>";
            else
                string += square.getPiece().toString();
            out(string);
        }*/
        /*out("Default FEN valid? " + FEN.isValidFENString(FEN.INITIAL_FEN_STRING));
        out("Sample FEN valid? " + FEN.isValidFENString("2kr1r2/ppp4Q/2n5/b2pP3/8/2P2NnP/PP2q1P1/RNB1K1R1 b - - 4 18"));*/
        out("Bye for now!");
    }

    private static void out(String message)
    {
        System.out.println(message);
    }

}
