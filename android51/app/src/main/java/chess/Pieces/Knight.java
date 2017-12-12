package chess.Pieces;

import java.io.Serializable;
import java.util.ArrayList;

import chess.Grid;

/**
 * Basic Knight Moves implementation
 * @author John Freese, Manan Bhavsar
 */
public class Knight extends Piece implements Serializable{
    public Knight() {}

    public Knight(char owner) {
        super(owner);
    }

    //custom one for knight
    public ArrayList<Grid.Space> getMoves(Grid.Space start) {
        ArrayList<Grid.Space> moves = new ArrayList<Grid.Space>();
        int row = start.row;
        int column = (int) start.column;
        /********************************
         * KNIGHT MOVES
         * XOXOX (row + 2, column +- 1)
         * OXXXO (row + 1, column +- 2)
         * XXPXX
         * OXXXO (row - 1, column +- 2)
         * XOXOX (row - 2, column +- 1)
         * manually check each of the 8 possible cases
         *******************************/
        row = start.row + 2;
        column = start.column - 1;
        if (Grid.isInBounds(row, column) && (Grid.mapToSpace(row, column).piece == null || Grid.mapToSpace(row, column).piece.owner != start.piece.owner)) {
            moves.add(Grid.mapToSpace(row, column));
        }
        row = start.row + 2;
        column = start.column + 1;
        if (Grid.isInBounds(row, column) && (Grid.mapToSpace(row, column).piece == null || Grid.mapToSpace(row, column).piece.owner != start.piece.owner)) {
            moves.add(Grid.mapToSpace(row, column));
        }
        row = start.row + 1;
        column = start.column - 2;
        if (Grid.isInBounds(row, column) && (Grid.mapToSpace(row, column).piece == null || Grid.mapToSpace(row, column).piece.owner != start.piece.owner)) {
            moves.add(Grid.mapToSpace(row, column));
        }
        row = start.row + 1;
        column = start.column + 2;
        if (Grid.isInBounds(row, column) && (Grid.mapToSpace(row, column).piece == null || Grid.mapToSpace(row, column).piece.owner != start.piece.owner)) {
            moves.add(Grid.mapToSpace(row, column));
        }
        row = start.row - 1;
        column = start.column - 2;
        if (Grid.isInBounds(row, column) && (Grid.mapToSpace(row, column).piece == null || Grid.mapToSpace(row, column).piece.owner != start.piece.owner)) {
            moves.add(Grid.mapToSpace(row, column));
        }
        row = start.row - 1;
        column = start.column + 2;
        if (Grid.isInBounds(row, column) && (Grid.mapToSpace(row, column).piece == null || Grid.mapToSpace(row, column).piece.owner != start.piece.owner)) {
            moves.add(Grid.mapToSpace(row, column));
        }
        row = start.row - 2;
        column = start.column - 1;
        if (Grid.isInBounds(row, column) && (Grid.mapToSpace(row, column).piece == null || Grid.mapToSpace(row, column).piece.owner != start.piece.owner)) {
            moves.add(Grid.mapToSpace(row, column));
        }
        row = start.row - 2;
        column = start.column + 1;
        if (Grid.isInBounds(row, column) && (Grid.mapToSpace(row, column).piece == null || Grid.mapToSpace(row, column).piece.owner != start.piece.owner)) {
            moves.add(Grid.mapToSpace(row, column));
        }

        return moves;
    }

    public String toString() {
        return owner + "N";
    }
}
