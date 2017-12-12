package chess.Pieces;
import java.util.ArrayList;
import chess.Grid;

/**
 *Generic Piece Class
 *@author John Freese, Manan Bhavsar
 */
public abstract class Piece {
    public char owner;
    public boolean moved;

    public Piece() {}

    public Piece(char owner) {
        this.owner = owner;
        this.moved = false;
    }

    /**
     * Generic getMoves function
     *
     * @param start grid location of piece to get moves from
     * @return moves
     */
    public ArrayList<Grid.Space> getMoves(Grid.Space start) {
        ArrayList<Grid.Space> moves = new ArrayList<Grid.Space>();
        return moves;
    }

	/*
	public ArrayList<Grid.Space> getSquares(Grid.Space start) {
		ArrayList<Grid.Space> moves = new ArrayList<Grid.Space>();
		return moves;
	}*/

    /**
     * Generic diagonal moves function used in Bishop and Queen
     *
     * @param start grid location of piece to get moves from
     * @return all the diagonal moves the piece at the start can make
     */
    public ArrayList<Grid.Space> getDiagonalMoves(Grid.Space start) {
        ArrayList<Grid.Space> moves = new ArrayList<Grid.Space>();

        //up and left
        int row = start.row + 1;
        int column = (int) start.column - 1;
        while (row < 9 && column > 96) {
            //empty space
            if (Grid.board[8 - row][column - 97].piece == null) {
                moves.add(Grid.mapToSpace(row, column));
            }
            //ran into a piece that is of opposite colour
            else if (Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                moves.add(Grid.mapToSpace(row, column));
                break;
            }
            //piece of same colour
            else {
                break;
            }
            row++;
            column--;
        }

        //up and right
        row = start.row + 1;
        column = (int) start.column + 1;
        while (row < 9 && column < 105) {
            if (Grid.mapToSpace(row, column).piece == null) {
                moves.add(Grid.mapToSpace(row, column));
            }
            else if (Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                moves.add(Grid.mapToSpace(row, column));
                break;
            }
            else {
                break;
            }
            row++;
            column++;
        }

        //down and left
        row = start.row - 1;
        column = (int) start.column - 1;
        while (row > 0 && column > 96) {
            if (Grid.mapToSpace(row, column).piece == null) {
                moves.add(Grid.mapToSpace(row, column));
            }
            else if (Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                moves.add(Grid.mapToSpace(row, column));
                break;
            }
            else {
                break;
            }
            row--;
            column--;
        }

        //down and right
        row = start.row - 1;
        column = (int) start.column + 1;
        while (row > 0 && column < 105) {
            if (Grid.mapToSpace(row, column).piece == null) {
                moves.add(Grid.mapToSpace(row, column));
            }
            else if (Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                moves.add(Grid.mapToSpace(row, column));
                break;
            }
            else {
                break;
            }
            row--;
            column++;
        }

        return moves;
    }

    public ArrayList<Grid.Space> getLine(Grid.Space start, String direction) {
        ArrayList<Grid.Space> moves = new ArrayList<Grid.Space>();
        int row;
        int column;
        switch (direction) {
            case "l": {
                row = start.row;
                column = start.column - 1;
                while (column > 96) {
                    if (Grid.mapToSpace(row, column).piece == null) {
                        moves.add(Grid.mapToSpace(row, column));
                    }
                    else if (Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                        moves.add(Grid.mapToSpace(row, column));
                        break;
                    }
                    else {
                        break;
                    }
                    column--;
                }
                break;
            }
            case "ul": {
                row = start.row + 1;
                column = (int) start.column - 1;
                while (row < 9 && column > 96) {
                    //empty space
                    if (Grid.board[8 - row][column - 97].piece == null) {
                        moves.add(Grid.mapToSpace(row, column));
                    }
                    //ran into a piece that is of opposite colour
                    else if (Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                        moves.add(Grid.mapToSpace(row, column));
                        break;
                    }
                    //piece of same colour
                    else {
                        break;
                    }
                    row++;
                    column--;
                }
                break;
            }
            case "u" :{
                row = start.row + 1;
                column = (int) start.column;
                while (row < 9) {
                    if (Grid.mapToSpace(row, column).piece == null) {
                        moves.add(Grid.mapToSpace(row, column));
                    }
                    else if (Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                        moves.add(Grid.mapToSpace(row, column));
                        break;
                    }
                    else {
                        break;
                    }
                    row++;
                }
                break;
            }
            case "ur": {
                row = start.row + 1;
                column = (int) start.column + 1;
                while (row < 9 && column < 105) {
                    if (Grid.mapToSpace(row, column).piece == null) {
                        moves.add(Grid.mapToSpace(row, column));
                    }
                    else if (Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                        moves.add(Grid.mapToSpace(row, column));
                        break;
                    }
                    else {
                        break;
                    }
                    row++;
                    column++;
                }
                break;
            }
            case "r": {
                row = start.row;
                column = start.column + 1;
                while (column < 105) {
                    if (Grid.mapToSpace(row, column).piece == null) {
                        moves.add(Grid.mapToSpace(row, column));
                    }
                    else if (Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                        moves.add(Grid.mapToSpace(row, column));
                        break;
                    }
                    else {
                        break;
                    }
                    column++;
                }
                break;
            }
            case "dr": {
                row = start.row - 1;
                column = (int) start.column + 1;
                while (row > 0 && column < 105) {
                    if (Grid.mapToSpace(row, column).piece == null) {
                        moves.add(Grid.mapToSpace(row, column));
                    }
                    else if (Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                        moves.add(Grid.mapToSpace(row, column));
                        break;
                    }
                    else {
                        break;
                    }
                    row--;
                    column++;
                }
                break;
            }
            case "d": {
                row = start.row - 1;
                column = (int) start.column;
                while (row > 0) {
                    if (Grid.mapToSpace(row, column).piece == null) {
                        moves.add(Grid.mapToSpace(row, column));
                    }
                    else if (Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                        moves.add(Grid.mapToSpace(row, column));
                        break;
                    }
                    else {
                        break;
                    }
                    row--;
                }
                break;
            }
            case "dl": {
                row = start.row - 1;
                column = (int) start.column - 1;
                while (row > 0 && column > 96) {
                    if (Grid.mapToSpace(row, column).piece == null) {
                        moves.add(Grid.mapToSpace(row, column));
                    }
                    else if (Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                        moves.add(Grid.mapToSpace(row, column));
                        break;
                    }
                    else {
                        break;
                    }
                    row--;
                    column--;
                }
            }
            default:
        }
        return moves;
    }

    /**
     * Generic horizontal moves function used in Rook and Queen
     *
     * @param start grid location of piece to get moves from
     * @return all the horizontal moves the piece at the start can make
     */
    public ArrayList<Grid.Space> getHorizontalMoves(Grid.Space start) {
        ArrayList<Grid.Space> moves = new ArrayList<Grid.Space>();
        int row = start.row;
        int column = (int) start.column;

        //up moves
        row = start.row + 1;
        while (row < 9) {
            if (Grid.mapToSpace(row, column).piece == null) {
                moves.add(Grid.mapToSpace(row, column));
            }
            else if (Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                moves.add(Grid.mapToSpace(row, column));
                break;
            }
            else {
                break;
            }
            row++;
        }

        //down moves
        row = start.row - 1;
        while (row > 0) {
            if (Grid.mapToSpace(row, column).piece == null) {
                moves.add(Grid.mapToSpace(row, column));
            }
            else if (Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                moves.add(Grid.mapToSpace(row, column));
                break;
            }
            else {
                break;
            }
            row--;
        }

        //left moves
        row = start.row;
        column = start.column - 1;
        while (column > 96) {
            if (Grid.mapToSpace(row, column).piece == null) {
                moves.add(Grid.mapToSpace(row, column));
            }
            else if (Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                moves.add(Grid.mapToSpace(row, column));
                break;
            }
            else {
                break;
            }
            column--;
        }

        //right moves
        column = start.column + 1;
        while (column < 105) {
            if (Grid.mapToSpace(row, column).piece == null) {
                moves.add(Grid.mapToSpace(row, column));
            }
            else if (Grid.mapToSpace(row, column).piece.owner != start.piece.owner) {
                moves.add(Grid.mapToSpace(row, column));
                break;
            }
            else {
                break;
            }
            column++;
        }

        return moves;
    }

	/* To be used at a later date for more complicated chess
	//same as get moves but used for control purposes
	public ArrayList<Grid.Space> getDiagonalSquares(Grid.Space start) {
		ArrayList<Grid.Space> moves = new ArrayList<Grid.Space>();

		int row = start.row + 1;
		int column = (int) start.column - 1;
		while (row < 9 && column > 96) {
			if (Grid.board[8 - row][97 - column].piece == null) {
				moves.add(Grid.board[8 - row][column - 97]);
			}
			else {
				moves.add(Grid.board[8 - row][column - 97]);
				break;
			}
			row++;
			column--;
		}

		row = start.row + 1;
		column = (int) start.column + 1;
		while (row < 9 && column < 105) {
			if (Grid.board[8 - row][97 - column].piece == null) {
				moves.add(Grid.board[8 - row][column - 97]);
			}
			else {
				moves.add(Grid.board[8 - row][column - 97]);
				break;
			}
			row++;
			column++;
		}

		row = start.row - 1;
		column = (int) start.column - 1;
		while (row < 9 && column < 105) {
			if (Grid.board[8 - row][97 - column].piece == null) {
				moves.add(Grid.board[8 - row][column - 97]);
			}
			else {
				moves.add(Grid.board[8 - row][column - 97]);
				break;
			}
			row--;
			column--;
		}

		row = start.row - 1;
		column = (int) start.column + 1;
		while (row < 9 && column < 105) {
			if (Grid.board[8 - row][97 - column].piece == null) {
				moves.add(Grid.board[8 - row][column - 97]);
			}
			else {
				moves.add(Grid.board[8 - row][column - 97]);
				break;
			}
			row--;
			column++;
		}

		return moves;
	}

	public ArrayList<Grid.Space> getHorizontalSquares(Grid.Space start) {
		ArrayList<Grid.Space> moves = new ArrayList<Grid.Space>();
		int row = start.row;
		int column = (int) start.column;

		row = start.row + 1;
		while (row < 9) {
			if (Grid.board[8 - row][start.column - 97].piece == null) {
				moves.add(Grid.board[8 - row][start.column - 97]);
			}
			else {
				moves.add(Grid.board[8 - row][start.column - 97]);
				break;
			}
			row++;
		}

		//down moves
		row = start.row - 1;
		while (row > 0) {
			if (Grid.board[8 - row][start.column - 97].piece == null) {
				moves.add(Grid.board[8 - row][start.column - 97]);
			}
			else {
				moves.add(Grid.board[8 - row][start.column - 97]);
				break;
			}
			row--;
		}

		//left moves
		column = start.column - 1;
		while (column > 96) {
			if (Grid.board[8 - start.row][column - 97].piece == null) {
				moves.add(Grid.board[8 - start.row][column - 97]);
			}
			else {
				moves.add(Grid.board[8 - row][start.column - 97]);
				break;
			}
			column--;
		}

		//right moves
		column = start.column + 1;
		while (column < 105) {
			if (Grid.board[8 - start.row][column - 97].piece == null) {
				moves.add(Grid.board[start.row][column - 97]);
			}
			else {
				moves.add(Grid.board[8 - row][start.column - 97]);
				break;
			}
			column++;
		}

		return moves;
	}*/


    public String toString() {
        return " ";
    }
}
