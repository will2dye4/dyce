package com.williamdye.dyce.util

/**
 * A helper class for reading a file one character at a time.
 *
 * @author William Dye
 */
class CharBuffer
{

    protected int index, line
    private boolean prev, closed
    private String currentLine
    private Scanner scanner

    /**
     * Instantiates a {@code CharBuffer} to read the specified file.
     *
     * @param file the file to be read by this buffer
     */
    CharBuffer(File file)
    {
        try {
            scanner = new Scanner(file)
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Attempt to construct CharBuffer for nonexistent file", e)
        }
        initialize()
    }

    /**
     * Instantiates a {@code CharBuffer} to read a {@code String}.
     *
     * @param s string to buffer
     */
    CharBuffer(String s)
    {
        scanner = new Scanner(s)
        initialize()
    }

    private void initialize()
    {
        currentLine = scanner.nextLine()
        index = 0
        line = 1
        prev = true
        closed = false
    }

    /**
     * Returns and consumes the next character in the file.
     */
    char getNextChar()
    {
        next(false)
    }

    /**
     * Returns, but does not consume, the next character in the file.
     */
    char peekNextChar()
    {
        next(true)
    }

    /**
     * If the file has a next line, advances to the next line and returns {@code true}.
     * Otherwise, returns {@code false}.
     */
    boolean advance()
    {
        boolean hasNext = false
        if (!closed) {
            if (scanner.hasNextLine()) {
                hasNext = true
                currentLine = scanner.nextLine()
                index = 0
                line++
                prev = true
            } else {
                scanner.close()
                closed = true
            }
        }
        hasNext
    }

    /** Helper for the getNextChar() and peekNextChar() methods. */
    private char next(boolean peek)
    {
        char next
        if (!prev) {
            index++
        }
        if (index >= currentLine.length()) {
            next = '\n'
        } else {
            next = currentLine.charAt(index)
        }
        prev = peek
        return next
    }

    /**
     * Accessor method for index in file.
     * @return current index of CharBuffer
     */
    int getIndex()
    {
        index
    }

    void setIndex(int newIndex)
    {
        index = newIndex
    }

    public int getLineNumber()
    {
        line
    }

}