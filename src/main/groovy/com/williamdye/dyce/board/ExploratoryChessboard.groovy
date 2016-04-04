package com.williamdye.dyce.board

import com.williamdye.dyce.pieces.Piece

class ExploratoryChessboard extends BaseChessboard
{

    ExploratoryChessboard(Chessboard source)
    {
        super()
        this.game = source.game
        source.board.eachWithIndex { square, i ->
            if (!square.isEmpty()) {
                Piece piece = pieceFactory.newPiece(square.piece.get().color, square.piece.get().pieceType)
                board[i].setPiece(piece)
                updatePieceLists(piece)
            }
        }
    }

    @Override
    void move(Piece piece, Square dest)
    {
        piece = getSquare(piece.square.file, piece.square.rank).piece.get()
        dest = getSquare(dest.file, dest.rank)
        Optional<Piece> captured = piece.move(dest)
        captured.ifPresent { capture(it) }
    }

    ExploratoryChessboard then(Piece piece, Square dest)
    {
        move(piece, dest)
        this
    }

}
