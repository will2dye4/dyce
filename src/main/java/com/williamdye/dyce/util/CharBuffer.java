package com.williamdye.dyce.util;

import java.io.*;
import java.util.Scanner;

/**
 * A helper class for reading a file one character at a time.
 * @author William Dye
 */
public class CharBuffer
{

    protected int index, line;
    private boolean prev, closed;
    private String currentLine;
    private Scanner scanner;

    /**
     * Instantiates a CharBuffer to read the specified file.
     * @param file the file to be read by this buffer
     */
    public CharBuffer(File file)
    {
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException except) {
            throw new IllegalStateException("Attempt to construct CharBuffer for nonexistent file", except);
        }
        currentLine = scanner.nextLine();
        index = 0;
        line = 1;
        prev = true;
    }

    /**
     * Instantiates a CharBuffer to read a string rather than a file
     * @param s string to buffer
     */
    public CharBuffer (String s)
    {
        scanner = new Scanner(s);
        currentLine = scanner.nextLine();
        index = 0;
        line = 1;
        prev = true;
        closed = false;
    }

    /**
     * Returns and consumes the next character in the file.
     */
    public char getNextChar()
    {
        return next(false);
    }

    /**
     * Returns, but does not consume, the next character in the file.
     */
    public char peekNextChar()
    {
        return next(true);
    }

    /**
     * If the file has a next line, advances to the next line and returns <code>true</code>.
     * Otherwise, returns <code>false</code>.
     */
    public boolean advance()
    {
        boolean hasNext = false;
        if (!closed) {
            if (scanner.hasNextLine()) {
                hasNext = true;
                currentLine = scanner.nextLine();
                index = 0;
                line++;
                prev = true;
            } else {
                scanner.close();
                closed = true;
            }
        }
        return hasNext;
    }

    /* Helper for the getNextChar() and peekNextChar() methods. */
    private char next(boolean peek)
    {
        char next;
        if (!prev)
            index++;
        if (index >= currentLine.length())
            next = '\n';
        else
            next = currentLine.charAt(index);
        prev = peek;
        return next;
    }

    /**
     * Accessor method for index in file.
     * @return current index of CharBuffer
     */
    public int getIndex()
    {
        return index;
    }

    public void setIndex(int newIndex)
    {
        index = newIndex;
    }

    public int getLineNumber()
    {
        return line;
    }

}