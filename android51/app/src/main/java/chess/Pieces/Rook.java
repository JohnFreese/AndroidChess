package chess.Pieces;

import java.io.Serializable;
import java.util.ArrayList;

import chess.Grid;

/**
 * Basic Rook Moves implementation
 * @author John Freese, Manan Bhavsar
 */
public class Rook extends Piece implements Serializable{
    public Rook() {}

    public Rook(char owner) {
        super(owner);
    }

    public ArrayList<Grid.Space> getMoves(Grid.Space start) {
        ArrayList<Grid.Space> moves = new ArrayList<Grid.Space>();
        moves = getHorizontalMoves(start);
        return moves;
    }

    public String toString() {
        return owner + "R";
    }

}
