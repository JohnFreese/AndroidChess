package chess.Pieces;

import java.io.Serializable;
import java.util.ArrayList;

import chess.Grid;

/**
 * Basic Pawn Moves implementation
 * @author John Freese, Manan Bhavsar
 */
public class Pawn extends Piece implements Serializable{
    public Pawn () {}

    public Pawn(char owner) {
        super(owner);
    }

    public ArrayList<Grid.Space> getMoves(Grid.Space start) {
        ArrayList<Grid.Space> moves = new ArrayList<Grid.Space>();
        int row = start.row;
        int column = (int) start.column;
        int offset = -1;

        if(start.piece.owner == 'w'){
            offset = 1;
        }

        if (!start.piece.moved) {
            if (Grid.mapToSpace(row + offset, column).piece == null
                    && Grid.mapToSpace(row + 2*offset, column).piece == null) {
                moves.add(Grid.mapToSpace(row + 2*offset, column));
            }
        }

        if (Grid.mapToSpace(row + offset, column).piece == null) {
            moves.add(Grid.mapToSpace(row + offset, column));
        }

        if (start.column == 'a') {
            if (Grid.mapToSpace(row, column + 1).piece != null && Grid.mapToSpace(row, column + 1).piece.owner != start.piece.owner) {
                moves.add(Grid.mapToSpace(row, column + 1));
            }
        } else if (start.column == 'h') {// check if pawn is in column h
            if (Grid.mapToSpace(row, column - 1).piece != null && Grid.mapToSpace(row, column - 1).piece.owner != start.piece.owner) {
                moves.add(Grid.mapToSpace(row, column - 1));
            }
        } else {
            if (Grid.mapToSpace(row + offset, column + 1).piece != null && Grid.mapToSpace(row + offset, column + 1).piece.owner != start.piece.owner) {
                moves.add(Grid.mapToSpace(row + offset, column + 1));
            }
            if (Grid.mapToSpace(row + offset, column - 1).piece != null && Grid.mapToSpace(row + offset, column - 1).piece.owner != start.piece.owner) {
                moves.add(Grid.mapToSpace(row + offset, column - 1));
            }
        }

        return moves;
    }

    public String toString() {
        return owner + "P";
    }
}
