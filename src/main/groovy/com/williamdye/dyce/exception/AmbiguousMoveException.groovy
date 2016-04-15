package com.williamdye.dyce.exception

import com.williamdye.dyce.pieces.Piece

class AmbiguousMoveException extends Exception
{
    private List<Piece> pieces

    AmbiguousMoveException(List<Piece> pieces)
    {
        super()
        this.pieces = pieces
    }

    @Override
    String getMessage()
    {
        "Move could refer to pieces on any of these squares: [${pieces*.square*.toString().join(', ')}]"
    }

    List<Piece> getPieces()
    {
        pieces
    }
}
