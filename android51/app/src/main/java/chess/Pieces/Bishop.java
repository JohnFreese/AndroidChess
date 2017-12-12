package chess.Pieces;

import java.io.Serializable;
import java.util.ArrayList;

import chess.Grid;

/**
 * Basic Bishop Moves implementation
 * @author John Freese, Manan Bhavsar
 */
public class Bishop extends Piece implements Serializable{
    public Bishop() {}

    public Bishop(char owner) {
        super(owner);
    }

    public ArrayList<Grid.Space> getMoves(Grid.Space start) {
        ArrayList<Grid.Space> moves = new ArrayList<Grid.Space>();
        moves = getDiagonalMoves(start);
        return moves;
    }

    public String toString() {
        return owner + "B";
    }

}
