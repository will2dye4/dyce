package com.williamdye.dyce

import groovy.util.logging.Slf4j
import java.text.ParseException

import com.williamdye.dyce.board.BuildableChessboard
import com.williamdye.dyce.board.BuildableChessboardImpl
import com.williamdye.dyce.board.Chessboard
import com.williamdye.dyce.board.DefaultChessboard
import com.williamdye.dyce.exception.AmbiguousMoveException
import com.williamdye.dyce.exception.IllegalMoveException
import com.williamdye.dyce.game.Game
import com.williamdye.dyce.game.GameState
import com.williamdye.dyce.notation.PGNReader
import com.williamdye.dyce.pieces.PieceColor
import com.williamdye.dyce.pieces.PieceType

/**
 * Main class for the dyce application.
 */
@Slf4j
final class Dyce
{

    /** Usage message (printed on invalid invocation) */
    private static final String USAGE = "Usage:\njava Dyce [-aev]\njava Dyce -p|--pgn input_file"

    /** Version message */
    private static final String VERSION_INFO = "dyce version 0.2, built November 2015"

    /**
     * Entry point for the dyce application.
     *
     * @param args Program arguments specifying what the application should do
     */
    static void main(String[] args)
    {
        log.info("The application has been started with arguments [${args.join(", ")}]")

        final String BANNER = """
                ###########################################
                #  Welcome to dyce, the Dye chess engine  #
                #  written by William Dye                 #
                ###########################################\n""".stripIndent()

        println(BANNER)

        if (!args) {
            log.debug("No program arguments given; defaulting to explore mode")
            println("No option given; assuming '-e'...\n")
            args = ["-e"]
        }

        parseArgs(args)

        log.info("The application is exiting")
        println("Bye for now!")
    }

    /** Helper to parse the program arguments and respond accordingly. */
    private static void parseArgs(final String[] args)
    {
        if (args.length > 2) {
            usage()
        } else {
            switch (args[0]) {
                case ["-a", "--adventure"]:
                    adventure(new BuildableChessboardImpl())
                    break
                case ["-e", "--explore"]:
                    explore(new DefaultChessboard())
                    break
                case ["-p", "--pgn"]:
                    pgn(args[1])
                    break
                case ["-v", "--version"]:
                    version()
                    break
                default:
                    usage()
            }
        }
    }

    /** Helper to print the usage message. */
    private static void usage()
    {
        log.info("Displaying usage message; program arguments were invalid")
        println(USAGE)
    }

    /** Helper to print the version message. */
    private static void version()
    {
        log.info("Displaying version information: $VERSION_INFO")
        println(VERSION_INFO)
    }

    /** Adventure mode - currently exists for testing purposes. */
    private static void adventure(BuildableChessboard board)
    {
        log.info("Entering adventure mode...")

        final List<String> TO_QUIT = ["quit", ":q"]
        final String INTRO_TEXT = """
                *========[ Adventure Mode ]========================================*
                | You may place pieces on any blank square and remove pieces       |
                | already on the board. To place a new piece, enter "place"        |
                | followed by the piece color (w/b), the piece type (b/k/n/p/q/r), |
                | and the square to place the piece on (e.g., "place w q e3").     |
                | To remove a piece from the board, enter "remove" followed by     |
                | the square to remove the piece from (e.g., "remove c7").         |
                | To quit, enter "${TO_QUIT[0]}" or "${TO_QUIT[1]}".                                   |
                *==================================================================*""".stripIndent()

        final Scanner scan = new Scanner(System.in)

        println(INTRO_TEXT)
        waitForEnter(scan)

        String command = ""
        while (!TO_QUIT.contains(command)) {
            if (command) {
                List<String> parts = command.split(" ").toList()
                switch (parts.first()) {
                    case [":p", "place"]:
                        if (parts.size() != 4) {
                            println("You must specify the piece color, type, and square!")
                        } else {
                            PieceColor color = ("w".equalsIgnoreCase(parts[1]) ? PieceColor.WHITE : PieceColor.BLACK)
                            PieceType type = PieceType.forSymbol(parts[2].charAt(0))
                            board.placePiece(color, type, parts[3])
                        }
                        break
                    case [":m", "move"]:
                        if (parts.size() != 2) {
                            println("You must enter a move!")
                        } else {
                            try {
                                board.move(parts[1])
                            } catch (AmbiguousMoveException ame) {
                                println("Ambiguous move!")
                            } catch (IllegalMoveException ime) {
                                println("Illegal move!")
                            }
                        }
                        break
                    case [":r", "remove"]:
                        if (parts.size() != 2) {
                            println("You must specify the square to remove a piece from!")
                        } else {
                            board.removePiece(parts[1])
                        }
                        break
                    default:
                        println("Invalid command!")
                }
            }
            println("\n${board.prettyPrint()}")
            print("> ")
            command = scan.next()
        }
    }

    /** Explore mode - currently exists for testing purposes to interact with a chessboard. */
    private static void explore(Chessboard board)
    {
        log.info("Entering explore mode...")

        final List<String> TO_QUIT = [":quit", ":q"]
        final String INTRO_TEXT = """
                *========[ Board Explorer ]========================================*
                | You play both sides. At the prompt, type the next move,          |
                | using Portable Game Notation (PGN). For example, to move         |
                | a Knight from b1 to c3, enter "Nc3".                             |
                | To quit, enter "${TO_QUIT[0]}" or "${TO_QUIT[1]}".                                  |
                *==================================================================*""".stripIndent()

        final GameState state = board.gameState
        final Scanner scan = new Scanner(System.in)

        println(INTRO_TEXT)
        waitForEnter(scan)

        String move = "", lastMove = ""
        while (!TO_QUIT.contains(move)) {
            if (move) {
                try {
                    board.move(move)
                } catch (AmbiguousMoveException ame) {
                    /* TODO - do more than this */
                    println("Ambiguous move!")
                } catch (IllegalMoveException ime) {
                    /* TODO - do more than this */
                    println("Illegal move!")
                }
            }
            println("\n${board.prettyPrint()}")
            if (lastMove) {
                println("Last move: $lastMove")
            }
            print("Enter ${state.activeColor == PieceColor.WHITE ? "white" : "black"}'s move: ")
            move = scan.next()
            lastMove = "$state.moveCount${state.activeColor == PieceColor.WHITE ? ". " : "..."}$move"
        }
    }

    private static void pgn(final String filepath)
    {
        log.info("Entering PGN mode...")
        log.debug("Attempting to parse game from input file: $filepath")
        println("Loading game from '$filepath' ...")
        final long start = System.currentTimeMillis()
        try {
            final Game game = new PGNReader(filepath).read()
            log.debug("Successfully imported game from file in ${System.currentTimeMillis() - start} ms")
            log.debug("Tag pairs were:")
            game.PGN.tagPairs.each { k, v -> log.debug("\t$k => $v") }
            println("Game loaded.")
        } catch (AmbiguousMoveException | IllegalMoveException | ParseException e) {
            log.warn("Failed to read file '$filepath'!", e)
            println("An error occurred while reading the file: $e.localizedMessage")
        }
    }

    private static void waitForEnter(final Scanner scan)
    {
        println("\nPress Enter to begin...")
        scan.useDelimiter("")
        scan.next()
        scan.useDelimiter("\n")
    }
    
}
