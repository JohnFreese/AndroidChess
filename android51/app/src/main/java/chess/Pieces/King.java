package chess.Pieces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import chess.Grid;

/**
 * Basic King Moves implementation
 * @author John Freese, Manan Bhavsar
 */
public class King extends Piece implements Serializable{
    public King() {}

    public King(char owner) {
        super(owner);
    }

    public ArrayList<Grid.Space> getMoves(Grid.Space start) {
        ArrayList<Grid.Space> moves = new ArrayList<Grid.Space>();
        int row;
        int column;
        //check once in every direction starting with up going clockwise
        row = start.row + 1;
        column = start.column;
        if (Grid.isInBounds(row, column)) {
            if (Grid.mapToSpace(row, column).piece == null || Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                moves.add(Grid.mapToSpace(row, column));
            }
        }

        row = start.row + 1;
        column = start.column + 1;
        if (Grid.isInBounds(row, column)) {
            if (Grid.mapToSpace(row, column).piece == null || Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                moves.add(Grid.mapToSpace(row, column));
            }
        }

        row = start.row;
        column = start.column + 1;
        if (Grid.isInBounds(row, column)) {
            if (Grid.mapToSpace(row, column).piece == null || Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                moves.add(Grid.mapToSpace(row, column));
            }
        }

        row = start.row - 1;
        column = start.column + 1;
        if (Grid.isInBounds(row, column)) {
            if (Grid.mapToSpace(row, column).piece == null || Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                moves.add(Grid.mapToSpace(row, column));
            }
        }

        row = start.row - 1;
        column = start.column;
        if (Grid.isInBounds(row, column)) {
            if (Grid.mapToSpace(row, column).piece == null || Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                moves.add(Grid.mapToSpace(row, column));
            }
        }

        row = start.row - 1;
        column = start.column - 1;
        if (Grid.isInBounds(row, column)) {
            if (Grid.mapToSpace(row, column).piece == null || Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                moves.add(Grid.mapToSpace(row, column));
            }
        }

        row = start.row;
        column = start.column - 1;
        if (Grid.isInBounds(row, column)) {
            if (Grid.mapToSpace(row, column).piece == null || Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                moves.add(Grid.mapToSpace(row, column));
            }
        }

        row = start.row + 1;
        column = start.column - 1;
        if (Grid.isInBounds(row, column)) {
            if (Grid.mapToSpace(row, column).piece == null || Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                moves.add(Grid.mapToSpace(row, column));
            }
        }

        return moves;
    }

    public String toString() {
        return owner + "K";
    }

}
