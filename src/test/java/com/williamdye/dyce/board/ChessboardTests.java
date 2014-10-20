package com.williamdye.dyce.board;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.williamdye.dyce.pieces.*;

@RunWith(JUnit4.class)
public class ChessboardTests
{
    public static final int NUM_BISHOPS = 2;
    public static final int NUM_KNIGHTS = 2;
    public static final int NUM_PAWNS = 8;
    public static final int NUM_ROOKS = 2;
    public static final int PIECES_PER_SIDE = 16;

    @Test
    public void createChessboardTest()
    {
        Chessboard board = new DefaultChessboard();
        assertNotNull("new board has null FEN", board.getFEN());
        assertNotNull("new board has null state", board.getGameState());
        Map<PieceType, Integer> map = new HashMap<>();
        map.put(PieceType.BISHOP, NUM_BISHOPS);
        map.put(PieceType.KNIGHT, NUM_KNIGHTS);
        map.put(PieceType.PAWN, NUM_PAWNS);
        map.put(PieceType.ROOK, NUM_ROOKS);
        for (PieceColor color : PieceColor.values()) {
            String colorName = color.getName();
            assertEquals(String.format("new board does not have %d %s pieces", PIECES_PER_SIDE, colorName),
                    PIECES_PER_SIDE, board.getActivePieces(color).size());
            assertEquals("new board has captured " + colorName + " pieces", 0, board.getCapturedPieces(color).size());
            for (PieceType type : map.keySet()) {
                int expected = map.get(type);
                assertEquals(String.format("new board does not have %d %s %ss", expected, colorName, type),
                        expected, board.getActivePieces(color, type).size());
            }
            assertNotNull("new board has null " + colorName + " king", board.getKing(color));
            assertNotNull("new board has null " + colorName + " queen", board.getActivePieces(color, PieceType.QUEEN).get(0));
        }
    }

    @Test
    public void getSquareByNameTest_RealSquares()
    {
        Chessboard board = new DefaultChessboard();
        String[] realSquares = {"a1", "a8", "h1", "h8", "e4", "c5", "d6", "f2", "h4", "g7", "b3"};
        for (String square : realSquares)
            assertNotNull("board does not have " + square + " square", board.getSquareByName(square));
    }

    @Test
    public void getSquareByNameTest_FakeSquares()
    {
        Chessboard board = new DefaultChessboard();
        String[] fakeSquares = {"a0", "h9", "5c", "i1", "2a", "d9", "22", "k3", "foo", "alpha won", "bb"};
        for (String square : fakeSquares) {
            boolean thrown = false;
            try {
                board.getSquareByName(square);
            } catch (Exception e) {
                assertTrue(e instanceof IllegalArgumentException);
                thrown = true;
            }
            if (!thrown) {
                fail("Exception not thrown for illegal square name");
            }
        }
    }

    /* TODO : test BaseChessboard::move(Piece, Square) */

}
