package com.williamdye.dyce.board;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class PathsTests
{

    @Test
    public void getRankDistance_DifferentBoards()
    {
        Square a1 = (new ChessboardImpl()).getSquareByName("a1");
        Square h8 = (new ChessboardImpl()).getSquareByName("h8");
        assertEquals("squares on different boards != -1", -1, Paths.getRankDistance(a1, h8));
        assertEquals("null square != -1", -1, Paths.getRankDistance(null, a1));
    }

    @Test
    public void getRankDistance_SameBoard()
    {
        Chessboard board = new ChessboardImpl();
        Map<String, Square> map = new HashMap<>();
        String[] squares = {"a2", "a7", "d1", "d8", "f2"};
        for (String name : squares)
            map.put(name, board.getSquareByName(name));
        assertEquals("pawns are not 5 ranks apart", 5, Paths.getRankDistance(map.get("a2"), map.get("a7")));
        assertEquals("getRankDistance is not commutative", 5, Paths.getRankDistance(map.get("a7"), map.get("a2")));
        assertEquals("getRankDistance varies by file", 5, Paths.getRankDistance(map.get("f2"), map.get("a7")));
        assertEquals("queens are not 7 ranks apart", 7, Paths.getRankDistance(map.get("d1"), map.get("d8")));
        assertEquals("a2.getRankDistance(f2) != 0", 0, Paths.getRankDistance(map.get("a2"), map.get("f2")));
        assertEquals("d8.getRankDistance(d8) != 0", 0, Paths.getRankDistance(map.get("d8"), map.get("d8")));
    }

    @Test
    public void getFileDistance_DifferentBoards()
    {
        Square a1 = (new ChessboardImpl()).getSquareByName("a1");
        Square h8 = (new ChessboardImpl()).getSquareByName("h8");
        assertEquals("squares on different boards != -1", -1, Paths.getFileDistance(a1, h8));
    }

    @Test
    public void getFileDistance_SameBoard()
    {
        Chessboard board = new ChessboardImpl();
        Map<String, Square> map = new HashMap<>();
        String[] squares = {"a1", "h1", "b8", "g8", "c1", "f1", "d8", "e8"};
        for (String name : squares)
            map.put(name, board.getSquareByName(name));
        assertEquals("rooks are not 7 files apart", 7, Paths.getFileDistance(map.get("a1"), map.get("h1")));
        assertEquals("getFileDistance is not commutative", 7, Paths.getFileDistance(map.get("h1"), map.get("a1")));
        assertEquals("getFileDistance varies by rank", 4, Paths.getFileDistance(map.get("h1"), map.get("d8")));
        assertEquals("knights are not 5 files apart", 5, Paths.getFileDistance(map.get("b8"), map.get("g8")));
        assertEquals("bishops are not 3 files apart", 3, Paths.getFileDistance(map.get("c1"), map.get("f1")));
        assertEquals("king and queen are not 1 file apart", 1, Paths.getFileDistance(map.get("d8"), map.get("e8")));
        assertEquals("c1.getFileDistance(c1) != 0", 0, Paths.getFileDistance(map.get("c1"), map.get("c1")));
    }

    @Test
    public void isSameDiagonal_DifferentBoards()
    {
        Square a1 = (new ChessboardImpl()).getSquareByName("a1");
        Square h8 = (new ChessboardImpl()).getSquareByName("h8");
        assertFalse("squares on different boards != false", Paths.isSameDiagonal(a1, h8));
    }

    @Test
    public void isSameDiagonal_SameBoard_True()
    {
        Chessboard board = new ChessboardImpl();
        Map<String, Square> map = new HashMap<>();
        String[] darkSquares = {"a1", "h8", "c3", "e1", "h4", "f6"};
        String[] lightSquares = {"c8", "a6", "f1", "h3", "f5", "g6"};
        for (String name : darkSquares)
            map.put(name, board.getSquareByName(name));
        for (String name : lightSquares)
            map.put(name, board.getSquareByName(name));
        /* test dark squares */
        assertTrue("a1 and h8 are not diagonal", Paths.isSameDiagonal(map.get("a1"), map.get("h8")));
        assertTrue("isSameDiagonal is not commutative", Paths.isSameDiagonal(map.get("h8"), map.get("a1")));
        assertTrue("a1 and c3 are not diagonal", Paths.isSameDiagonal(map.get("a1"), map.get("c3")));
        assertTrue("c3 and e1 are not diagonal", Paths.isSameDiagonal(map.get("c3"), map.get("e1")));
        assertTrue("e1 and h4 are not diagonal", Paths.isSameDiagonal(map.get("h4"), map.get("e1")));
        /* test light squares */
        assertTrue("c8 and a6 are not diagonal", Paths.isSameDiagonal(map.get("c8"), map.get("a6")));
        assertTrue("isSameDiagonal is not commutative (2)", Paths.isSameDiagonal(map.get("a6"), map.get("c8")));
        assertTrue("a6 and f1 are not diagonal", Paths.isSameDiagonal(map.get("a6"), map.get("f1")));
        assertTrue("f1 and h3 are not diagonal", Paths.isSameDiagonal(map.get("f1"), map.get("h3")));
        assertTrue("h3 and f5 are not diagonal", Paths.isSameDiagonal(map.get("f5"), map.get("h3")));
        assertTrue("f5 and g6 are not diagonal", Paths.isSameDiagonal(map.get("f5"), map.get("g6")));
    }

    @Test
    public void isSameDiagonal_SameBoard_False()
    {
        Chessboard board = new ChessboardImpl();
        Map<String, Square> map = new HashMap<>();
        String[] squares = {"a1", "a8", "h8", "f7", "e4", "c3", "d3"};
        for (String name : squares)
            map.put(name, board.getSquareByName(name));
        assertFalse("a1 and a8 are diagonal", Paths.isSameDiagonal(map.get("a1"), map.get("a8")));
        assertFalse("a8 and h8 are diagonal", Paths.isSameDiagonal(map.get("a8"), map.get("h8")));
        assertFalse("h8 and f7 are diagonal", Paths.isSameDiagonal(map.get("h8"), map.get("f7")));
        assertFalse("h8 and e4 are diagonal", Paths.isSameDiagonal(map.get("e4"), map.get("h8")));
        assertFalse("f7 and e4 are diagonal", Paths.isSameDiagonal(map.get("f7"), map.get("e4")));
        assertFalse("e4 and c3 are diagonal", Paths.isSameDiagonal(map.get("c3"), map.get("e4")));
        assertFalse("c3 and d3 are diagonal", Paths.isSameDiagonal(map.get("c3"), map.get("d3")));
    }

    @Test
    public void isPathClear_DifferentBoards()
    {
        Square a4 = (new ChessboardImpl()).getSquareByName("a4");
        Square h4 = (new ChessboardImpl()).getSquareByName("h4");
        assertFalse("squares on different boards != false", Paths.isPathClear(a4, h4));
    }

    @Test
    public void isPathClear_SameBoard()
    {
        /* TODO - exhaustively test this method */
    }

}
