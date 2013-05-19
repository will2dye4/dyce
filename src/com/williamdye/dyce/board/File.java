package com.williamdye.dyce.board;

/**
 * @author William Dye
 */
public enum File
{
    A_FILE("a"),
    B_FILE("b"),
    C_FILE("c"),
    D_FILE("d"),
    E_FILE("e"),
    F_FILE("f"),
    G_FILE("g"),
    H_FILE("h");

    protected String name;

    File(String string)
    {
        this.name = string;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
