package chess;

import chess.Pieces.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Grid Class used to construct the board
 * @author John Freese, Manan Bhavsar
 */
public class Grid {
    public static Space[][] board;
    public static Space[] wKingCheck;
    public static Space[] bKingCheck;

    public static class Space implements Serializable{
        public Piece piece = null;
        public String color; //for UI purposes not needed for now
        public int row;
        public char column;
        public Piece[] controllingPieces; //this is a problem for another day

        public Space(String color, Piece piece) {
            this.color = color;
            this.piece = piece;
        }

        public Space(String color, Piece piece, char column, int row) {
            this.color = color;
            this.piece = piece;
            this.row = row;
            this.column = column;
        }

        public boolean equals(Object o) {
            if (o == null || !(o instanceof Space)) {
                return false;
            }
            Space space = (Space) o;
            if (space.row == this.row && space.column == this.column) {
                return true;
            }
            return false;
        }

        public String toString() {
            return this.column + " " + this.row;
        }
    }

    private Grid() {

    }
    /**
     * Sets the board to its initial state
     */
    public static void init() {
        wKingCheck = new Space[2];
        wKingCheck[0] = null;
        wKingCheck[1] = null;
        bKingCheck = new Space[2];
        bKingCheck[0] = null;
        bKingCheck[1] = null;

        board = new Space[9][9];
        board[0][0] = new Space("white", new Rook('b'), 'a', 8); //a8
        board[0][1] = new Space("black", new Knight('b'), 'b', 8); //b8
        board[0][2] = new Space("white", new Bishop('b'), 'c', 8); //...
        board[0][3] = new Space("black", new Queen('b'), 'd', 8);
        board[0][4] = new Space("white", new King('b'), 'e', 8);
        board[0][5] = new Space("black", new Bishop('b'), 'f', 8);
        board[0][6] = new Space("white", new Knight('b'), 'g', 8);
        board[0][7] = new Space("black", new Rook('b'), 'h', 8); //h8

        for (int i = 0; i < 8; i++) {
            if (i%2 == 0) {
                board[1][i] = new Space("black", new Pawn('b'),(char) ('a' + i), 7);
            }
            else {
                board[1][i] = new Space("white", new Pawn('b') ,(char) ('a' + i), 7);
            }
        }

        for (int j = 2; j < 6; j++) {
            for (int i = 0; i < 8; i++) {
                if ((i + j + 1)%2 == 0) {
                    board[j][i] = new Space("black", null, (char) ('a' + i), 8 - j);
                }
                else {
                    board[j][i] = new Space("white", null, (char) ('a' + i), 8 - j);
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            if ((i+1)%2 == 0) {
                board[6][i] = new Space("black", new Pawn('w'), (char) ('a' + i), 2);
            }
            else {
                board[6][i] = new Space("white", new Pawn('w'), (char) ('a' + i), 2);
            }
        }

        board[7][0] = new Space("black", new Rook('w'), 'a', 1); //a1
        board[7][1] = new Space("white", new Knight('w'), 'b', 1);
        board[7][2] = new Space("black", new Bishop('w'), 'c', 1);
        board[7][3] = new Space("white", new Queen('w'), 'd', 1);
        board[7][4] = new Space("black", new King('w'), 'e', 1);
        board[7][5] = new Space("white", new Bishop('w'), 'f', 1);
        board[7][6] = new Space("black", new Knight('w'), 'g', 1);
        board[7][7] = new Space("white", new Rook('w'), 'h', 1); //h1

        for (int i = 0; i < 8; i++) {
            board[i][8] = new Space(Integer.toString(8-i), null);
        }

        for (int i = 0; i < 8; i++) {
            board[8][i] = new Space(Character.toString((char)(97 + i)), null);
        }
        board[8][8] = new Space("  ", null);
    }

    /**
     * Calcuates whether a given input is on the chess board
     *
     * @param row 1 ... 8
     * @param column a ... h
     * @return true if on the board, false otherwise
     */
    public static boolean isInBounds(int row, int column) {
        if (row > 8 || row < 1) {
            return false;
        }
        if (column > 104 || column < 97) {
            return false;
        }
        return true;
    }

    public static Space mapToSpace(int row, int column) {
        return board[8 - row][column - 97];
    }

    public static boolean isMyPiece(char player, Space start) {
        return !(Grid.mapToSpace(start.row, start.column).piece.owner != player);
    }

    /**
     * Checks to see if the move is illegal by checking the destination against a list
     * of generated legal moves. if the move is legal then the function will move the piece and
     * check whether the piece is putting the king in check. If the piece is putting the king
     * in check then that piece is added to the wKingCheck or bKingCheck field. returns false
     * if the piece could not be moved
     *
     * @param start location of piece to be moved
     * @param destination location where the piece is to be moved
     * @param player current player
     * @param isInCheck boolean to see if the player is in check
     * @return whether the piece was moved or not
     */
    public static boolean movePiece(Space start, Space destination, char player, boolean isInCheck) {
        if (start.piece == null || start.piece.owner != player) {
            return false;
        }


        if (isInCheck && !(start.piece instanceof King)) { //player is in double check
            if ((player == 'w' && wKingCheck[0] != null && wKingCheck[1] != null) ||
                    (player == 'b' && bKingCheck[0] != null && bKingCheck[1] != null))
                return false;
        }

        ArrayList<Space> legalMoves = start.piece.getMoves(start);

        //System.out.println(legalMoves);
        legalMoves.stream() //check if he destination is a legal move
                .filter(s -> s.equals(destination) && !illegalMove(start, destination))
                .limit(1)
                .forEach(space -> {
                    destination.piece = start.piece;
                    start.piece = null;
                    destination.piece.moved = true;

                    ArrayList<Space> futureMoves = null;
                    futureMoves = destination.piece.getMoves(destination);

                    futureMoves.stream()
                            .filter(s -> s.piece instanceof King)
                            .limit(1)
                            .forEach(future_space -> {
                                if (future_space.piece.owner == 'w')
                                    wKingCheck[0] = destination;
                                else
                                    bKingCheck[0] = destination;
                            });

                    //discover check
                    Arrays.asList(board).stream()
                            .flatMap(Arrays::stream)
                            .filter(s -> (s.piece instanceof Rook || s.piece instanceof Bishop || s.piece instanceof Queen) && isMyPiece(player, s))
                            .filter(s -> {
                                if (player == 'w' & bKingCheck[0] == null) {
                                    return true;
                                } else if (player == 'b' & null == wKingCheck[0]) {
                                    return true;
                                }
                                return false;
                            })
                            .forEach(s -> {
                                if (player == 'w') {
                                    if (s.piece.getMoves(s).contains(findKing('b'))) {
                                        bKingCheck[0] = s;
                                        //System.out.println("Discover Check!");
                                    }
                                }
                                else {
                                    if (s.piece.getMoves(s).contains(findKing('w'))) {
                                        wKingCheck[0] = s;
                                       //System.out.println("Discover Check!");
                                    }
                                }
                            });

                    //check for double check
                    Arrays.asList(board).stream()
                            .flatMap(Arrays::stream)
                            .filter(s -> (s.piece instanceof Rook || s.piece instanceof Bishop || s.piece instanceof Queen) && isMyPiece(player, s))
                            .filter(s -> {
                                if (player == 'w' & bKingCheck[0] != null)
                                    return s != bKingCheck[0];
                                else if (player == 'b' & wKingCheck[0] != null)
                                    return s != wKingCheck[0];
                                return false;
                            })
                            .forEach(s -> {
                                if (player == 'w') {
                                    if (s.piece.getMoves(s).contains(findKing('b'))) {
                                        bKingCheck[1] = s;
                                        //System.out.println("Double Check!");
                                    }
                                }
                                else {
                                    if (s.piece.getMoves(s).contains(findKing('w'))) {
                                        wKingCheck[1] = s;
                                        //System.out.println("Double Check!");
                                    }
                                }
                            });
                });

        return start.piece == null;
    }

    public static boolean canCastleLeft(Space king) {
        if (king.piece.moved == true) {
            return false;
        }

        if (king.piece.owner == 'w') {
            if (mapToSpace(1, (int) 'a').piece != null && mapToSpace(1, (int) 'a').piece.moved == false) {
                if (mapToSpace(1, (int) 'b').piece == null) {
                    if (mapToSpace(1, (int) 'c').piece == null) {
                        if (mapToSpace(1, (int) 'd').piece == null) {
                            return true;
                        }
                    }
                }
            }
        }
        else {
            if (mapToSpace(8, (int) 'a').piece != null && mapToSpace(8, (int) 'a').piece.moved == false) {
                if (mapToSpace(8, (int) 'b').piece == null) {
                    if (mapToSpace(8, (int) 'c').piece == null) {
                        if (mapToSpace(8, (int) 'd').piece == null) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public static boolean canCastleRight(Space king) {
        if (king.piece.moved == true) {
            return false;
        }

        if (king.piece.owner == 'w') {
            if (mapToSpace(1, (int) 'h').piece != null && mapToSpace(1, (int) 'h').piece.moved == false) {
                if (mapToSpace(1, (int) 'f').piece == null) {
                    if (mapToSpace(1, (int) 'g').piece == null) {
                        return true;
                    }
                }
            }
        }
        else {
            if (mapToSpace(8, (int) 'h').piece != null && mapToSpace(8, (int) 'h').piece.moved == false) {
                if (mapToSpace(8, (int) 'f').piece == null) {
                    if (mapToSpace(8, (int) 'g').piece == null) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean illegalMove(Grid.Space start, Grid.Space destination) {
        Piece tmp_piece = destination.piece;
        destination.piece = start.piece;
        start.piece = null;

        boolean bad_move =  Arrays.asList(board).stream()
                .flatMap(Arrays::stream)
                .filter(s -> s.piece != null && !(isMyPiece(destination.piece.owner, s)) && s.piece.getMoves(s).contains(findKing(destination.piece.owner)))
                .map(s -> true)
                .findAny()
                .orElse(false);

        start.piece = destination.piece;
        destination.piece = tmp_piece;

        return bad_move;
    }

    /**
     * goes through the entire grid brute force style until it finds the
     * current player's king
     *
     * @return the grid space containing the current player's king
     */
    public static Space findKing(char player) {
        return	Arrays.asList(board).stream()
                .flatMap(Arrays::stream)
                .filter(s -> s.piece instanceof King)
                .filter(s -> s.piece.owner == player)
                .findAny()
                .orElse(null);
    }

    /**
     * Prints the board in its current state
     */
    public static void printBoard() {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (board[row][column].piece != null)
                    System.out.print(board[row][column].piece + " ");
                else if (board[row][column].color.equals("white")) {
                    System.out.print("  " + " ");
                }
                else if (board[row][column].color.equals("black")) {
                    System.out.print("##" + " ");
                }
                else {
                    System.out.print(board[row][column].color + "  ");
                }

            }
            System.out.println();
        }
        System.out.println();
    }
}
