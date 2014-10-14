package com.williamdye.dyce;

import java.util.Scanner;

import com.williamdye.dyce.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.williamdye.dyce.board.*;
import com.williamdye.dyce.exception.*;
import com.williamdye.dyce.game.*;
import com.williamdye.dyce.pieces.*;

/**
 * Main class for the dyce application.
 */
public final class Dyce
{

    private static final Logger logger = LoggerFactory.getLogger(Dyce.class);

    private static final String USAGE = "Usage: java Dyce [-ev]";
    private static final String VERSION_INFO = "dyce version 0.1, built October 2014";

    public static void main(String[] args)
    {
        logger.info("The application has been started with arguments [{}]", StringUtils.join(args, ", "));

        out("###########################################");
        out("#  Welcome to dyce, the Dye chess engine  #");
        out("#  written by William Dye                 #");
        out("###########################################\n");

        if (args.length == 0) {
            logger.debug("No program arguments given; defaulting to explore mode");
            out("No option given; assuming '-e'...\n");
            args = new String[]{"-e"};
        }

        parseArgs(args);

        logger.info("The application is exiting");
        out("Bye for now!");
    }

    private static void usage()
    {
        logger.info("Displaying usage message; program arguments were invalid");
        out(USAGE);
    }

    private static void version()
    {
        logger.info("Displaying version information: {}", VERSION_INFO);
        out(VERSION_INFO);
    }

    private static void parseArgs(final String[] args)
    {
        String command = args[0];
        if ((args.length > 1) || (command.length() < 2) || ('-' != command.charAt(0))) {
            usage();
        } else {
            switch (command.charAt(1)) {
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
        logger.info("Entering explore mode...");

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
