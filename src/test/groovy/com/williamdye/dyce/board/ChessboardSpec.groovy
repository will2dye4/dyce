package com.williamdye.dyce.board

import spock.lang.Specification
import spock.lang.Unroll

import com.williamdye.dyce.pieces.PieceColor

import static com.williamdye.dyce.pieces.PieceColor.*
import static com.williamdye.dyce.pieces.PieceType.*

class ChessboardSpec extends Specification
{

    public static final int PIECES_PER_SIDE = 16

    def board = new DefaultChessboard()

    def "new board has properly initialized fields"()
    {
        expect:
        board.FEN != null
        board.gameState != null
        board.board != null
    }

    @Unroll
    def "new board has nonnull lists of #color.name pieces"()
    {
        expect:
        board.getActivePieces(color) != null
        board.getActivePieces(color).size() == PIECES_PER_SIDE

        board.getCapturedPieces(color) != null
        board.getCapturedPieces(color).isEmpty()

        where:
        color << PieceColor.values()
    }

    @Unroll
    def "new board has nonnull #color.name king"()
    {
        expect:
        board.getKing(color) != null

        where:
        color << PieceColor.values()
    }

    @Unroll
    def "new board has #size #color.name #type(s)"()
    {
        expect:
        board.getActivePieces(color, type) != null
        board.getActivePieces(color, type).size() == size

        where:
        color | type   || size
        WHITE | BISHOP || 2
        WHITE | KNIGHT || 2
        WHITE | ROOK   || 2
        WHITE | QUEEN  || 1
        WHITE | PAWN   || 8

        BLACK | BISHOP || 2
        BLACK | KNIGHT || 2
        BLACK | ROOK   || 2
        BLACK | QUEEN  || 1
        BLACK | PAWN   || 8
    }

    @Unroll
    def "new board has a square at #file#rank"()
    {
        expect:
        board.getSquare(file, rank) != null

        where:
        [file, rank] << [File.values(), Rank.values()].combinations()
    }

    @Unroll
    def "new board has a square called #file#rank"()
    {
        expect:
        board.getSquareByName("$file$rank") != null

        where:
        [file, rank] << [File.values(), Rank.values()].combinations()
    }

    @Unroll
    def "new board does not have a square called #square"()
    {
        when:
        board.getSquareByName(square)

        then:
        thrown(IllegalArgumentException)

        where:
        square << ["a0", "h9", "5c", "i1", "2a", "d9", "22", "k3", "foo", "alpha won", "bb"]
    }

}