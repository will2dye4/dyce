package com.williamdye.dyce.board

import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

class PathsSpec extends Specification
{

    def setupSpec()
    {
        DefaultChessboard.metaClass.propertyMissing = { square -> delegate.getSquareByName(square) }
    }

    def "getting rank distance throws exception for mismatched squares"()
    {
        given:
        def a1 = new DefaultChessboard()["a1"]
        def h8 = new DefaultChessboard()["h8"]

        when:
        Paths.getRankDistance(a1, h8)

        then:
        thrown(IllegalArgumentException)
    }

    @Unroll
    def "rank distance between #start and #end is #distance"()
    {
        given:
        def board = new DefaultChessboard()

        expect:
        Paths.getRankDistance(board[start], board[end]) == distance

        where:
        start | end  || distance
        "a2"  | "a7" || 5
        "a7"  | "a2" || 5
        "f2"  | "a7" || 5
        "d1"  | "d8" || 7
        "a2"  | "f2" || 0
        "c5"  | "c5" || 0
    }

    def "getting file distance throws exception for mismatched squares"()
    {
        given:
        def a1 = new DefaultChessboard()["a1"]
        def h8 = new DefaultChessboard()["h8"]

        when:
        Paths.getFileDistance(a1, h8)

        then:
        thrown(IllegalArgumentException)
    }

    @Unroll
    def "file distance between #start and #end is #distance"()
    {
        given:
        def board = new DefaultChessboard()

        expect:
        Paths.getFileDistance(board[start], board[end]) == distance

        where:
        start | end  || distance
        "a1"  | "h1" || 7
        "h1"  | "a1" || 7
        "h1"  | "d8" || 4
        "b8"  | "g8" || 5
        "c1"  | "f1" || 3
        "d8"  | "e8" || 1
        "e4"  | "e6" || 0
        "c1"  | "c1" || 0
    }

    def "is same diagonal throws exception for mismatched squares"()
    {
        given:
        def a1 = new DefaultChessboard()["a1"]
        def h8 = new DefaultChessboard()["h8"]

        when:
        Paths.isSameDiagonal(a1, h8)

        then:
        thrown(IllegalArgumentException)
    }

    @Unroll
    def "#start and #end are on the same diagonal"()
    {
        given:
        def board = new DefaultChessboard()

        expect:
        Paths.isSameDiagonal(board[start], board[end]) == true

        where:
        start | end
        "a1"  | "h8"    /* dark squares */
        "h8"  | "a1"
        "a1"  | "c3"
        "c3"  | "e1"
        "e1"  | "h4"

        "c8"  | "a6"    /* light squares */
        "a6"  | "c8"
        "a6"  | "f1"
        "f1"  | "h3"
        "h3"  | "f5"
        "f5"  | "g6"
    }

    @Unroll
    def "#start and #end are NOT on the same diagonal"()
    {
        given:
        def board = new DefaultChessboard()

        expect:
        Paths.isSameDiagonal(board[start], board[end]) == false

        where:
        start | end
        "a1"  | "a8"
        "a8"  | "h8"
        "h8"  | "f7"
        "h8"  | "e4"
        "f7"  | "e4"
        "e4"  | "c3"
        "c3"  | "d3"
    }

    def "is path clear throws exception for mismatched squares"()
    {
        given:
        def a1 = new DefaultChessboard()["a1"]
        def h8 = new DefaultChessboard()["h8"]

        when:
        Paths.isPathClear(a1, h8)

        then:
        thrown(IllegalArgumentException)
    }

    @Ignore
    def "is path clear returns true when the path is clear"()
    {
        // TODO
    }

    @Ignore
    def "is path clear returns false when the path is not clear"()
    {
        // TODO
    }

}
