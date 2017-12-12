package chess.Pieces;

import java.io.Serializable;
import java.util.ArrayList;

import chess.Grid;

/**
 * Basic Queen Moves implementation
 * @author John Freese, Manan Bhavsar
 */
public class Queen extends Piece implements Serializable{
    public Queen() {}

    public Queen(char owner) {
        super(owner);
    }

    public ArrayList<Grid.Space> getMoves(Grid.Space start) {
        ArrayList<Grid.Space> moves = new ArrayList<Grid.Space>();
        moves.addAll(getHorizontalMoves(start));
        moves.addAll(getDiagonalMoves(start));
        return moves;
    }

    public String toString() {
        return owner + "Q";
    }
}
