package com.williamdye.dyce.board

import com.williamdye.dyce.exception.AmbiguousMoveException
import com.williamdye.dyce.exception.IllegalMoveException
import com.williamdye.dyce.game.Game
import com.williamdye.dyce.pieces.King
import com.williamdye.dyce.pieces.Piece
import com.williamdye.dyce.pieces.PieceColor
import com.williamdye.dyce.pieces.PieceType

/**
 * This is dyce's representation of a chessboard. It acts as a container for the board's squares and pieces,
 * along with the game's state and its representation in Forsyth-Edwards Notation (FEN). The {@code Chessboard}
 * interface also provides the mechanism for moving pieces via the {@code move} methods.
 *
 * @author William Dye
 */
interface Chessboard
{

    /**
     * Accessor for a reference to a chessboard's game.
     *
     * @return The game for this chessboard
     */
    Game getGame()

    /**
     * Formats the chessboard as a string which can be printed to the console. The board is represented as an ASCII
     * table, with the pieces represented as they are in Forsyth-Edwards Notation. The formatting includes labels for
     * the ranks and files along the left and bottom edges of the table.
     *
     * @return A formatted representation of this chessboard
     */
    String prettyPrint()

    /**
     * Accessor for a chessboard's array of {@link Square}s, which comprise the board itself.
     *
     * @return The array of squares which make up this chessboard
     */
    Square[] getBoard()

    /**
     * Accessor for a chessboard's active pieces of a certain color.
     *
     * @param color The target color for the returned pieces
     * @return A list of the active pieces of the specified color
     */
    List<Piece> getActivePieces(PieceColor color)

    /**
     * Accessor for a chessboard's active pieces of a certain color and type.
     *
     * NOTE: This method should not be called with {@code PieceType.KING} as its {@code type} argument. Instead,
     * use the {@link #getKing} method to retrieve the king of a certain color.
     *
     * @param color The target color for the returned pieces
     * @param type The target type for the returned pieces
     * @return A list of the active pieces of the specified color and type
     */
    List<Piece> getActivePieces(PieceColor color, PieceType type)

    /**
     * Accessor for a chessboard's captured pieces of a certain color.
     *
     * @param color The target color for the returned pieces
     * @return A list of the captured pieces of the specified color
     */
    List<Piece> getCapturedPieces(PieceColor color)

    /**
     * Accessor for a chessboard's king of a certain color.
     *
     * @param color the color of the king to return
     * @return the king of the specified color
     */
    King getKing(PieceColor color)

    /**
     * Returns the square corresponding to the specified rank and file.
     *
     * @param file The file of the square to return
     * @param rank The rank of the square to return
     * @return The square at the specified rank and file
     */
    Square getSquare(com.williamdye.dyce.board.File file, Rank rank)

    /**
     * Returns the square corresponding to the specified name. The name is given as the square's file (in lower case)
     * followed by its rank, with no space or separator in between. For example, to access the square in the bottom
     * left of the board (when viewed from white's perspective), the appropriate name would be {@code "a1"}.
     *
     * @param name The name of the square to return
     * @return The square corresponding to the name
     */
    Square getSquareByName(String name)

    /**
     * Moves a certain piece to a certain square, provided that the square is legal for the piece.
     *
     * @param piece The piece to move
     * @param dest The square to which the piece should move
     * @throws IllegalMoveException if the piece may not legally move to {@code dest}
     */
    void move(Piece piece, Square dest) throws IllegalMoveException

    /**
     * Makes a move corresponding to a Portable Game Notation (PGN) string, provided that the move is legal
     * and unambiguous.
     *
     * @param pgnString The PGN string of the move
     * @throws AmbiguousMoveException If more than one piece could make the specified move
     * @throws IllegalMoveException If the specified move is not legal
     */
    void move(String pgnString) throws AmbiguousMoveException, IllegalMoveException

}
