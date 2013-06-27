package com.williamdye.dyce.pieces;

import com.williamdye.dyce.board.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * @author William Dye
 */
@RunWith(JUnit4.class)
public class PieceTests
{
    private Chessboard board;


    @Before
    public void setUp()
    {
        board = new ChessboardImpl();
    }

    @Test
    public void isLegalSquare_True()
    {
        Piece knight = board.getSquareByName("b8").getPiece();
        assertTrue("Nc6 is illegal?", knight.isLegalSquare(board.getSquareByName("c6")));
        assertTrue("Na6 is illegal?", knight.isLegalSquare(board.getSquareByName("a6")));

        /* TODO: REALLY test this method */

        /*
        Piece king = board.getSquareByName("e1").getPiece();
        assertTrue("Ke2 is illegal?", king.isLegalSquare(board.getSquareByName("e2")));
        assertTrue("Kd1 is illegal?", king.isLegalSquare(board.getSquareByName("d1")));

        Piece bishop = board.getSquareByName("c1").getPiece();
        assertTrue("Bf4 is illegal?", bishop.isLegalSquare(board.getSquareByName("f4")));
        assertTrue("Ba3 is illegal?", bishop.isLegalSquare(board.getSquareByName("a3")));

        Piece queen = board.getSquareByName("d8").getPiece();
        assertTrue("Qd1 is illegal?", queen.isLegalSquare(board.getSquareByName("d1")));
        assertTrue("Qh8 is illegal?", queen.isLegalSquare(board.getSquareByName("h8")));

        Piece pawn = board.getSquareByName("e2").getPiece();
        assertTrue("e4 is illegal?", pawn.isLegalSquare(board.getSquareByName("e4")));
        assertTrue("e3 is illegal?", pawn.isLegalSquare(board.getSquareByName("e3")));
        */

    }

    @Test
    public void isLegalSquare_False()
    {
        /* TODO: add more test cases */

        Piece king = board.getSquareByName("e1").getPiece();
        assertFalse("Ke5 is legal?", king.isLegalSquare(board.getSquareByName("e5")));
        assertFalse("Kh8 is legal?", king.isLegalSquare(board.getSquareByName("h8")));

        Piece knight = board.getSquareByName("b8").getPiece();
        assertFalse("Na1 is legal?", knight.isLegalSquare(board.getSquareByName("a1")));
        assertFalse("Nd3 is legal?", knight.isLegalSquare(board.getSquareByName("d3")));

        Piece bishop = board.getSquareByName("c1").getPiece();
        assertFalse("Bf5 is legal?", bishop.isLegalSquare(board.getSquareByName("f5")));
        assertFalse("Bb1 is legal?", bishop.isLegalSquare(board.getSquareByName("b1")));

        Piece queen = board.getSquareByName("d8").getPiece();
        assertFalse("Qh1 is legal?", queen.isLegalSquare(board.getSquareByName("h1")));
        assertFalse("Qe2 is legal?", queen.isLegalSquare(board.getSquareByName("e2")));

        Piece pawn = board.getSquareByName("e2").getPiece();
        assertFalse("e1 is legal?", pawn.isLegalSquare(board.getSquareByName("e1")));
        assertFalse("e->f2 is legal?", pawn.isLegalSquare(board.getSquareByName("f2")));
    }

}
