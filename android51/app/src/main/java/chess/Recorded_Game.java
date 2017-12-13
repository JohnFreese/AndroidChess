package chess;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Object class that holds the name, winner, date, and
 * moves made in a game
 */

public class RecordedGame implements Serializable {
    private String name;
    private String winner;
    private Calendar date;
    private ArrayList<Move> moves;

    public RecordedGame(){};

    public RecordedGame(String name, String winner, ArrayList<Move> moves){
        this.name = name;
        this.winner = winner;
        this.moves = moves;
        this.date = Calendar.getInstance();
    }

    public Calendar getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<Move> moves) {
        this.moves = moves;
    }

    public String getDateString() {
        return date.getTime().toString();
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String toString(){
        return name +"\n"+date.getTime().toString();
    }
}