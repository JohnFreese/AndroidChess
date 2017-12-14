package main.android51;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.util.Log;

import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Arrays;

import chess.Grid;
import chess.Pieces.Pawn;

/**
 * Created by John Freese on 12/9/2017.
 */

public class HomeScreen extends Activity{
    public static final String RECORDED_GAMES = "RECORDED_GAMES";

    ArrayList<Game> games;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        try {
            Intent game_over_activity = getIntent();
            ArrayList<Grid.Space[]> saved_game = (ArrayList<Grid.Space[]>) game_over_activity.getSerializableExtra(GameOver.SAVED_GAME);
            String game_name = game_over_activity.getStringExtra(GameOver.GAME_NAME);
            Game game = new Game(game_name, saved_game);

            game.moves.stream()
                    .flatMap(x -> Arrays.stream(x))
                    .forEach(s -> {
                        Log.i("INFO", "move: " + s.toString());
                    });

            save_data(game);
        }
        catch (Exception e) {
            Log.i("INFO", "e caught");
        }
    }

    public void play_button_click(View v){
        Intent mainActivity = new Intent(this, ChessGame.class);
        startActivity(mainActivity);
    }

    static class Game implements Serializable {
        String name;
        ArrayList<Grid.Space[]> moves;

        public Game() {

        }

        public Game(String name, ArrayList<Grid.Space[]> moves) {
            this.name = name;
            this.moves = moves;
        }

        public String toString() {
            return name + moves.toString();
        }
    }

    public void r_games_button_click(View view) {
        Intent recorded_games_activity = new Intent(this, RecordedList.class);
        games = load_data();
        recorded_games_activity.putExtra(RECORDED_GAMES, games);
        startActivity(recorded_games_activity);
    }

    ArrayList<Game> load_data() {
        ArrayList<Game> loaded_games = new ArrayList<Game>();
        try {
            File file = new File(getFilesDir().getPath() + "/games");
//            file.delete();
//            file.createNewFile();
            FileInputStream fi = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fi);
            loaded_games = (ArrayList<Game>) in.readObject();
            in.close();
            fi.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return loaded_games;
    }

    void save_data(Game game) {
        ArrayList<Game> loaded_games = load_data();

        if (loaded_games == null) {
            Log.i("INFO", "Loaded games is null");
            loaded_games = new ArrayList<Game>();
            loaded_games.add(game);
        }
        else {
            loaded_games.add(game);
        }

        File file = new File(getFilesDir().getPath() + "/games");
        try {
            FileOutputStream fo = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fo);
            out.writeObject(loaded_games);
            out.close();
            fo.close();
        }
        catch (FileNotFoundException e) {

        }
        catch (IOException e) {

        }
    }
}
