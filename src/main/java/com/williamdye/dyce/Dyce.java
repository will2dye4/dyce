package com.williamdye.dyce;

import java.util.*;

import com.google.common.base.Splitter;
import org.slf4j.*;

import com.williamdye.dyce.board.*;
import com.williamdye.dyce.exception.*;
import com.williamdye.dyce.game.*;
import com.williamdye.dyce.pieces.*;
import com.williamdye.dyce.util.StringUtils;

/**
 * Main class for the dyce application.
 */
public final class Dyce
{

    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(Dyce.class);

    /** Usage message (printed on invalid invocation) */
    private static final String USAGE = "Usage: java Dyce [-aev]";

    /** Version message */
    private static final String VERSION_INFO = "dyce version 0.1, built October 2014";

    /**
     * Entry point for the dyce application.
     *
     * @param args Program arguments specifying what the application should do
     */
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

    /** Helper to parse the program arguments and respond accordingly. */
    private static void parseArgs(final String[] args)
    {
        if (args.length > 1)
            usage();
        else {
            switch (args[0]) {
                case "-a":
                case "--adventure":
                    adventure(new BuildableChessboardImpl());
                    break;
                case "-e":
                case "--explore":
                    explore(new DefaultChessboard());
                    break;
                case "-v":
                case "--version":
                    version();
                    break;
                default:
                    usage();
                    break;
            }
        }
    }

    /** Helper to print the usage message. */
    private static void usage()
    {
        logger.info("Displaying usage message; program arguments were invalid");
        out(USAGE);
    }

    /** Helper to print the version message. */
    private static void version()
    {
        logger.info("Displaying version information: {}", VERSION_INFO);
        out(VERSION_INFO);
    }

    /** Adventure mode - currently exists for testing purposes. */
    private static void adventure(BuildableChessboard board)
    {
        logger.info("Entering adventure mode...");

        final List<String> TO_QUIT = Arrays.asList("quit", ":q");
        final Scanner scan = new Scanner(System.in);

        out("*========[ Adventure Mode ]========================================*");
        out("| You may place pieces on any blank square and remove pieces       |");
        out("| already on the board. To place a new piece, enter \"place\"        |"); /* spacing is not off */
        out("| followed by the piece color (w/b), the piece type (b/k/n/p/q/r), |");
        out("| and the square to place the piece on (e.g., \"place w q e3\").     |");
        out("| To remove a piece from the board, enter \"remove\" followed by     |");
        out("| the square to remove the piece from (e.g., \"remove c7\").         |");
        out("| To quit, enter \"" + TO_QUIT.get(0) + "\" or \"" + TO_QUIT.get(1) + "\".                                   |");
        out("*==================================================================*");

        out("Press Enter to begin...");
        scan.useDelimiter("");
        scan.next();
        scan.useDelimiter("\n");

        String command = "";
        do {
            if (!command.isEmpty()) {
                List<String> parts = Splitter.on(" ").splitToList(command);
                switch (parts.get(0)) {
                    case ":p":
                    case "place":
                        if (parts.size() != 4)
                            out("You must specify the piece color, type, and square!");
                        else {
                            PieceColor color = ("w".equalsIgnoreCase(parts.get(1)) ? PieceColor.WHITE : PieceColor.BLACK);
                            PieceType type = PieceType.forSymbol(parts.get(2).charAt(0));
                            board.placePiece(color, type, parts.get(3));
                        }
                        break;
                    case ":r":
                    case "remove":
                        if (parts.size() != 2)
                            out("You must specify the square to remove a piece from!");
                        else
                            board.removePiece(parts.get(1));
                        break;
                    default:
                        out("Invalid command!");
                }
            }
            out("\n" + board.prettyPrint());
            print("> ");
            command = scan.next();
        } while (!TO_QUIT.contains(command));
    }

    /** Explore mode - currently exists for testing purposes to interact with a chessboard. */
    private static void explore(Chessboard board)
    {
        logger.info("Entering explore mode...");

        final List<String> TO_QUIT = Arrays.asList(":quit", ":q");
        final GameState state = board.getGameState();
        final Scanner scan = new Scanner(System.in);

        out("*========[ Board Explorer ]========================================*");
        out("| You play both sides. At the prompt, type the next move,          |");
        out("| using Portable Game Notation (PGN). For example, to move         |");
        out("| a Knight from b1 to c3, enter \"Nc3\".                             |");   /* spacing is not off */
        out("| To quit, enter \"" + TO_QUIT.get(0) + "\" or \"" + TO_QUIT.get(1) + "\".                                  |");
        out("*==================================================================*");

        out("\nPress Enter to begin...");
        scan.useDelimiter("");
        scan.next();
        scan.useDelimiter("\n");

        String move = "", lastMove = "";
        do {
            if (!move.isEmpty()) {
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
        } while (!TO_QUIT.contains(move));
    }

    /** Convenience method that calls System.out#println. */
    private static void out(String message)
    {
        System.out.println(message);
    }

    /** Convenience method that calls System.out#print. */
    private static void print(String message)
    {
        System.out.print(message);
    }

}
