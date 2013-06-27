package com.williamdye.dyce;

import com.williamdye.dyce.board.*;
import com.williamdye.dyce.exception.*;
import com.williamdye.dyce.game.*;
import com.williamdye.dyce.pieces.*;

import java.util.Scanner;

/**
 * @author William Dye
 */
public final class Dyce
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
        out("dyce version 0.1, built June 2013");
    }

    private static void parseArgs(final String[] args)
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
        final String[] TO_QUIT = { ":quit", ":q" };
        Scanner scan = new Scanner(System.in);
        out("*========[ Board Explorer ]========================================*");
        out("| You play both sides. At the prompt, type the next move,          |");
        out("| using Portable Game Notation (PGN). For example, to move         |");
        out("| a Knight from b1 to c3, enter \"Nc3\".                             |");   /* spacing is not off */
        out("| To quit, enter \"" + TO_QUIT[0] + "\" or \"" + TO_QUIT[1] + "\".                                  |");
        out("*==================================================================*");
        out("\nPress Enter to begin...");
        scan.useDelimiter("");
        scan.next();
        scan.useDelimiter("\n");
        GameState state = board.getGameState();
        String move = "", lastMove = "";
        do {
            if (move.length() > 0) {
                try {
                    board.move(move);
                } catch (AmbiguousMoveException ame) {
                    /* TODO - do more than this */
                    out("Ambiguous move!");
                } catch (IllegalMoveException ime) {
                    /* TODO - do more than this */
                    out("Illegal move!");
                }
            }
            out("\n" + board.prettyPrint());
            if (state.getHalfMoveTotal() > 0)
                out("Last move: " + lastMove);
            print("Enter " + ((state.getActiveColor() == PieceColor.WHITE) ? "white" : "black") + "'s move: ");
            move = scan.next();
            lastMove = state.getMoveCount() + ((state.getActiveColor() == PieceColor.WHITE) ? ". " : "...") + move;
        } while (!TO_QUIT[0].equals(move) && !TO_QUIT[1].equals(move));
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
