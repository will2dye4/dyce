package com.williamdye.dyce.board;

/**
 * @author William Dye
 */
public enum File
{
    A_FILE("a", 1),
    B_FILE("b", 2),
    C_FILE("c", 3),
    D_FILE("d", 4),
    E_FILE("e", 5),
    F_FILE("f", 6),
    G_FILE("g", 7),
    H_FILE("h", 8);

    protected String name;
    protected int num;

    File(String string, int number)
    {
        this.name = string;
        this.num = number;
    }

    public static File forName(String name)
    {
        for (File file : File.values()) {
            if (file.toString().equals(name))
                return file;
        }
        return null;
    }

    public int getNumber()
    {
        return num;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
