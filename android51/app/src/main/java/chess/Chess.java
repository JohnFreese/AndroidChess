package chess;
import chess.Pieces.*;
import chess.Grid;
import java.util.Scanner;

/**
 * Chess Implementation
 *
 * @author John Freese, Manan Bhavsar
 * @version 1.0
 * @since 11/2/2017
 *
public class Chess {
    private static char current_player = 'w'; //white goes first
    public static void main(String args[]) {
        String draw = "";
        String[] tokens;   //the input tokens
        Grid.Space start;
        Grid.Space destination;
        Scanner scanner = new Scanner(System.in);
        Grid.init();

        while(!isGameOver()) {
            Grid.printBoard();
            if (current_player == 'w')
                System.out.print("White Move: ");
            else
                System.out.print("Black Move: ");
            tokens = scanner.nextLine().split(" ");
            System.out.println();;
            if (tokens.length > 0 && tokens[0].equals("resign")) {
                break;
            }

            if (tokens.length > 2 && tokens[2].equals("draw?")) {
                //read the next input
                draw = scanner.nextLine();
                if (draw.equals("draw")) {
                    break;
                }
                else {
                    draw = "";
                }
            }

            while(!(isLegalCommand(tokens[0])) && !(isLegalCommand(tokens[1]))) {
                System.out.println("Command entered is not valid: " + tokens[0] + " " + tokens[1]);
                tokens = scanner.nextLine().split(" ");
            }

            int row = Character.getNumericValue(tokens[0].charAt(1));
            int column = tokens[0].charAt(0);

            start = Grid.mapToSpace(row, column);

            row = Character.getNumericValue(tokens[1].charAt(1));
            column = tokens[1].charAt(0);

            destination = Grid.mapToSpace(row, column);;

            if (!(Grid.movePiece(start, destination, current_player, isInCheck()))) {
                System.out.println("Illegal move, try again");
                System.out.println();
                continue;
            }

            if (current_player == 'w') {
                Grid.wKingCheck[0] = null;
                Grid.wKingCheck[1] = null;
                current_player = 'b';
            }
            else {
                Grid.bKingCheck[0] = null;
                Grid.bKingCheck[1] = null;
                current_player = 'w';
            }
        }
        if (!(draw.equals("draw"))) {
            if (current_player == 'b') {
                System.out.println("CheckMate\nWhite Wins");
            }
            else {
                System.out.println("CheckMate\nBlack Wins");
            }
            scanner.close();
        }
        else {
            //its a draw don't print anything
        }
    }*/
    /**
     * isGameOver looks at the current player king to see if it
     * has no moves, if it does not and it is currently in check
     * then the game is over
     *
     * @return whether or not the game is over
     *
    public static boolean isGameOver() {
        Grid.Space playerKing = Grid.findKing(current_player);

        boolean king_has_moves = playerKing.piece.getMoves(playerKing).stream()
                .map(s -> Grid.illegalKingMove(s, playerKing))
                .reduce((a, b) -> a && b)
                .orElse(true);

        return isInCheck() && king_has_moves;
    }*/

    /**
     * looks at the current player and the bKingCheck or wKingCheck
     * space to see if there is a piece putting the king in check
     * if there is then the player is in check
     *
     * @return whether or not a player is in Check
     *
    public static boolean isInCheck() {
        if (current_player == 'b' && Grid.bKingCheck[0] != null) {
            return true;
        }
        else if (current_player == 'w' && Grid.wKingCheck[0] != null) {
            return true;
        }
        return false;
    }*/

    /**
     * takes a string and determines whether or not it is
     * a valid command. Valid commands: a1 ... h8
     *
     * @params any string
     * @return whether the string is a valid command
     *
    private static boolean isLegalCommand(String command) {
        switch (command.charAt(0)) {
            case 'a': case 'b': case 'c': case 'd': case 'e': case 'f': case 'g':
            case 'h': break;
            default: return false;
        }
        switch (command.charAt(1)) {
            case '1': case '2': case '3': case '4': case '5': case '6': case '7':
            case '8': break;
            default: return false;
        }

        return true;
    }

}*/