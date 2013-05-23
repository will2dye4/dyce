package com.williamdye.dyce;

import com.williamdye.dyce.board.Chessboard;
import com.williamdye.dyce.board.ChessboardImpl;

import java.util.Scanner;

/**
 * @author William Dye
 */
public class Dyce
{

    public static void main(String[] args)
    {
        out("###########################################");
        out("#  Welcome to dyce, the Dye chess engine  #");
        out("#  written by William Dye                 #");
        out("###########################################\n");
        if (args.length == 0) {
            out("No option given; assuming '-e'...\n");
            args = new String[] { "-e" };
        }
        parseArgs(args);
        out("Bye for now!");
    }

    private static void usage()
    {
        out("Usage: java Dyce [-ev]");
    }

    private static void version()
    {
        out("dyce version 0.1, built May 2013");
    }

    private static void parseArgs(String[] args)
    {
        if ((args.length > 1) || (args[0].length() < 2) || ('-' != args[0].charAt(0)) || ('-' == args[0].charAt(1))) {
            usage();
        } else {
            switch (args[0].charAt(1)) {
                case 'e':
                    explore(new ChessboardImpl());
                    break;
                case 'v':
                    version();
                    break;
                default:
                    usage();
                    break;
            }
        }
    }

    private static void explore(Chessboard board)
    {
        Scanner scan = new Scanner(System.in);
        out("*========[ Board Explorer ]========================================*");
        out("| You play both sides. At the prompt, type the next move,          |");
        out("| with the starting and finishing squares separated by a space.    |");
        out("| For example, to move a Pawn from e2 to e4, enter \"e2 e4\".        |");   /* spacing looks off but */
        out("| To quit, enter \":quit\" or \":q\".                                  |"); /*  it prints correctly  */
        out("*==================================================================*");
        out("\nPress Enter to begin...");
        scan.useDelimiter("");
        scan.next();
        scan.useDelimiter("\n");
        String move = "", lastMove = "";
        do {
            if (move.length() > 0) {
                String[] squares = move.split(" ");
                board.move(squares[0], squares[1]);
            }
            out("\n" + board.prettyPrint());
            if (lastMove.length() > 0)
                out("Last move was: " + lastMove);
            print("Enter a move: ");
            move = scan.next();
            lastMove = move;
        } while (!":quit".equals(move) && !":q".equals(move));
    }

    private static void out(String message)
    {
        System.out.println(message);
    }

    private static void print(String message)
    {
        System.out.print(message);
    }

}
