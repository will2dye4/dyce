package com.williamdye.dyce.pieces

/**
 * Enumeration of the possible types of chess pieces.
 *
 * @author William Dye
 */
enum PieceType
{
    PAWN(   'p',   "pawn",  1),
    KNIGHT( 'n', "knight",  3),
    BISHOP( 'b', "bishop",  3),
    ROOK(   'r',   "rook",  5),
    QUEEN(  'q',  "queen",  9),
    KING(   'k',   "king",  Integer.MAX_VALUE);

    /** The piece type's symbol. */
    protected char symbol

    /** The piece type's logical name. */
    protected String name

    /** The piece type's material value. */
    protected int materialValue

    /**
     * Construct a {@code PieceType} having the specified values.
     *
     * @param symbol The piece type's symbol
     * @param name The piece type's logical name
     * @param materialValue The piece type's material value
     */
    PieceType(final String symbol, final String name, final int materialValue)
    {
        this.symbol = symbol.charAt(0)
        this.name = name
        this.materialValue = materialValue
    }

    /**
     * Given a piece type's symbol, retrieve the corresponding {@code PieceType}.
     *
     * @param symbol The symbol of the piece type to find
     * @return The piece type having the specified symbol, or {@code null} if not found
     */
    public static PieceType forSymbol(char symbol)
    {
        if (Character.isUpperCase(symbol)) {
            symbol += 32
        }
        values().find { it.symbol == symbol }
    }

    /**
     * Accessor for a piece type's symbol.
     *
     * @return The piece type's symbol
     */
    char getSymbol()
    {
        symbol
    }

    /**
     * Accessor for a piece type's material value.
     *
     * @return The piece type's material value
     */
    int getMaterialValue()
    {
        materialValue
    }

    @Override
    String toString()
    {
        name
    }

}
