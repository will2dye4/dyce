package com.williamdye.dyce.pieces

import com.williamdye.dyce.board.DefaultChessboard
import spock.lang.Specification

class PieceSpec extends Specification
{

    def board = new DefaultChessboard()

    def setupSpec()
    {
        DefaultChessboard.metaClass.propertyMissing = { square -> delegate.getSquareByName(square) }
    }

    def "is legal square returns true when the square is a legal destination for the piece"()
    {
        given:
        def knight = board["b8"].piece.get()

        expect:
        knight.isLegalSquare(board[square]) == true

        where:
        square << ["c6", "a6"]

        // TODO - add more test cases
    }

    def "is legal square returns false when the square is not a legal destination for the piece"()
    {
        expect:
        def piece = board[start].piece.get()
        piece.isLegalSquare(board[end]) == false

        where:
        start | end
        "e1"  | "e5"
        "e1"  | "h8"

        "b8"  | "a1"
        "b8"  | "d3"

        "c1"  | "f5"
        "c1"  | "b1"

        "d8"  | "h1"
        "d8"  | "e2"

        "e2"  | "e1"
        "e2"  | "f2"

        // TODO - add more test cases
    }

}
